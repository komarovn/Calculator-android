package com.mobile.calculator;

import java.util.Arrays;
import java.util.List;

public abstract class CalculatorUtils {

    public static final List<String> NUMBER_VALUES = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final String SEPARATOR_SIGN = ".";
    public static final String EMPTY_VALUE = "0";
    public static final String INCORRECT_INPUT = "Error";

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() || value.equals(NUMBER_VALUES.get(0));
    }

    public static boolean containsOperationSign(String value) {
        return value.contains(Operation.ADDITION.toString()) ||
                value.contains(Operation.MULTIPLICATION.toString()) ||
                value.contains(Operation.DIVISION.toString()) ||
                value.contains(Operation.SUBTRACTION.toString()) ||
                value.contains(Operation.ROOT.toString());
    }

    public static boolean containsNegateSign(String value) {
        return value.contains("-");
    }

}
