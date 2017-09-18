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
        return intPart.equals(0) && fracPart.equals(0) && !isDecimal();
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

    public void removeSymbol() {
        if (fracPart.equals(0)) {
            if (isDecimal) {
                setDecimal(false);
            } else {
                setIntPart(removeLastDigit(intPart));
            }
        } else {
            setFracPart(removeLastDigit(fracPart));
        }
    }

    private Integer removeLastDigit(Integer val) {
        String view = val.toString();
        view = view.substring(0, view.length() - 1);
        if (view.isEmpty() || "-".equals(view)) {
            return 0;
        } else {
            return Integer.valueOf(view);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(intPart.toString());
        if (isDecimal) {
            result.append(CalculatorUtils.SEPARATOR_SIGN);
            if (!fracPart.equals(0)) {
                result.append(fracPart);
            }
        }
        return result.toString();
    }
}
