package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;


// decorator: Concrete Decorator
public class TravelInsuranceDecorator extends ItineraryDecorator {

    private static final double INSURANCE_RATE = 0.05; // 5% of budget

    public TravelInsuranceDecorator(BaseItinerary itinerary) {
        super(itinerary);
    }

    @Override
    public void printServiceDetails() {
        System.out.println("  [+] TRAVEL INSURANCE");
        System.out.println("      Coverage  : Medical, Trip cancellation, Lost baggage");
        System.out.println("      Provider  : GlobalShield Insurance");
        System.out.println("      Cost      : " + CurrencyUtil.formatBdtDecimal(getAdditionalCost()));
    }

    @Override
    public double getAdditionalCost() {
        return wrappedItinerary.getProfile().getBudget() * INSURANCE_RATE;
    }

    @Override
    public String getServiceName() {
        return "Travel Insurance";
    }
}
