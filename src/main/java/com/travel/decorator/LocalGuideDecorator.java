package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;


// decorator: Concrete Decorator
public class LocalGuideDecorator extends ItineraryDecorator {

    private static final double GUIDE_COST_USD = 75.0;

    public LocalGuideDecorator(BaseItinerary itinerary) {
        super(itinerary);
    }

    @Override
    public void printServiceDetails() {
        System.out.println("  [+] LOCAL GUIDE SERVICE");
        System.out.println("      Guides    : 1 certified local guide per destination city");
        System.out.println("      Languages : English + local language");
        System.out.println("      Includes  : 8hr/day guided tour, cultural briefing, emergency contact");
        System.out.println("      Cost      : " + CurrencyUtil.formatBdtDecimal(getAdditionalCost()) +
                           " (" + wrappedItinerary.getSelectedDestinations().size() + " cities x " +
                           CurrencyUtil.formatBdt(CurrencyUtil.usdToBdt(GUIDE_COST_USD)) + "/day)");
    }

    @Override
    public double getAdditionalCost() {
        int numDestinations = wrappedItinerary.getSelectedDestinations().size();
        return CurrencyUtil.usdToBdt(numDestinations * GUIDE_COST_USD);
    }

    @Override
    public String getServiceName() {
        return "Local Guide";
    }
}
