package com.travel.model;

import java.util.List;

// Data model to represent traveler's profile and preferences. Used by strategies, factories, and template steps
public class TravelerProfile {
    private String name;
    private String travelerType;  
    private double budget;
    private int durationDays;
    private List<String> pastTrips;
    private List<String> interests;
    private int groupSize;

    public TravelerProfile(String name, String travelerType, double budget,
                           int durationDays, List<String> pastTrips,
                           List<String> interests, int groupSize) {
        this.name = name;
        this.travelerType = travelerType;
        this.budget = budget;
        this.durationDays = durationDays;
        this.pastTrips = pastTrips;
        this.interests = interests;
        this.groupSize = groupSize;
    }

    public String getName()             { return name; }
    public String getTravelerType()     { return travelerType; }
    public double getBudget()           { return budget; }
    public int getDurationDays()        { return durationDays; }
    public List<String> getPastTrips()  { return pastTrips; }
    public List<String> getInterests()  { return interests; }
    public int getGroupSize()           { return groupSize; }
}
