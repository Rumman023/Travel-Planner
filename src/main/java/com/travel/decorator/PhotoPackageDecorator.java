package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;


// decorator: Concrete Decorator
public class PhotoPackageDecorator extends ItineraryDecorator {

    private static final double PHOTO_PACKAGE_COST_USD = 299.0;

    public PhotoPackageDecorator(BaseItinerary itinerary) {
        super(itinerary);
    }

    @Override
    public void printServiceDetails() {
        System.out.println("  [+] PHOTOGRAPHY PACKAGE");
        System.out.println("      Includes  : Professional photographer for 2 days");
        System.out.println("      Deliverable: 200+ edited digital photos, same-day previews");
        System.out.println("      Extras    : Drone aerial shots at key destinations");
        System.out.println("      Cost      : " + CurrencyUtil.formatBdtDecimal(getAdditionalCost()));
    }

    @Override
    public double getAdditionalCost() {
        return CurrencyUtil.usdToBdt(PHOTO_PACKAGE_COST_USD);
    }

    @Override
    public String getServiceName() {
        return "Photography Package";
    }
}
