package com.example.abhishek.bakeme.utils;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;

public class FormatHelper {
    // private constructor for static purpose
    private FormatHelper() {
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    /**
     * Converts decimals to fraction String
     *
     * @param num The decimal point number to be converted
     * @return String type in form of fraction (1/2 for 0.5)
     */
    public static String convertDecimalToFraction(float num) {
        StringBuilder builder = new StringBuilder();
        int divider = 100;

        int integralPart = (int) num;
        int decimalPart = (int) ((num - integralPart) * divider);
        int gcd = gcd(decimalPart, divider);

        decimalPart /= gcd;
        divider /= gcd;

        if (integralPart != 0) {
            builder.append(integralPart)
                    .append(" ");
        }
        if (decimalPart != 0) {
            builder.append(decimalPart)
                    .append("/")
                    .append(divider);
        }

        return builder.toString();
    }

    /**
     * Creates a custom {@link SpannableStringBuilder} to format ingredient
     *
     * @param quantity   A floating point quantity for ingredient
     * @param measure    Measure unit of quantity
     * @param ingredient The ingredient measured
     * @return Custom {@link SpannableStringBuilder} formatted for better display 1.5g ingredient
     * as:
     * <p>
     * <b>1 <sup>1</sup>/<sub>2</sub> g</b> ingredient
     * </p>
     */
    public static SpannableStringBuilder convertIngredient(
            float quantity,
            String measure,
            String ingredient) {
        String quantityString = FormatHelper
                .convertDecimalToFraction(quantity);

        String quantityStringArray[] = quantityString.split(" ");

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        // Add the quantity to String first
        for (String s : quantityStringArray) {
            spannableStringBuilder.append(s);
        }

        // Edit spannable string for fractional units
        if (quantityString.contains("/")) {
            int start = 0;
            if (quantityStringArray.length == 2) {
                start = quantityStringArray[0].length();
            }
            spannableStringBuilder.setSpan(new SuperscriptSpan(), start, start + 1, 0);
            spannableStringBuilder.setSpan(new RelativeSizeSpan(0.65f), start, start + 3, 0);
            spannableStringBuilder.setSpan(new SubscriptSpan(), start + 2, start + 3, 0);
        }

        // Add units of quantity
        spannableStringBuilder
                .append(" ")
                .append(measure.toLowerCase());

        // Make quantity and unit bold
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableStringBuilder.length(), 0);

        // Finally add the ingredient
        spannableStringBuilder
                .append(" ")
                .append(ingredient);

        return spannableStringBuilder;
    }
}
