package com.travel.template;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.util.CurrencyUtil;
import java.util.ArrayList;
import java.util.List;

// TEMPLATE PATTERN (abstract base class)

public abstract class BaseItinerary {

    protected TravelerProfile profile;
    protected RecommendationStrategy strategy;
    protected List<String> selectedDestinations = new ArrayList<>();
    protected List<String> bookedActivities = new ArrayList<>();
    protected String transportPlan;

    public BaseItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        this.profile = profile;
        this.strategy = strategy;
    }

    //Template Method
    public final void buildItinerary() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║       BUILDING ITINERARY - " + getItineraryType());
        System.out.println("╚══════════════════════════════════════════════╝");

        gatherPreferences();       // Step 1: fixed
        selectDestinations();      // Step 2: overridable (hook)
        bookActivities();          // Step 3: overridable (hook)
        arrangeTransport();        // Step 4: fixed
        generateItineraryReport(); // Step 5: fixed
    }

    // Fixed steps: same for all types

    private void gatherPreferences() {
        System.out.println("\n[Step 1] Gathering preferences for: " + profile.getName());
        System.out.println("  > Traveler type : " + profile.getTravelerType());
        System.out.println("  > Budget        : " + CurrencyUtil.formatBdt(profile.getBudget()));
        System.out.println("  > Duration      : " + profile.getDurationDays() + " days");
        System.out.println("  > Group size    : " + profile.getGroupSize());
        System.out.println("  > Interests     : " + profile.getInterests());
    }

    private void arrangeTransport() {
        System.out.println("\n[Step 4] Arranging transport...");
        this.transportPlan = buildTransportPlan();
        System.out.println("  > " + transportPlan);
    }

    private void generateItineraryReport() {
        System.out.println("\n[Step 5] Generating Itinerary Report...");
        System.out.println("  ----------------------------------------");
        System.out.println("  Strategy used   : " + strategy.getStrategyName());
        System.out.println("  Destinations    : " + selectedDestinations);
        System.out.println("  Activities      : " + bookedActivities);
        System.out.println("  Transport       : " + transportPlan);
        System.out.println("  ----------------------------------------");
    }

    // Hooks: subclasses override to customize behavior
    protected void selectDestinations() {
        System.out.println("\n[Step 2] Selecting destinations via " + strategy.getStrategyName() + "...");
        selectedDestinations = strategy.recommendDestinations(profile);
        System.out.println("  > Destinations selected: " + selectedDestinations);
    }

    protected void bookActivities() {
        System.out.println("\n[Step 3] Booking activities via " + strategy.getStrategyName() + "...");
        bookedActivities = strategy.recommendActivities(profile);
        System.out.println("  > Activities booked: " + bookedActivities);
    }


    protected abstract String buildTransportPlan();
    public abstract String getItineraryType();

    // Getters — used by Decorators
    public List<String> getSelectedDestinations() { return selectedDestinations; }
    public List<String> getBookedActivities()      { return bookedActivities; }
    public String getTransportPlan()               { return transportPlan; }
    public TravelerProfile getProfile()            { return profile; }
}