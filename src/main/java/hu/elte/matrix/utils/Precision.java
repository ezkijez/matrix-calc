package hu.elte.matrix.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Precision {

    /**
     * Rounds a value to n places.
     * Uses BigDecimal(String) constructor to prevent issue with inexact values.
     *
     * @param value double value
     * @param places required n precision
     * @return rounded value
     */
    public static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
