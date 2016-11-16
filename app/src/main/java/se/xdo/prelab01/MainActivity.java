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

public class MainActivity extends AppCompatActivity {
    //
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

    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Log.d("STATE", "Numeric Button Was Pressed");
                if(operand == "") {
                    if(atStart==true) {
                        leftNum = button.getText().toString();
                    } else {
                        leftNum = leftNum + button.getText().toString();
                    }
                    txtScreen.setText(leftNum);
                    atStart=false;
                    Log.d("STATE", "LEFT" + leftNum );
                } else {
                    rightNum = rightNum + button.getText().toString();
                    txtScreen.setText(rightNum);
                    Log.d("STATE", "RIGHT" + rightNum );
                }
                }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

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
        if(leftNum=="" || rightNum == "" || operand == "") {
            return;
        }
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

    private void onEqual() {
        if(leftNum=="" || rightNum == "" || operand == "") {
            return;
        }
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
    }
}