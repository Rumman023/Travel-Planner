package com.travel.main;

import com.travel.decorator.*;
import com.travel.factory.*;
import com.travel.model.TravelerProfile;
import com.travel.strategy.*;
import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;
import com.travel.util.PDFExporter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


// Main entry point.
public class TravelPlannerApp {

    public static void main(String[] args) {

        PDFExporter pdf = new PDFExporter("TravelItinerary.pdf");

        printBanner();

        // SCENARIO 1
        printSeparator("SCENARIO 1: Miraj — Adventure Traveler");

        TravelerProfile mirajProfile = new TravelerProfile(
            "Miraj", "adventure",
            CurrencyUtil.usdToBdt(2500.00), 14,
            Arrays.asList("Asia", "South America"),
            Arrays.asList("rock climbing", "skydiving", "surfing"), 1
        );

        ItineraryFactory adventureFactory = ItineraryFactoryProvider.getFactory("adventure");
        RecommendationStrategy aiStrategy = new AIRecommendationStrategy();
        BaseItinerary mirajItinerary = adventureFactory.createItinerary(mirajProfile, aiStrategy);
        mirajItinerary.buildItinerary();

        System.out.println("\n\n[Applying add-on services via Decorators...]");
        TravelInsuranceDecorator mirajIns   = new TravelInsuranceDecorator(mirajItinerary);
        LocalGuideDecorator      mirajGuide = new LocalGuideDecorator(mirajItinerary);
        PhotoPackageDecorator    mirajPhoto = new PhotoPackageDecorator(mirajItinerary);
        List<ItineraryDecorator> mirajAddons = List.of(mirajIns, mirajGuide, mirajPhoto);

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║         MIRAJ'S FINAL ITINERARY SUMMARY       ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        double mirajAddOnTotal = printFinalSummary(mirajItinerary, mirajAddons);

        // Feed Scenario 1 into PDF
        pdf.addScenarioHeader("Scenario 1: Miraj — Adventure Traveler");
        addProfileToPdf(pdf, mirajProfile);
        pdf.addSubHeader("Selected Destinations");
        mirajItinerary.getSelectedDestinations().forEach(pdf::addBullet);
        pdf.addSubHeader("Booked Activities");
        mirajItinerary.getBookedActivities().forEach(pdf::addBullet);
        pdf.addDetail("Transport", mirajItinerary.getTransportPlan());
        pdf.addDetail("Recommendation Strategy", aiStrategy.getStrategyName());
        addAddonsToPdf(pdf, mirajAddons);
        pdf.addCostSummary(
            CurrencyUtil.formatBdt(mirajProfile.getBudget()),
            CurrencyUtil.formatBdtDecimal(mirajAddOnTotal),
            CurrencyUtil.formatBdtDecimal(mirajProfile.getBudget() + mirajAddOnTotal)
        );

        pdf.addDivider();

        // SCENARIO 2
        printSeparator("SCENARIO 2: Rakib Family — Family Travelers");

        TravelerProfile rakibProfile = new TravelerProfile(
            "Rakib Family", "family",
            CurrencyUtil.usdToBdt(6000.00), 10,
            Arrays.asList("Europe"),
            Arrays.asList("culture", "history", "food", "kid-friendly"), 4
        );

        ItineraryFactory familyFactory = ItineraryFactoryProvider.getFactory("family");
        RecommendationStrategy cultureStrategy = new LocalCultureStrategy();
        BaseItinerary rakibItinerary = familyFactory.createItinerary(rakibProfile, cultureStrategy);
        rakibItinerary.buildItinerary();

        System.out.println("\n\n[Applying add-on services via Decorators...]");
        MealPlanDecorator       rakibMeals     = new MealPlanDecorator(rakibItinerary);
        AirportTransferDecorator rakibTransfers = new AirportTransferDecorator(rakibItinerary);
        List<ItineraryDecorator> rakibAddons    = List.of(rakibMeals, rakibTransfers);

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║       RAKIB FAMILY'S FINAL ITINERARY SUMMARY  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        double rakibAddOnTotal = printFinalSummary(rakibItinerary, rakibAddons);

        // Feed Scenario 2 into PDF
        pdf.addScenarioHeader("Scenario 2: Rakib Family — Family Travelers");
        addProfileToPdf(pdf, rakibProfile);
        pdf.addSubHeader("Selected Destinations");
        rakibItinerary.getSelectedDestinations().forEach(pdf::addBullet);
        pdf.addSubHeader("Booked Activities");
        rakibItinerary.getBookedActivities().forEach(pdf::addBullet);
        pdf.addDetail("Transport", rakibItinerary.getTransportPlan());
        pdf.addDetail("Recommendation Strategy", cultureStrategy.getStrategyName());
        addAddonsToPdf(pdf, rakibAddons);
        pdf.addCostSummary(
            CurrencyUtil.formatBdt(rakibProfile.getBudget()),
            CurrencyUtil.formatBdtDecimal(rakibAddOnTotal),
            CurrencyUtil.formatBdtDecimal(rakibProfile.getBudget() + rakibAddOnTotal)
        );

        pdf.addDivider();

        // STRATEGY SWAP DEMO
        printSeparator("STRATEGY SWAP DEMO: Budget vs Quality Strategy\n  (Same BackpackerItinerary, different algorithms)");

        TravelerProfile jamilProfile = new TravelerProfile(
            "Jamil", "backpacker",
            CurrencyUtil.usdToBdt(800.00), 7,
            Arrays.asList(),
            Arrays.asList("hiking", "local food"), 1
        );

        ItineraryFactory budgetFactory = ItineraryFactoryProvider.getFactory("budget");

        System.out.println("\n--- With BudgetStrategy ---");
        BaseItinerary budgetRun = budgetFactory.createItinerary(jamilProfile, new BudgetStrategy());
        budgetRun.buildItinerary();

        System.out.println("\n--- With QualityStrategy (same traveler, strategy swapped!) ---");
        BaseItinerary qualityRun = budgetFactory.createItinerary(jamilProfile, new QualityStrategy());
        qualityRun.buildItinerary();

        // Feed Strategy Demo into PDF
        pdf.addScenarioHeader("Strategy Swap Demo: Jamil — Backpacker");
        pdf.addDetail("Traveler", jamilProfile.getName());
        pdf.addDetail("Budget", CurrencyUtil.formatBdt(jamilProfile.getBudget()));
        pdf.addDetail("Duration", jamilProfile.getDurationDays() + " days");
        pdf.addSubHeader("With BudgetStrategy");
        budgetRun.getSelectedDestinations().forEach(pdf::addBullet);
        pdf.addSubHeader("With QualityStrategy (same traveler — strategy swapped at runtime)");
        qualityRun.getSelectedDestinations().forEach(pdf::addBullet);
        pdf.addLine("This demo shows Strategy swapped at runtime: same factory, same profile, different algorithm output.");

        // Write PDF
        pdf.save();
    }

    //Helpers

    private static void printBanner() {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║    ADVENTURE TOURS — PERSONALIZED ITINERARY PLANNER  ║");
        System.out.println("║    Demonstrating: Factory + Template + Strategy +     ║");
        System.out.println("║                   Decorator Design Patterns           ║");
        System.out.println("║    Currency: Bangladeshi Taka (\u09F3) | Rate: 1USD = 110\u09F3 ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    private static void printSeparator(String title) {
        System.out.println("\n\n\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501");
        System.out.println("  " + title);
        System.out.println("\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501");
    }

    // Prints the final summary to console and returns total add-on cost.
    private static double printFinalSummary(BaseItinerary itinerary, List<ItineraryDecorator> decorators) {
        System.out.println("  Traveler      : " + itinerary.getProfile().getName());
        System.out.println("  Itinerary type: " + itinerary.getItineraryType());
        System.out.println("  Destinations  : " + itinerary.getSelectedDestinations());
        System.out.println("  Transport     : " + itinerary.getTransportPlan());
        System.out.println("\n  \u2500\u2500 Add-on Services \u2500\u2500");

        double total = 0;
        for (ItineraryDecorator d : decorators) {
            d.printEnhancedSummary();
            total += d.getAdditionalCost();
        }

        System.out.println("\n  Base Budget   : " + CurrencyUtil.formatBdt(itinerary.getProfile().getBudget()));
        System.out.printf("  Add-ons Total : %s%n", CurrencyUtil.formatBdtDecimal(total));
        System.out.printf("  GRAND TOTAL   : %s%n",
                CurrencyUtil.formatBdtDecimal(itinerary.getProfile().getBudget() + total));
        return total;
    }

    private static void addProfileToPdf(PDFExporter pdf, TravelerProfile p) {
        pdf.addSubHeader("Traveler Profile");
        pdf.addDetail("Name",          p.getName());
        pdf.addDetail("Type",          p.getTravelerType());
        pdf.addDetail("Budget",        CurrencyUtil.formatBdt(p.getBudget()));
        pdf.addDetail("Duration",      p.getDurationDays() + " days");
        pdf.addDetail("Group Size",    String.valueOf(p.getGroupSize()));
        pdf.addDetail("Interests",     p.getInterests().toString());
    }

    private static void addAddonsToPdf(PDFExporter pdf, List<ItineraryDecorator> decorators) {
        pdf.addSubHeader("Add-on Services");
        List<String[]> rows = new ArrayList<>();
        for (ItineraryDecorator d : decorators) {
            rows.add(new String[]{
                d.getServiceName(),
                "See console output for details",
                CurrencyUtil.formatBdtDecimal(d.getAdditionalCost())
            });
        }
        pdf.addServicesTable(rows);
    }
}