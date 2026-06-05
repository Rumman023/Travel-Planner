package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;

// decorator: Concrete Decorator

public class AirportTransferDecorator extends ItineraryDecorator 
{

    private static final double TRANSFER_COST_USD = 45.0;

    public AirportTransferDecorator(BaseItinerary itinerary) {
        super(itinerary);
    }

    @Override
    public void printServiceDetails() {
        System.out.println("  [+] AIRPORT TRANSFER");
        System.out.println("      Includes  : Private pickup & drop-off at all airports");
        System.out.println("      Vehicle   : Air-conditioned sedan/SUV based on group size");
        System.out.println("      Tracking  : Real-time driver tracking via app");
        System.out.println("      Cost      : " + CurrencyUtil.formatBdtDecimal(getAdditionalCost()) +
                           " (" + CurrencyUtil.formatBdt(CurrencyUtil.usdToBdt(TRANSFER_COST_USD)) + " × " +
                           (wrappedItinerary.getSelectedDestinations().size() * 2) + " transfers)");
    }

    @Override
    public double getAdditionalCost() 
    {
        return wrappedItinerary.getSelectedDestinations().size() * 2 * CurrencyUtil.usdToBdt(TRANSFER_COST_USD);
    }

    @Override
    public String getServiceName() {
        return "Airport Transfer";
    }
}
