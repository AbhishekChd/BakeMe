package com.example.abhishek.bakeme.utils;

public class FormatHelper {
    // private constructor for static purpose
    private FormatHelper() {
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

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
}
