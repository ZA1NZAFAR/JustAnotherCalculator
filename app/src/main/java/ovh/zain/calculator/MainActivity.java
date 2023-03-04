package ovh.zain.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
        try {
            validateExpression(expression);
            double result = Calculator.evaluate(expression);
            ((TextView) findViewById(R.id.textViewResult)).setText(Double.toString(result));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void validateExpression(String expression) {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("Expression is empty");
        }
        if (expression.charAt(0) == '+' || expression.charAt(0) == '*' || expression.charAt(0) == '/') {
            throw new IllegalArgumentException("Expression cannot start with " + expression.charAt(0));
        }
        if (expression.charAt(expression.length() - 1) == '+' || expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/') {
            throw new IllegalArgumentException("Expression cannot end with " + expression.charAt(expression.length() - 1));
        }
        for (int i = 0; i < expression.length() - 1; i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                if (expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '/') {
                    throw new IllegalArgumentException("Expression cannot have two operators in a row");
                }
            }
        }
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
        EditText myEditText = (EditText) findViewById(R.id.editTextExpression);
        if (myEditText.getText().toString().startsWith("W"))
            myEditText.setText("");
        myEditText.setText(myEditText.getText().toString() + ((Button) v).getTag().toString());
    }

    public void removeLastCharacterInExpression(View v) {
        EditText myEditText = (EditText) findViewById(R.id.editTextExpression);
        String expression = myEditText.getText().toString();
        if (expression.length() > 0) {
            myEditText.setText(expression.substring(0, expression.length() - 1));
        }
    }

    public void clearEverything(View v) {
        EditText myEditText = (EditText) findViewById(R.id.editTextExpression);
        myEditText.setText("");
        ((TextView) findViewById(R.id.textViewResult)).setText("0.0");
    }

}