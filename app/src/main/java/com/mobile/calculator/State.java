package com.mobile.calculator;

public class State {
    private Operand leftValue = new Operand();
    private Operand rightValue = new Operand();
    private Operation operation = Operation.NONE;

    public void addSymbol(String symbol) {
        if (CalculatorUtils.containsOperationSign(symbol)) {
            processOperation(Operation.parse(symbol));
        } else if (CalculatorUtils.SEPARATOR_SIGN.equals(symbol)) {
            processSeparator();
        } else if (CalculatorUtils.NUMBER_VALUES.contains(symbol)) {
            processNumber(Integer.valueOf(symbol));
        }
    }

    private void processOperation(Operation operation) {
        if (operation == Operation.ROOT) {
            processExpression();
        }
        if (!leftValue.isEmpty()) {
            if (rightValue.isEmpty()) {
                setOperation(operation);
            } else {
                processExpression();
                setOperation(operation);
            }
        }
    }

    private void processSeparator() {
        if (operation == Operation.NONE) {
            leftValue.setDecimal(true);
        } else {
            if (!rightValue.isEmpty()) {
                rightValue.setDecimal(true);
            }
        }
    }

    private void processNumber(int number) {
        if (operation == Operation.NONE) {
            leftValue.addNumber(number);
        } else {
            rightValue.addNumber(number);
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean isEmpty() {
        return leftValue.isEmpty() && rightValue.isEmpty();
    }

    public String getViewValue() {
        StringBuilder result = new StringBuilder();
        if (!leftValue.isEmpty()) {
            result.append(leftValue.toString());
            if (operation != Operation.NONE) {
                result.append(operation.toString());
                if (!rightValue.isEmpty()) {
                    result.append(rightValue.toString());
                }
            }
        } else {
            result.append(CalculatorUtils.EMPTY_VALUE);
        }
        return result.toString();
    }

    public void negate() {
        if (operation == Operation.NONE) {
            leftValue.negate();
        } else if (operation == Operation.ADDITION) {
            operation = Operation.SUBTRACTION;
        } else if (operation == Operation.SUBTRACTION) {
            operation = Operation.ADDITION;
        } else if (operation == Operation.MULTIPLICATION || operation == Operation.DIVISION) {
            leftValue.negate();
        }
    }

    public void processExpression() {
        Double result = 0.0;
        switch (operation) {
            case ADDITION:
                result = processAddition();
                break;
            case SUBTRACTION:
                result = processSubtraction();
                break;
            case MULTIPLICATION:
                result = processMultiplication();
                break;
            case DIVISION:
                result = processDivision();
                break;
            case ROOT:
                result = processRoot();
                break;
        }

        String val = result.toString();
        Integer intPart = result.intValue(); //Integer.valueOf(val.substring(0, val.indexOf(CalculatorUtils.SEPARATOR_SIGN)));
        Integer fracPart = Integer.valueOf(val.substring(val.indexOf(CalculatorUtils.SEPARATOR_SIGN) + 1));
        //if (leftValue.isDecimal() || rightValue.isDecimal() || fracPart.equals(0)) {
            leftValue.setDecimal(!fracPart.equals(0));
            leftValue.setIntPart(intPart);
            leftValue.setFracPart(fracPart);
       /* } else {
            leftValue.setDecimal(false);
            leftValue.setIntPart(intPart);
            leftValue.setFracPart(0);
        }*/
        rightValue = new Operand();
        setOperation(Operation.NONE);
    }

    private Double processAddition() {
        return leftValue.getValue() + rightValue.getValue();
    }

    private Double processSubtraction() {
        return leftValue.getValue() - rightValue.getValue();
    }

    private Double processMultiplication() {
        return leftValue.getValue() * rightValue.getValue();
    }

    private Double processDivision() {
        return leftValue.getValue() / rightValue.getValue();
    }

    private Double processRoot() {
        return Math.sqrt(leftValue.getValue());
    }

    public void clear() {
        this.operation = Operation.NONE;
        this.leftValue = new Operand();
        this.rightValue = new Operand();
    }
}
