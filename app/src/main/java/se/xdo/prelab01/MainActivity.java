package se.xdo.prelab01;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.Console;


public class MainActivity extends AppCompatActivity {
    // IDs of all the numeric buttons
    private int[] numericButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.buttonPlus, R.id.buttonMinus, R.id.buttonTimes, R.id.buttonDivide};
    // TextView used to display the output
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    // Represent that current state is in error or not
    private boolean stateError;

    private String leftNum, rightNum, operand;
    private boolean atStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        // Find the TextView
        this.txtScreen = (TextView) findViewById(R.id.textView);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener();
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener();
        leftNum = "";
        rightNum = "";
        operand = "";
    }

    /**
     * Find and set OnClickListener to numeric buttons.
     */
    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                // Just append/set the text of clicked button
                Button button = (Button) v;
                if (stateError || atStart) {
                    // If current state is Error, replace the error message
                    txtScreen.setText(button.getText());
                    stateError = atStart = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    txtScreen.append(button.getText());
                }*/

                Button button = (Button) v;
                Log.d("STATE", "Numeric Button Was Pressed");
                if(operand == "") {
                    leftNum = button.getText().toString();
                    txtScreen.setText(button.getText());
                    Log.d("STATE", "LEFT" + leftNum );
                } else {
                    rightNum = button.getText().toString();
                    Log.d("STATE", "RIGHT" + rightNum );
                }
                }
                // Set the flag
                //lastNumeric = true;

        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.
     */
    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                Log.d("STATE", "Operand Button Was Pressed");
                if(rightNum != "") {
                    opEqual();
                }
                Button button = (Button) v;
                operand = button.getText().toString();
                Log.d("STATE", operand );
                /*if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    operand = button.getText().toString();
                    Log.d("STATE", operand );
                    lastNumeric = false;
                }*/
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        // Equal button
        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void opEqual() {
        Log.d("STATE", "OP Equal heard!");
        Log.d("STATE", leftNum + operand +  rightNum);
        String txt = leftNum + operand +  rightNum;
        Expression expression = new ExpressionBuilder(txt).build();
        double result = expression.evaluate();
        txtScreen.setText(Double.toString(result));
        leftNum = Double.toString(result);
        rightNum = "";
        atStart = true;
    }

    /**
     * Logic to calculate the solution.
     */
    private void onEqual() {
        Log.d("STATE", "EQ Equal heard!");
        Log.d("STATE", leftNum + operand +  rightNum);
        String txt = leftNum + operand +  rightNum;
        Expression expression = new ExpressionBuilder(txt).build();
        double result = expression.evaluate();
        txtScreen.setText(Double.toString(result));
        leftNum = Double.toString(result);
        rightNum = "";
        operand = "";
        atStart = true;
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        /*
        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = Double.toString(leftNum) + operand +  Double.toString(rightNum);
            // Create an Expression (A class from exp4j library)
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                double result = expression.evaluate();
                txtScreen.setText(Double.toString(result));
            } catch (ArithmeticException ex) {
                // Display an error message
                txtScreen.setText("Error!");
                stateError = true;
                lastNumeric = false;
            }
            operand = "";
            atStart = true;
        }
        */
    }
}