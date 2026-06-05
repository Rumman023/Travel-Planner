package com.travel.util;

// Utility class for currency conversion from USD to Bangladeshi Taka.
// Conversion rate: 1 USD = 120 BDT

public class CurrencyUtil {
    private static final double USD_TO_BDT_RATE = 120.0;
    private static final String BDT_SYMBOL = "৳";

    public static double usdToBdt(double usdAmount) {
        return usdAmount * USD_TO_BDT_RATE;
    }

    public static String formatBdt(double bdtAmount) {
        return BDT_SYMBOL + String.format("%.0f", bdtAmount);
    }

    public static String formatBdtDecimal(double bdtAmount) {
        return BDT_SYMBOL + String.format("%.2f", bdtAmount);
    }

    public static double getConversionRate() {
        return USD_TO_BDT_RATE;
    }
}
