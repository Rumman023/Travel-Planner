package com.travel.template;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.util.CurrencyUtil;

//Template Pattern: Concrete Class
public class PremiumItinerary extends BaseItinerary {

    private static final double TRANSPORT_BUDGET_RATIO = 0.35;

    public PremiumItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        super(profile, strategy);
    }

    @Override
    protected void selectDestinations() {
        System.out.println("\n[Step 2] Selecting LUXURY destinations (5-star filter applied)...");
        selectedDestinations = new java.util.ArrayList<>(strategy.recommendDestinations(profile));
        selectedDestinations.add("Maldives (private overwater villa)");
        selectedDestinations.add("Amalfi Coast, Italy (VIP access)");
        System.out.println("  > VIP-filtered destinations: " + selectedDestinations);
    }

    @Override
    protected void bookActivities() {
        System.out.println("\n[Step 3] Booking LUXURY activities (private, exclusive access)...");
        bookedActivities = new java.util.ArrayList<>(strategy.recommendActivities(profile));
        bookedActivities.add("Private yacht charter");
        bookedActivities.add("Michelin-star dining reservation");
        bookedActivities.add("Personal concierge service");
        System.out.println("  > Premium activities booked: " + bookedActivities);
    }

    @Override
    protected String buildTransportPlan() {
        double transportBudgetBdt = profile.getBudget() * TRANSPORT_BUDGET_RATIO;
        return "Premium transport: business class flights, private transfers. " +
               "Budget allocated: " + CurrencyUtil.formatBdt(transportBudgetBdt) + " for transport.";
    }

    @Override
    public String getItineraryType() {
        return "PREMIUM LUXURY ITINERARY";
    }
}