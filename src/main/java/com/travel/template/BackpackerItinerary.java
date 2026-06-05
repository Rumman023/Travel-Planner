package com.travel.template;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.util.CurrencyUtil;

/**
 * Template - Concrete Class
 */
public class BackpackerItinerary extends BaseItinerary {

    private static final double TRANSPORT_COST_PER_DAY_USD = 8.0;

    public BackpackerItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        super(profile, strategy);
    }

    @Override
    protected void bookActivities() {
        System.out.println("\n[Step 3] Booking BACKPACKER activities (hostel-friendly, social focus)...");
        bookedActivities = new java.util.ArrayList<>(strategy.recommendActivities(profile));
        bookedActivities.add("Hostel rooftop social night");
        bookedActivities.add("Group pub crawl with other backpackers");
        System.out.println("  > Activities booked: " + bookedActivities);
    }

    @Override
    protected String buildTransportPlan() {
        double perSegmentBdt = CurrencyUtil.usdToBdt(TRANSPORT_COST_PER_DAY_USD);
        double totalBdt = CurrencyUtil.usdToBdt(profile.getDurationDays() * TRANSPORT_COST_PER_DAY_USD);
        return "Budget transport: overnight buses, shared vans, and local trains. " +
               "Avg. " + CurrencyUtil.formatBdt(perSegmentBdt) + "/segment. " +
               "Estimated total transport: " + CurrencyUtil.formatBdt(totalBdt);
    }

    @Override
    public String getItineraryType() {
        return "BACKPACKER ITINERARY";
    }
}