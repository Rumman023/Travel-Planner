package com.travel.strategy;

import com.travel.model.TravelerProfile;
import java.util.Arrays;
import java.util.List;


//Strategy - Concrete Strategy
public class BudgetStrategy implements RecommendationStrategy {

    @Override
    public List<String> recommendDestinations(TravelerProfile profile) {
        System.out.println("  [BudgetStrategy] Scanning cheapest destinations for budget: $" + profile.getBudget());
        return Arrays.asList(
            "Vietnam (Hanoi & Ha Long Bay)",
            "Thailand (Chiang Mai)",
            "Portugal (Porto)",
            "Colombia (Medellin)"
        );
    }

    @Override
    public List<String> recommendActivities(TravelerProfile profile) {
        return Arrays.asList(
            "Street food tours",
            "Free walking tours",
            "Public beach access",
            "Hostel social events",
            "Local market exploration"
        );
    }

    @Override
    public String getStrategyName() {
        return "Budget Optimization Strategy";
    }
}
