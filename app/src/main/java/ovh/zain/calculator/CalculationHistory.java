package ovh.zain.calculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;
import java.net.URISyntaxException;

import ovh.zain.calculator.tools.Keys;
import ovh.zain.calculator.tools.StringSaver;

public class CalculationHistory extends AppCompatActivity {
    EditText urlEditText;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_history);

        // Retrieve the result from SharedPreferences
        String result = StringSaver.getResult(getApplicationContext(), Keys.LAST_CALCULATION.toString());

        // Set the result in the TextView
        TextView resultTextView = findViewById(R.id.last_expression_tv);
        resultTextView.setText(result);

        urlEditText = findViewById(R.id.urlEditText);
        goButton = findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                Intent intent = new Intent(CalculationHistory.this, WebViewActivity.class);

                try {
                    URI uri = new URI(url);
                    String scheme = uri.getScheme();
                    if (scheme == null) {
                        url = "https://" + url;
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}

