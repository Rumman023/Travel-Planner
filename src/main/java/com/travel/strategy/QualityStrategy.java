package com.travel.strategy;

import com.travel.model.TravelerProfile;
import java.util.Arrays;
import java.util.List;

// STRATEGY PATTERN - Concrete Strategy

public class QualityStrategy implements RecommendationStrategy {

    @Override
    public List<String> recommendDestinations(TravelerProfile profile) {
        System.out.println("  [QualityStrategy] Scoring destinations by price-to-quality ratio...");
        return Arrays.asList(
            "Costa Rica (4.7★ eco-lodges, mid-range pricing)",
            "Slovenia (4.8★ scenery, underrated pricing)",
            "South Korea (excellent value, 4.6★ infrastructure)",
            "Peru (Machu Picchu, high bang-for-buck)"
        );
    }

    @Override
    public List<String> recommendActivities(TravelerProfile profile) {
        return Arrays.asList(
            "Top-rated guided tours (min. 4.5 stars)",
            "Award-winning local restaurants",
            "Verified adventure operators",
            "Boutique hotel experiences"
        );
    }

    @Override
    public String getStrategyName() {
        return "Quality-Value Balance Strategy";
    }
}
