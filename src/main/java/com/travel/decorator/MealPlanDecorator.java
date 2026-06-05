package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.util.CurrencyUtil;


// decorator: Concrete Decorator
public class MealPlanDecorator extends ItineraryDecorator {

    private static final double MEAL_COST_PER_DAY_USD = 35.0;

    public MealPlanDecorator(BaseItinerary itinerary) {
        super(itinerary);
    }

    @Override
    public void printServiceDetails() {
        System.out.println("  [+] MEAL PLAN");
        System.out.println("      Includes  : Daily breakfast + dinner at local partner restaurants");
        System.out.println("      Duration  : " + wrappedItinerary.getProfile().getDurationDays() + " days");
        System.out.println("      Dietary   : Vegetarian/vegan/halal options available");
        System.out.println("      Cost      : " + CurrencyUtil.formatBdtDecimal(getAdditionalCost()) +
                           " (" + CurrencyUtil.formatBdt(CurrencyUtil.usdToBdt(MEAL_COST_PER_DAY_USD)) + "/day × " +
                           wrappedItinerary.getProfile().getDurationDays() + " days)");
    }

    @Override
    public double getAdditionalCost() {
        return wrappedItinerary.getProfile().getDurationDays() * CurrencyUtil.usdToBdt(MEAL_COST_PER_DAY_USD);
    }

    @Override
    public String getServiceName() {
        return "Meal Plan";
    }
}
