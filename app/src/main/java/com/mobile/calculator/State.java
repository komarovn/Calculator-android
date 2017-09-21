package com.mobile.calculator;

public class State {
    private Operand leftValue = new Operand();
    private Operand rightValue = new Operand();
    private Operation operation = Operation.NONE;

    public boolean addSymbol(String symbol) {
        if (CalculatorUtils.containsOperationSign(symbol)) {
            return processOperation(Operation.parse(symbol));
        } else if (CalculatorUtils.SEPARATOR_SIGN.equals(symbol)) {
            processSeparator();
        } else if (CalculatorUtils.NUMBER_VALUES.contains(symbol)) {
            processNumber(Integer.valueOf(symbol));
        }
        return true;
    }

    private boolean processOperation(Operation operation) {
        boolean isProcessed = true;
        if (operation == Operation.ROOT) {
            setOperation(Operation.ROOT);
            isProcessed = processExpression();
        } else if (!leftValue.isEmpty()) {
            if (rightValue.isEmpty()) {
                setOperation(operation);
            } else {
                isProcessed = processExpression();
                setOperation(operation);
            }
        }
        return isProcessed;
    }

    private void processSeparator() {
        if (operation == Operation.NONE) {
            leftValue.setDecimal(true);
        } else {
            //if (!rightValue.isEmpty()) {
                rightValue.setDecimal(true);
            //}
        }
    }

    private void processNumber(int number) {
        if (operation == Operation.NONE) {
            leftValue.addNumber(number);
        } else {
            rightValue.addNumber(number);
        }
    }

    public void removeSymbol() {
        if (operation != Operation.NONE) {
            if (rightValue.isEmpty()) {
                setOperation(Operation.NONE);
            } else {
                rightValue.removeSymbol();
            }
        } else {
            leftValue.removeSymbol();
        }
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

    public boolean processExpression() {
        Double result = leftValue.getValue();
        try {
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

            if (result.equals(Double.NaN) ||
                    result.equals(Double.POSITIVE_INFINITY) ||
                    result.equals(Double.NEGATIVE_INFINITY)) {
                throw new Exception();
            }

            leftValue.setValue(result);
            rightValue = new Operand();
            setOperation(Operation.NONE);
        } catch (Exception e) {
            clear();
            return false;
        }
        return true;
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
