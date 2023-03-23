package ovh.zain.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activvity_web_view);

        webView = findViewById(R.id.wbView);
        webView.setWebViewClient(new WebViewClient());

        String url = getIntent().getData().toString();
        System.out.println("URL: " + url);
        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);

    }

}

