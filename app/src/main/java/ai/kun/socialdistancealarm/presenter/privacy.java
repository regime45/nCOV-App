package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import ai.kun.socialdistancealarm.MainActivity;
import ai.kun.socialdistancealarm.MenuActivity2;
import ai.kun.socialdistancealarm.R;

public class privacy extends AppCompatActivity {
    private WebView mywebview, mywebviews;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        mywebview = (WebView) findViewById(R.id.webView);
        next =findViewById(R.id.next);
        WebSettings webSettings = mywebview.getSettings();

        mywebview .requestFocus();

        mywebview .getSettings().setLightTouchEnabled(false);
        //mywebview .getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.setInitialScale(100);
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl("https://regimepadillo1234.wixsite.com/website/copy-of-term-of-use");
        // mywebview.loadUrl("https://covid-19-app-17f14.web.app/");
        mywebview.setWebViewClient(new WebViewClient());

        // bbutton
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is exist
                   // Toast.makeText(privacy.this, "please register first",  Toast.LENGTH_LONG).show();
                    Intent amphibiansActivityIntent = new Intent(privacy.this, terms.class);
                    startActivity(amphibiansActivityIntent);
            }
        });
    }
}