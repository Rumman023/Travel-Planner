# Travel-Planner
# Adventure Tours — Personalized Travel Itinerary Planner
### Design Patterns: Factory + Template Method + Strategy + Decorator

---

## Project Structure

```
src/main/java/com/travel/
├── model/
│   └── TravelerProfile.java          — Traveler data model
├── strategy/
│   ├── RecommendationStrategy.java   — Strategy interface
│   ├── BudgetStrategy.java           — Cheapest options
│   ├── QualityStrategy.java          — Price/quality balance
│   ├── LocalCultureStrategy.java     — Authentic experiences
│   └── AIRecommendationStrategy.java — ML-based recommendations
├── template/
│   ├── BaseItinerary.java            — Template Method base class
│   ├── BackpackerItinerary.java      — Budget/backpacker subclass
│   ├── PremiumItinerary.java         — Luxury subclass
│   ├── ExtremeSportsItinerary.java   — Adrenaline subclass
│   └── FamilyItinerary.java          — Family subclass
├── factory/
│   ├── ItineraryFactory.java         — Factory interface
│   └── ItineraryFactoryProvider.java — All concrete factories + provider
├── decorator/
│   ├── ItineraryDecorator.java       — Abstract decorator base
│   ├── TravelInsuranceDecorator.java — Adds travel insurance
│   ├── LocalGuideDecorator.java      — Adds local guide per city
│   ├── MealPlanDecorator.java        — Adds daily meal plan
│   ├── PhotoPackageDecorator.java    — Adds photography service
│   └── AirportTransferDecorator.java — Adds airport transfers
└── main/
    └── TravelPlannerApp.java         — Entry point / demo
```

---

## How to Compile and Run

### Prerequisites
- **Java 17+** (uses switch expressions). Verify with `java -version`.
- **iTextPDF 5.5.13.3 JAR** — Already included in `lib/` folder

### On Windows (PowerShell)

```powershell
# Navigate to project root
cd travel-planner

# Compile (use semicolon as classpath separator on Windows)
javac -cp ".;lib/itextpdf-5.5.13.3.jar" -d out @sources.txt

# Run
java -cp ".;out;lib/itextpdf-5.5.13.3.jar" com.travel.main.TravelPlannerApp
```

### On macOS / Linux

```bash
# Navigate to project root
cd travel-planner

# Compile (use colon as classpath separator on Mac/Linux)
javac -cp ".:lib/itextpdf-5.5.13.3.jar" -d out @sources.txt

# Run
java -cp ".:out:lib/itextpdf-5.5.13.3.jar" com.travel.main.TravelPlannerApp
```

---

## Characters & Scenarios

The project demonstrates itinerary planning through 4 scenarios with 3 travelers:

| Scenario | Traveler | Type | Budget | Duration | Add-ons |
|----------|----------|------|--------|----------|---------|
| 1 | **Miraj** | Adventure | ৳275,000 | 14 days | Insurance, Local Guide, Photography |
| 2 | **Rakib Family** | Family (4 people) | ৳660,000 | 10 days | Meal Plan, Airport Transfer |
| 3 | **Jamil** | Backpacker | ৳88,000 | 7 days | *Strategy Demo: Budget* |
| 4 | **Jamil** | Backpacker | ৳88,000 | 7 days | *Strategy Demo: Quality* |

---

## Currency

**All pricing is in Bangladeshi Taka (৳)**

- Conversion Rate: 1 USD = 110 BDT
- Applies to all budgets, service costs, and calculations
- Currency utilities in: `com.travel.util.CurrencyUtil`

---

## Output Export

After running the application, the following files are automatically generated:

### **TravelItinerary.pdf** (formatted report)
- Multi-page PDF with proper formatting
- Includes all scenarios and cost breakdowns

---

## Pattern Summary

| Pattern  | Role                                                          |
|----------|---------------------------------------------------------------|
| Factory  | Creates the right itinerary type from a traveler profile string |
| Template | Enforces 5-step pipeline; subclasses override specific steps  |
| Strategy | Pluggable recommendation algorithm, swappable at runtime      |
| Decorator| Stackable add-on services added after itinerary is built      |
