package com.travel.template;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.util.CurrencyUtil;
import java.util.Arrays;

// Template pattern: Concrete Class

public class ExtremeSportsItinerary extends BaseItinerary {

    private static final double TRANSPORT_COST_PER_DAY_USD = 45.0;

    public ExtremeSportsItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        super(profile, strategy);
    }

    @Override
    protected void selectDestinations() {
        System.out.println("\n[Step 2] Selecting EXTREME destinations (adrenaline score > 8/10 only)...");
        System.out.println("  [Ignoring standard strategy output — applying extreme filter]");
        selectedDestinations = Arrays.asList(
            "Queenstown, NZ (bungee, skydive, jet boat)",
            "Moab, Utah (mountain biking, canyoneering)",
            "Interlaken, Switzerland (base jumping, paragliding)",
            "Chamonix, France (free solo climbing, extreme skiing)"
        );
        System.out.println("  > Extreme destinations: " + selectedDestinations);
    }

    @Override
    protected void bookActivities() {
        System.out.println("\n[Step 3] Booking EXTREME activities (waiver required for all)...");
        bookedActivities = Arrays.asList(
            "Skydiving (15,000 ft tandem)",
            "White-water rafting (Grade V)",
            "Free solo rock climbing session",
            "Base jumping introduction course",
            "Night cliff diving"
        );
        System.out.println("  > Extreme activities booked: " + bookedActivities);
    }

    @Override
    protected String buildTransportPlan() {
        double estimatedBdt = CurrencyUtil.usdToBdt(profile.getDurationDays() * TRANSPORT_COST_PER_DAY_USD);
        return "Adventure transport: 4WD off-road vehicles, helicopter transfers to remote sites. " +
               "Estimated: " + CurrencyUtil.formatBdt(estimatedBdt) + " for specialist transport.";
    }

    @Override
    public String getItineraryType() {
        return "EXTREME SPORTS ITINERARY";
    }
}