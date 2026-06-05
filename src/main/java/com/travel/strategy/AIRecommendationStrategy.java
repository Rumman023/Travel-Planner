package com.travel.strategy;

import com.travel.model.TravelerProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Strategy - Concrete Strategy
public class AIRecommendationStrategy implements RecommendationStrategy {

    @Override
    public List<String> recommendDestinations(TravelerProfile profile) {
        System.out.println("  [AIRecommendationStrategy] Running ML model on traveler profile...");
        System.out.println("  [AIRecommendationStrategy] Analyzing past trips: " + profile.getPastTrips());
        System.out.println("  [AIRecommendationStrategy] Matching interest vectors...");

        // Point to be noted: here no real AI models/api is used, just hardcoded logic to simulate the behavior of an AI-based recommendation system.
        List<String> recommendations = new ArrayList<>();
        if (profile.getPastTrips().contains("Asia")) {
            recommendations.add("Bhutan (new frontier based on Asia affinity)");
            recommendations.add("Kyrgyzstan (nomadic adventure)");
        } else {
            recommendations.add("Japan (high predicted satisfaction: 94%)");
            recommendations.add("New Zealand (adventure match score: 91%)");
        }
        recommendations.add("Iceland (trending among similar traveler profiles)");
        return recommendations;
    }

    @Override
    public List<String> recommendActivities(TravelerProfile profile) {
        return Arrays.asList(
            "AI-curated hidden gem restaurant tour",
            "Personalized hiking trail based on fitness level",
            "Photography spots matched to golden hour schedule",
            "Social activity matched to traveler personality type"
        );
    }

    @Override
    public String getStrategyName() {
        return "AI-Powered Personalization Strategy";
    }
}
