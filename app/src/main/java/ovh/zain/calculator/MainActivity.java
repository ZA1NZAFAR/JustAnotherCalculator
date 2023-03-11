package ovh.zain.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout myLayout = findViewById(R.id.farRightVertical);
        myLayout.addView(getEqualButton());


    }


    @SuppressLint("SetTextI18n")
    private void onEqualClick() {
        EditText myEditText = findViewById(R.id.editTextExpression);
        String expression = myEditText.getText().toString();

// ----------------------USING HANDLER
//        Handler handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                System.out.println("Result: " + msg.obj);
//                ((TextView) findViewById(R.id.textViewResult)).setText(msg.obj.toString());
//            }
//        };
//        try {
//            Calculator.evaluateWithHandler(expression, handler);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }


// ----------------------USING ASYNC TASK
//        Calculator.evaluateWithAsyncTask(expression, new Calculator.ResultCallback() {
//            @Override
//            public void onResult(double result) {
//                ((TextView) findViewById(R.id.textViewResult)).setText(Double.toString(result));
//            }
//        });


// ----------------------Final Solution
        Calculator.evaluateAsyncWithHandler(expression, new Calculator.CalculatorCallback() {
            @Override
            public void onSuccess(double result) {
                ((TextView) findViewById(R.id.textViewResult)).setText(Double.toString(result));
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




    private Button getEqualButton() {
        Button myButton = new Button(this);
        myButton.setId(R.id.buttonEqual);
        myButton.setText("\u003D");
        myButton.setTextSize(50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
        params.setMargins(5, 5, 5, 5);
        myButton.setLayoutParams(params);
        myButton.isActivated();
        myButton.setOnClickListener(v -> onEqualClick());
        return myButton;
    }

    @SuppressLint("SetTextI18n")
    public void insertVal(View v) {
        EditText myEditText = findViewById(R.id.editTextExpression);
        if (myEditText.getText().toString().startsWith("W"))
            myEditText.setText("");
        myEditText.setText(myEditText.getText().toString() + ((Button) v).getTag().toString());
    }

    public void removeLastCharacterInExpression(View v) {
        EditText myEditText = findViewById(R.id.editTextExpression);
        String expression = myEditText.getText().toString();
        if (expression.length() > 0) {
            myEditText.setText(expression.substring(0, expression.length() - 1));
        }
    }

    public void clearEverything(View v) {
        EditText myEditText = findViewById(R.id.editTextExpression);
        myEditText.setText("");
        ((TextView) findViewById(R.id.textViewResult)).setText("0.0");
    }

}