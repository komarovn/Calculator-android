package com.mobile.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Button> numberButtons = new ArrayList<Button>();
    private TextView resultTextBox;
    private String viewValue = CalculatorUtils.EMPTY_VALUE;
    private State state = new State();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.resultTextBox = (TextView) findViewById(R.id.result);
        updateResult();

        numberButtons.add((Button) findViewById(R.id.zero));
        numberButtons.add((Button) findViewById(R.id.one));
        numberButtons.add((Button) findViewById(R.id.two));
        numberButtons.add((Button) findViewById(R.id.three));
        numberButtons.add((Button) findViewById(R.id.four));
        numberButtons.add((Button) findViewById(R.id.five));
        numberButtons.add((Button) findViewById(R.id.six));
        numberButtons.add((Button) findViewById(R.id.seven));
        numberButtons.add((Button) findViewById(R.id.eight));
        numberButtons.add((Button) findViewById(R.id.nine));

        for (int i = 0; i < numberButtons.size(); i++) {
            setNumberListener(numberButtons.get(i), CalculatorUtils.NUMBER_VALUES.get(i));
        }

        Button separator = (Button) findViewById(R.id.comma);
        setSeparatorListener(separator);

        Button negate = (Button) findViewById(R.id.plusMinus);
        setNegateListener(negate);

        Button backspace = (Button) findViewById(R.id.backspace);
        setBackspaceListener(backspace);

        Button clear = (Button) findViewById(R.id.clear);
        setClearListener(clear);

        Button equals = (Button) findViewById(R.id.equals);
        setEqualsListener(equals);

        Button plus = (Button) findViewById(R.id.plus);
        Button minus = (Button) findViewById(R.id.minus);
        Button multiply = (Button) findViewById(R.id.multiply);
        Button divide = (Button) findViewById(R.id.divide);
        Button squareRoot = (Button) findViewById(R.id.squareRoot);

        setOperationListener(plus, Operation.ADDITION);
        setOperationListener(minus, Operation.SUBTRACTION);
        setOperationListener(multiply, Operation.MULTIPLICATION);
        setOperationListener(divide, Operation.DIVISION);
        setOperationListener(squareRoot, Operation.ROOT);
    }

    private void setNumberListener(Button button, final String value) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSymbol(value);
            }
        });
    }

    private void setSeparatorListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!viewValue.contains(CalculatorUtils.SEPARATOR_SIGN)) {
                    addSymbol(CalculatorUtils.SEPARATOR_SIGN);
                }*/
                addSymbol(CalculatorUtils.SEPARATOR_SIGN);
            }
        });
    }

    private void setNegateListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (state.getRightValue().equals(0.0)) {
                    state.setLeftValue(state.getLeftValue() * (-1));
                } else {
                    state.setRightValue(state.getRightValue() * (-1));
                }
                updateResult();*/
                /*if (!CalculatorUtils.isEmpty(viewValue) && state.getOperation() != Operation.SUBTRACTION) {
                    if (CalculatorUtils.containsNegateSign(viewValue)) {
                        viewValue = viewValue.substring(1, viewValue.length());
                    } else {
                        viewValue = "-" + viewValue;
                    }
                    updateResult();
                }*/
                state.negate();
                updateResult();
            }
        });
    }

    private void setBackspaceListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CalculatorUtils.isEmpty(viewValue)) {
                    removeSymbol();
                }
            }
        });
    }

    private void setClearListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*viewValue = CalculatorUtils.EMPTY_VALUE;
                state.setLeftValue(0.0);
                state.setRightValue(0.0);
                state.setOperation(Operation.NONE);*/
                state.clear();
                updateResult();
            }
        });
    }

    private void setOperationListener(Button button, final Operation operation) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*switch (operation) {
                    case ADDITION:
                        if (state.getOperation() != Operation.NONE) {
                            if (!state.getRightValue().equals(0.0)) {
                                //calculate
                            } else {
                                state.setOperation(operation);
                                removeSymbol();
                                addSymbol(operation.toString());
                            }
                        } else {
                            state.setOperation(operation);
                            addSymbol(operation.toString());
                        }
                        state.setOperation(operation);
                        if (!CalculatorUtils.containsOperationSign(viewValue)) {
                            addSymbol(operation.toString());
                        }
                        break;
                    case SUBTRACTION:
                        if (!CalculatorUtils.containsOperationSign(viewValue)) {
                            addSymbol("-");
                        }
                        break;
                    case MULTIPLICATION:
                        if (!CalculatorUtils.containsOperationSign(viewValue)) {
                            addSymbol("*");
                        }
                        break;
                    case DIVISION:
                        if (!CalculatorUtils.containsOperationSign(viewValue)) {
                            addSymbol("/");
                        }
                        break;
                    case ROOT:
                        break;
                }*/
                addSymbol(operation.toString());
            }
        });
    }

    private void setEqualsListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state.processExpression()) {
                    resultTextBox.setText(CalculatorUtils.INCORRECT_INPUT);
                } else {
                    updateResult();
                }
            }
        });
    }

    private void addSymbol(String symbol) {
        if (!state.addSymbol(symbol)) {
            resultTextBox.setText(CalculatorUtils.INCORRECT_INPUT);
        } else {
            updateResult();
        }

        /*if (CalculatorUtils.containsOperationSign(symbol)) {
            if (!state.isEmpty()) {
                state.setOperation(Operation.parse(symbol));
            }
        } else if (CalculatorUtils.SEPARATOR_SIGN.equals(symbol)) {
            if (state.getRightValue().equals(0.0)) {

            }
        } else {
            StringBuilder result = new StringBuilder();

            if (state.getOperation() == Operation.NONE) {
                if (!state.getLeftValue().equals(0.0)) {
                    result.append(String.valueOf(state.getLeftValue()));
                }
                if (CalculatorUtils.NUMBER_VALUES.contains(symbol)) {
                    result.append(symbol);
                }
                state.setLeftValue(Double.valueOf(result.toString()));
            }

            if (!CalculatorUtils.isEmpty(viewValue) || symbol.equals(CalculatorUtils.SEPARATOR_SIGN)) {
                result.append(viewValue);
            }
            result.append(symbol);
            viewValue = result.toString();
            updateResult();
        }*/
    }

    private void removeSymbol() {
        if (!CalculatorUtils.isEmpty(viewValue)) {
            if (viewValue.length() > 1) {
                if (viewValue.length() == 2 && CalculatorUtils.containsNegateSign(viewValue)) {
                    viewValue = CalculatorUtils.EMPTY_VALUE;
                } else {
                    viewValue = viewValue.substring(0, viewValue.length() - 1);
                }
            } else {
                viewValue = CalculatorUtils.EMPTY_VALUE;
            }
        }
        updateResult();
    }

    public void updateResult() {
        if (this.resultTextBox == null) return;
        //this.resultTextBox.setText(viewValue);
        this.resultTextBox.setText(state.getViewValue());
    }

    public void processIncorrectInput() {
        this.resultTextBox.setText(CalculatorUtils.INCORRECT_INPUT);
    }

}
