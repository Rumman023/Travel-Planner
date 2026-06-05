package com.travel.factory;

import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.template.BaseItinerary;

// Factory - Abstract Factory Interface

public interface ItineraryFactory {
    BaseItinerary createItinerary(TravelerProfile profile, RecommendationStrategy strategy);
    String getFactoryType();
}
