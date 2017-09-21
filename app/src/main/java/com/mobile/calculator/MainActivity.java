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
                addSymbol(CalculatorUtils.SEPARATOR_SIGN);
            }
        });
    }

    private void setNegateListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.negate();
                updateResult();
            }
        });
    }

    private void setBackspaceListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CalculatorUtils.INCORRECT_INPUT.equals(resultTextBox.getText())) {
                    state.clear();
                    updateResult();
                } else {
                    removeSymbol();
                }
            }
        });
    }

    private void setClearListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.clear();
                updateResult();
            }
        });
    }

    private void setOperationListener(Button button, final Operation operation) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSymbol(operation.toString());
            }
        });
    }

    private void setEqualsListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state.processExpression()) {
                    processIncorrectInput();
                } else {
                    updateResult();
                }
            }
        });
    }

    private void addSymbol(String symbol) {
        if (!state.addSymbol(symbol)) {
            processIncorrectInput();
        } else {
            updateResult();
        }
    }

    private void removeSymbol() {
        state.removeSymbol();
        updateResult();
    }

    public void updateResult() {
        if (this.resultTextBox == null) return;
        this.resultTextBox.setText(state.getViewValue());
    }

    public void processIncorrectInput() {
        this.resultTextBox.setText(CalculatorUtils.INCORRECT_INPUT);
    }

}
