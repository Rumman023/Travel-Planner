package com.travel.template;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.util.CurrencyUtil;

// Template Pattern: Concrete Class

public class FamilyItinerary extends BaseItinerary {

    private static final double TRANSPORT_COST_PER_PERSON_USD = 120.0;

    public FamilyItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        super(profile, strategy);
    }

    @Override
    protected void bookActivities() {
        System.out.println("\n[Step 3] Booking FAMILY-FRIENDLY activities (child-safe filter applied)...");
        bookedActivities = new java.util.ArrayList<>(strategy.recommendActivities(profile));
        bookedActivities.add("Kid's cooking class");
        bookedActivities.add("Wildlife sanctuary visit (educational)");
        bookedActivities.add("Family beach day with lifeguard supervision");
        System.out.println("  > Family activities booked: " + bookedActivities);
    }

    @Override
    protected String buildTransportPlan() {
        double totalBdt = CurrencyUtil.usdToBdt(profile.getGroupSize() * TRANSPORT_COST_PER_PERSON_USD);
        return "Family transport: direct flights preferred, private minivan transfers. " +
               "Group of " + profile.getGroupSize() + " — estimated: " +
               CurrencyUtil.formatBdt(totalBdt) + " total transport.";
    }

    @Override
    public String getItineraryType() {
        return "FAMILY ITINERARY";
    }
}