package com.mobile.calculator;

public enum Operation {
    NONE(""),
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    ROOT("sr");

    private String value;

    Operation(String value) {
        this.value = value;
    }

    public static Operation parse(String value) {
        switch (value) {
            case "+":
                return ADDITION;
            case "-":
                return SUBTRACTION;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            case "sr":
                return ROOT;
        }
        return NONE;
    }

    @Override
    public String toString() {
        return value;
    }
}
