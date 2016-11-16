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

    private int[] numericButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    private int[] operatorButtons = {R.id.buttonPlus, R.id.buttonMinus, R.id.buttonTimes, R.id.buttonDivide};
    private TextView txtScreen;
    private String leftNum, rightNum, operand;
    private boolean atStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Force app to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        this.txtScreen = (TextView) findViewById(R.id.textView);
        setNumericOnClickListener();
        setOperatorOnClickListener();
        leftNum = "";
        rightNum = "";
        operand = "";
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if("".equals(operand)) {
                    if(atStart) {
                        leftNum = button.getText().toString();
                    } else {
                        leftNum = leftNum + button.getText().toString();
                    }
                    txtScreen.setText(leftNum);
                    atStart=false;
                } else {
                    rightNum = rightNum + button.getText().toString();
                    txtScreen.setText(rightNum);
                }
            }
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(rightNum)) {
                    onEqual(false);
                }
                Button button = (Button) v;
                operand = button.getText().toString();
            }
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual(true);
            }
        });
    }

    /*
    private void opEqual(Boolean equal) {
        if("".equals(leftNum) || "".equals(rightNum) || "".equals(operand)) {
            return;
        }
        String txt = leftNum + operand +  rightNum;
        Expression expression = new ExpressionBuilder(txt).build();
        double result = expression.evaluate();
        txtScreen.setText(Double.toString(result));
        leftNum = Double.toString(result);
        rightNum = "";
        atStart = true;
    }
    */

    private void onEqual(Boolean equal) {
        if("".equals(leftNum) || "".equals(rightNum) || "".equals(operand)) {
            return;
        }

        String txt = leftNum + operand +  rightNum;
        Expression expression = new ExpressionBuilder(txt).build();
        try {
            double result = expression.evaluate();
            txtScreen.setText(Double.toString(result));
            leftNum = Double.toString(result);
        } catch (Exception e) {
            leftNum = "";
            rightNum = "";
            operand = "";
            atStart = true;
            txtScreen.setText(R.string.error);
        }

        rightNum = "";
        atStart = true;
        if(equal) {
            operand = "";
        }

    }
}