package com.travel.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Generates a properly formatted PDF report using iText 5.

public class PDFExporter {

    //Fonts
    private static final BaseColor DARK_BLUE  = new BaseColor(31,  92, 153);
    private static final BaseColor MID_BLUE   = new BaseColor(46, 134, 193);
    private static final BaseColor LIGHT_GRAY = new BaseColor(242, 243, 244);
    private static final BaseColor WHITE      = BaseColor.WHITE;
    private static final BaseColor DARK_TEXT  = new BaseColor(30, 30, 50);

    private Font fontTitle;
    private Font fontH1;
    private Font fontH2;
    private Font fontBody;
    private Font fontBold;
    private Font fontCode;
    private Font fontLabel;

    private final String filename;
    private final Document document;
    private final List<Element> elements = new ArrayList<>();
    private PdfWriter writer;

    public PDFExporter(String filename) {
        this.filename = filename.endsWith(".pdf") ? filename : filename + ".pdf";
        this.document = new Document(PageSize.A4, 50, 50, 60, 60);
        initFonts();
    }

    private void initFonts() {
        try {
            fontTitle = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, WHITE);
            fontH1    = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, DARK_BLUE);
            fontH2    = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, MID_BLUE);
            fontBody  = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DARK_TEXT);
            fontBold  = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   DARK_TEXT);
            fontCode  = new Font(Font.FontFamily.COURIER,   9,  Font.NORMAL, DARK_TEXT);
            fontLabel = new Font(Font.FontFamily.HELVETICA, 9,  Font.ITALIC, new com.itextpdf.text.BaseColor(100, 100, 120));
        } catch (Exception e) {
            throw new RuntimeException("Font init failed: " + e.getMessage(), e);
        }
    }

    
    public void addScenarioHeader(String title) {
        try {
            elements.add(Chunk.NEWLINE);
            Paragraph p = new Paragraph(title, fontH1);
            p.setSpacingBefore(12);
            p.setSpacingAfter(4);
            elements.add(p);
            LineSeparator line = new LineSeparator(1.5f, 100, MID_BLUE, Element.ALIGN_LEFT, -2);
            elements.add(new Chunk(line));
            elements.add(Chunk.NEWLINE);
        } catch (Exception e) { /* non-fatal */ }
    }

    
    public void addSubHeader(String title) {
        Paragraph p = new Paragraph(title, fontH2);
        p.setSpacingBefore(8);
        p.setSpacingAfter(3);
        elements.add(p);
    }

    public void addLine(String text) {
        Paragraph p = new Paragraph(text, fontBody);
        p.setSpacingAfter(2);
        elements.add(p);
    }

    public void addDetail(String label, String value) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(label + ": ", fontBold));
        phrase.add(new Chunk(value, fontBody));
        Paragraph p = new Paragraph(phrase);
        p.setIndentationLeft(15);
        p.setSpacingAfter(2);
        elements.add(p);
    }

    public void addBullet(String text) {
        Paragraph p = new Paragraph("\u2022  " + text, fontBody);
        p.setIndentationLeft(25);
        p.setSpacingAfter(2);
        elements.add(p);
    }

    public void addCostSummary(String baseBudget, String addOnsTotal, String grandTotal) {
        try {
            elements.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(60);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setWidths(new float[]{45f, 55f});
            table.setSpacingBefore(6);
            table.setSpacingAfter(8);

            addCostRow(table, "Base Budget",   baseBudget,  LIGHT_GRAY, false);
            addCostRow(table, "Add-ons Total", addOnsTotal, WHITE,      false);
            addCostRow(table, "GRAND TOTAL",   grandTotal,  DARK_BLUE,  true);

            elements.add(table);
        } catch (Exception e) { /* non-fatal */ }
    }

  
    public void addServicesTable(List<String[]> services) {
        try {
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{25f, 50f, 25f});
            table.setSpacingBefore(6);
            table.setSpacingAfter(8);

            // Header
            for (String h : new String[]{"Service", "Details", "Cost"}) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, WHITE)));
                cell.setBackgroundColor(DARK_BLUE);
                cell.setPadding(6);
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            boolean odd = true;
            for (String[] row : services) {
                BaseColor bg = odd ? LIGHT_GRAY : WHITE;
                for (String cell : row) {
                    PdfPCell c = new PdfPCell(new Phrase(cell, fontBody));
                    c.setBackgroundColor(bg);
                    c.setPadding(5);
                    c.setBorderColor(BaseColor.LIGHT_GRAY);
                    table.addCell(c);
                }
                odd = !odd;
            }
            elements.add(table);
        } catch (Exception e) { /* non-fatal */ }
    }

    public void addDivider() {
        try {
            elements.add(Chunk.NEWLINE);
            LineSeparator line = new LineSeparator(0.5f, 100, new BaseColor(200, 200, 210), Element.ALIGN_CENTER, -2);
            elements.add(new Chunk(line));
            elements.add(Chunk.NEWLINE);
        } catch (Exception e) { /* non-fatal */ }
    }

    public void addPageBreak() {
        elements.add(Chunk.NEXTPAGE);
    }

    public void save() {
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            // Page event for header/footer on every page
            writer.setPageEvent(new HeaderFooterPageEvent());

            document.open();
            addCoverPage();

            for (Element e : elements) {
                document.add(e);
            }

            document.close();
            System.out.println("\n✓ PDF report saved to: " + filename);
        } catch (DocumentException | IOException e) {
            System.err.println("Error saving PDF: " + e.getMessage());
        }
    }

    // Internal helpers 

    private void addCoverPage() throws DocumentException {
        // Dark blue banner
        PdfPTable banner = new PdfPTable(1);
        banner.setWidthPercentage(100);
        banner.setSpacingAfter(30);

        PdfPCell titleCell = new PdfPCell();
        titleCell.setBackgroundColor(DARK_BLUE);
        titleCell.setPadding(20);
        titleCell.setBorder(Rectangle.NO_BORDER);

        Paragraph title = new Paragraph("ADVENTURE TOURS", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        Paragraph subtitle = new Paragraph("Personalized Itinerary Planner", new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, WHITE));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        Paragraph patterns = new Paragraph("Factory  ·  Template  ·  Strategy  ·  Decorator", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(180, 210, 240)));
        patterns.setAlignment(Element.ALIGN_CENTER);

        titleCell.addElement(title);
        titleCell.addElement(subtitle);
        titleCell.addElement(Chunk.NEWLINE);
        titleCell.addElement(patterns);
        banner.addCell(titleCell);
        document.add(banner);

        // Meta info
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
        Paragraph meta = new Paragraph("Generated: " + timestamp + "   |   Currency: Bangladeshi Taka (\u09F3)   |   Rate: 1 USD = 110 BDT",
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, new BaseColor(120, 120, 140)));
        meta.setAlignment(Element.ALIGN_CENTER);
        meta.setSpacingAfter(20);
        document.add(meta);

        LineSeparator line = new LineSeparator(1f, 100, MID_BLUE, Element.ALIGN_CENTER, -2);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addCostRow(PdfPTable table, String label, String value, BaseColor bg, boolean bold) {
        Font f = bold ? new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, bold ? WHITE : DARK_TEXT)
                      : fontBody;
        Font fv = bold ? new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, WHITE) : fontBold;

        PdfPCell labelCell = new PdfPCell(new Phrase(label, f));
        labelCell.setBackgroundColor(bg);
        labelCell.setPadding(6);
        labelCell.setBorderColor(BaseColor.LIGHT_GRAY);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, fv));
        valueCell.setBackgroundColor(bg);
        valueCell.setPadding(6);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valueCell.setBorderColor(BaseColor.LIGHT_GRAY);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private static class HeaderFooterPageEvent extends PdfPageEventHelper {
        private final Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, new BaseColor(160, 160, 170));

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            // Footer line
            cb.setColorStroke(new BaseColor(180, 180, 200));
            cb.setLineWidth(0.5f);
            cb.moveTo(document.leftMargin(), document.bottomMargin() - 5);
            cb.lineTo(document.right(), document.bottomMargin() - 5);
            cb.stroke();

            // Page number
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    new Phrase("Page " + writer.getPageNumber(), footerFont),
                    document.right(), document.bottomMargin() - 15, 0);

            // Footer label
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                    new Phrase("Adventure Tours — Design Patterns Assignment", footerFont),
                    document.leftMargin(), document.bottomMargin() - 15, 0);
        }
    }
}