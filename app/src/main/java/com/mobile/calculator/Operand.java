package com.mobile.calculator;

public class Operand {
    private Double value = 0.0;
    private boolean isDecimal = false;

    public void setValue(Double value) {
        this.value = value;
        this.isDecimal = !isFractionalPartEmpty();
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    public void setDecimal(boolean isDecimal) {
        this.isDecimal = isDecimal;
    }

    public boolean isEmpty() {
        return value.equals(0.0) && !isDecimal();
    }

    public Double getValue() {
        return value;
    }

    public void negate() {
        this.value = this.value * (-1);
    }

    public void addNumber(int number) {
        value = isDecimal ? Double.valueOf(String.valueOf(value.intValue()) + CalculatorUtils.SEPARATOR_SIGN + (isFractionalPartEmpty() ? "" : String.valueOf(getFractionalPart())) + String.valueOf(number)) :
                Double.valueOf(String.valueOf(value.intValue()) + String.valueOf(number));
    }

    public void removeSymbol() {
        String view = toString();
        view = view.substring(0, view.length() - 1);
        setDecimal(view.contains(CalculatorUtils.SEPARATOR_SIGN));
        if (view.isEmpty() || "-".equals(view)) {
            value = 0.0;
        } else {
            value = Double.valueOf(view);
        }
    }

    private boolean isFractionalPartEmpty() {
        return getFractionalPart().equals(CalculatorUtils.NUMBER_VALUES.get(0));
    }

    private String getFractionalPart() {
        String val = value.toString();
        return val.substring(val.indexOf(CalculatorUtils.SEPARATOR_SIGN) + 1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (isDecimal) {
            if (isFractionalPartEmpty()) {
                result.append(String.valueOf(value.intValue()));
                result.append(CalculatorUtils.SEPARATOR_SIGN);
            } else {
                result.append(value.toString());
            }
        } else {
            result.append(String.valueOf(value.intValue()));
        }
        return result.toString();
    }
}
