package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;

import ai.kun.socialdistancealarm.MenuActivity2;
import ai.kun.socialdistancealarm.R;

public class terms extends AppCompatActivity {
    private WebView mywebview, mywebviews;
    Button next;
    CheckBox cb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        mywebview = (WebView) findViewById(R.id.webView);
        next =findViewById(R.id.submit);
        cb1 = findViewById(R.id.agree);
        WebSettings webSettings = mywebview.getSettings();

        mywebview .requestFocus();

        mywebview .getSettings().setLightTouchEnabled(false);
        //mywebview .getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.setInitialScale(100);
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl("https://regimepadillo1234.wixsite.com/website/about-4");
        // mywebview.loadUrl("https://covid-19-app-17f14.web.app/");
        mywebview.setWebViewClient(new WebViewClient());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()){
                    Intent amphibiansActivityIntent = new Intent(terms.this, MenuActivity2.class);
                    startActivity(amphibiansActivityIntent);
                }

            }

        });
    }
}