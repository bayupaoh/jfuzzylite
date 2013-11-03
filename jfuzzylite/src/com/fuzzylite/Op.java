/*   Copyright 2013 Juan Rada-Vilela

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.fuzzylite;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class Op {

    /*
     * Math Operations
     */
    public static boolean isEq(double a, double b) {
        return Math.abs(a - b) < FuzzyLite.getTolerance();
    }

    public static boolean isLt(double a, double b) {
        return !isEq(a, b) && a < b;
    }

    public static boolean isLE(double a, double b) {
        return isEq(a, b) || a < b;
    }

    public static boolean isGt(double a, double b) {
        return !isEq(a, b) && a > b;
    }

    public static boolean isGE(double a, double b) {
        return isEq(a, b) || a > b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        return a / b;
    }

    public static double modulo(double a, double b) {
        return a % b;
    }

    public static double logicalAnd(double a, double b) {
        return (isEq(a, 1.0) && isEq(b, 1.0)) ? 1.0 : 0.0;
    }
    
    public static double logicalOr(double a, double b){
        return (isEq(a, 1.0) || isEq(b, 1.0)) ? 1.0 : 0.0;
    }
    
    public static double negate(double x){
        return -x;
    }

    public static double scale(double x,
            double fromMin, double fromMax, double toMin, double toMax) {
        return (toMax - toMin) / (fromMax - fromMin) * (x - fromMin) + toMin;
    }

    public static double toDouble(String x) throws NumberFormatException {
        if ("nan".equals(x)) {
            return Double.NaN;
        }
        if ("inf".equals(x)) {
            return Double.POSITIVE_INFINITY;
        }
        if ("-inf".equals(x)) {
            return Double.NEGATIVE_INFINITY;
        }

        return Double.parseDouble(x);
    }

    /*
     * String Operations
     */
    public static <T extends Number> String str(T x) {
        String result = "";
        if (Double.isNaN(x.doubleValue())) {
            result = "nan";
        } else if (Double.isInfinite(x.doubleValue())) {
            if (isLt(x.doubleValue(), 0.0)) {
                result = "-";
            }
            result += "inf";
        } else if (isEq(x.doubleValue(), 0.0)) {
            result = FuzzyLite.getFormatter().format(0.0);
        } else {
            result = FuzzyLite.getFormatter().format(x);
        }
        return result;
    }

    public static <T> String join(List<T> x, String separator) {
        String result = "";
        for (Iterator<T> it = x.iterator(); it.hasNext();) {
            result += it.next();
            if (it.hasNext()) {
                result += separator;
            }
        }
        return result;
    }

    @SafeVarargs
    public static <T> String join(String separator, T... x) {
        String result = "";
        for (int i = 0; i < x.length; ++i) { 
            result += x[i].toString();
             if (i + 1 < x.length) {
                result += separator;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Double.valueOf("nan"));
    }
}
