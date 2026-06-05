package com.travel.decorator;

import com.travel.template.BaseItinerary;
import com.travel.model.TravelerProfile;
import java.util.List;

// decorator - Abstract Decorator
 
public abstract class ItineraryDecorator extends BaseItinerary {

    protected BaseItinerary wrappedItinerary;

    public ItineraryDecorator(BaseItinerary itinerary) {
        super(itinerary.getProfile(), null); 
        this.wrappedItinerary = itinerary;
    }

    public void printEnhancedSummary() {
        printServiceDetails();
    }

    public abstract void printServiceDetails();
    public abstract double getAdditionalCost();
    public abstract String getServiceName();

    @Override
    protected String buildTransportPlan() {
        return wrappedItinerary.getTransportPlan();
    }

    @Override
    public String getTransportPlan() {
        return wrappedItinerary.getTransportPlan();
    }

    @Override
    public String getItineraryType() {
        return wrappedItinerary.getItineraryType();
    }

    @Override
    public List<String> getSelectedDestinations() {
        return wrappedItinerary.getSelectedDestinations();
    }

    @Override
    public List<String> getBookedActivities() {
        return wrappedItinerary.getBookedActivities();
    }

    @Override
    public TravelerProfile getProfile() {
        return wrappedItinerary.getProfile();
    }
}