package com.travel.strategy;

import com.travel.model.TravelerProfile;
import java.util.List;

/**
 * Strategy - Interface
 * Defines the contract for all interchangeable recommendation algorithms.
 * Strategies can be swapped at runtime without changing the itinerary structure.
 */
public interface RecommendationStrategy {
    List<String> recommendDestinations(TravelerProfile profile);
    List<String> recommendActivities(TravelerProfile profile);
    String getStrategyName();
}
