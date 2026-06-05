package com.travel.strategy;

import com.travel.model.TravelerProfile;
import java.util.Arrays;
import java.util.List;


// STRATEGY PATTERN - Concrete Strategy

public class LocalCultureStrategy implements RecommendationStrategy {

    @Override
    public List<String> recommendDestinations(TravelerProfile profile) {
        System.out.println("  [LocalCultureStrategy] Finding off-the-beaten-path destinations...");
        return Arrays.asList(
            "Oaxaca, Mexico (artisan culture)",
            "Luang Prabang, Laos (Buddhist heritage)",
            "Tbilisi, Georgia (ancient wine culture)",
            "Fez, Morocco (medieval medina)"
        );
    }

    @Override
    public List<String> recommendActivities(TravelerProfile profile) {
        return Arrays.asList(
            "Cooking class with local families",
            "Traditional craft workshops",
            "Village homestay experience",
            "Local festival participation",
            "Language exchange meetups"
        );
    }

    @Override
    public String getStrategyName() {
        return "Local Culture Immersion Strategy";
    }
}
