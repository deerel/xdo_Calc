package se.xdo.prelab01;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    //Setting up array with all buttons
    private int[] numButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    private int[] opButtons = {R.id.buttonPlus, R.id.buttonMinus, R.id.buttonTimes, R.id.buttonDivide};
    private TextView sumDisplay;
    private String leftNum, rightNum, operand;
    private boolean atStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Force app to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        sumDisplay = (TextView) findViewById(R.id.textView);
        fixFontSize();
        setNumericOnClickListener();
        setOperatorOnClickListener();
        clearAll();
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                //If operand is given or not
                if("".equals(operand)) {
                    //If digit is given or not
                    if(atStart) {
                        leftNum = button.getText().toString();
                    } else {
                        leftNum = leftNum + button.getText().toString();
                    }
                    sumDisplay.setText(leftNum);
                    atStart=false;
                } else {
                    rightNum = rightNum + button.getText().toString();
                    sumDisplay.setText(rightNum);
                }

                fixFontSize();
            }
        };

        for (int id : numButtons) {
                findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Execute expression evaluation if there are a right number
                if(!"".equals(rightNum)) {
                    onEqual(false);
                }
                Button button = (Button) v;
                operand = button.getText().toString();
            }
        };

        for (int id : opButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual(true);
            }
        });
    }

    private void onEqual(Boolean equal) {
        //If equals is pressed and expression not is complete, do nothing.
        if(!isCompleteExpression()) {
            return;
        }
        String txt = leftNum + operand +  rightNum;
        Expression expression = new ExpressionBuilder(txt).build();
        String sumText = "";
        double result = 0;
        try {
            result = expression.evaluate();
            sumText = Double.toString(result);
            sumDisplay.setText(sumText);
            leftNum = sumText;
        } catch (Exception e) {
            clearAll();
            sumDisplay.setText(R.string.error);
        }

        fixFontSize();
        clear(equal);


    }

    private boolean isCompleteExpression() {
        if("".equals(leftNum) || "".equals(rightNum) || "".equals(operand)) {
            return false;
        }
        return true;
    }

    private void fixFontSize() {
        if(sumDisplay.length()>11 && sumDisplay.length()<=20) {
            sumDisplay.setTextSize(44-(sumDisplay.length()-11)*2.2f);
        } else {
            sumDisplay.setTextSize(44);
        }
    }

    private void clearAll() {
        leftNum = "0";
        rightNum = "";
        operand = "";
        atStart = true;
    }

    private void clear(Boolean equal) {
        rightNum = "";
        atStart = true;

        if(equal) {
            operand = "";
        }
    }
}