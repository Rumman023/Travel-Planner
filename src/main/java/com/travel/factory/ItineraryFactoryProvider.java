package com.travel.factory;
import com.travel.model.TravelerProfile;
import com.travel.strategy.RecommendationStrategy;
import com.travel.template.*;

// Factory: Concrete Factories



// Creates BackpackerItinerary for budget solo travelers.
class BudgetFactory implements ItineraryFactory {
    @Override
    public BaseItinerary createItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        System.out.println("[BudgetFactory] Creating BackpackerItinerary for " + profile.getName());
        return new BackpackerItinerary(profile, strategy);
    }
    @Override
    public String getFactoryType() { return "BudgetFactory"; }
}


// Creates PremiumItinerary for luxury travelers.
class LuxuryFactory implements ItineraryFactory {
    @Override
    public BaseItinerary createItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        System.out.println("[LuxuryFactory] Creating PremiumItinerary for " + profile.getName());
        return new PremiumItinerary(profile, strategy);
    }
    @Override
    public String getFactoryType() { return "LuxuryFactory"; }
}

// Creates ExtremeSportsItinerary for adventurous
class AdventureFactory implements ItineraryFactory {
    @Override
    public BaseItinerary createItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        System.out.println("[AdventureFactory] Creating ExtremeSportsItinerary for " + profile.getName());
        return new ExtremeSportsItinerary(profile, strategy);
    }
    @Override
    public String getFactoryType() { return "AdventureFactory"; }
}


//Creates FamilyItinerary for families traveling together.
class FamilyTravelFactory implements ItineraryFactory {
    @Override
    public BaseItinerary createItinerary(TravelerProfile profile, RecommendationStrategy strategy) {
        System.out.println("[FamilyTravelFactory] Creating FamilyItinerary for " + profile.getName());
        return new FamilyItinerary(profile, strategy);
    }
    @Override
    public String getFactoryType() { return "FamilyTravelFactory"; }
}


// Factory Provider
public class ItineraryFactoryProvider {

    public static ItineraryFactory getFactory(String travelerType) {
        return switch (travelerType.toLowerCase()) {
            case "backpacker", "budget" -> new BudgetFactory();
            case "luxury", "premium"   -> new LuxuryFactory();
            case "adventure", "extreme" -> new AdventureFactory();
            case "family"              -> new FamilyTravelFactory();
            default -> throw new IllegalArgumentException("Unknown traveler type: " + travelerType);
        };
    }
}
