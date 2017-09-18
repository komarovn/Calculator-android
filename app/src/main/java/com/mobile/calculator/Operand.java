package com.mobile.calculator;

public class Operand {
    private Integer intPart = 0;
    private Integer fracPart = 0;
    private boolean isDecimal = false;

    public void setIntPart(Integer intPart) {
        this.intPart = intPart;
    }

    public void setFracPart(Integer fracPart) {
        this.fracPart = fracPart;
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    public void setDecimal(boolean isDecimal) {
        this.isDecimal = isDecimal;
    }

    public boolean isEmpty() {
        return intPart.equals(0) && fracPart.equals(0);
    }

    public Double getValue() {
        return Double.valueOf(toString());
    }

    public void negate() {
        this.intPart = this.intPart * (-1);
    }

    public void addNumber(int number) {
        if (isDecimal) {
            fracPart = Integer.valueOf(String.valueOf(fracPart) + String.valueOf(number));
        } else {
            intPart = Integer.valueOf(String.valueOf(intPart) + String.valueOf(number));
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(intPart.toString());
        if (isDecimal) {
            result.append(".");
            if (!fracPart.equals(0)) {
                result.append(fracPart);
            }
        }
        return result.toString();
    }
}
