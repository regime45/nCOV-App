package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ai.kun.socialdistancealarm.R;

public class ph extends AppCompatActivity {
    private WebView mywebview, mywebviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph);
        mywebview = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl("https://doh.gov.ph/covid19tracker");
        // mywebview.loadUrl("https://covid-19-app-17f14.web.app/");
        mywebview.setWebViewClient(new WebViewClient());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.global:
                Intent amphibiansActivityIntentss = new Intent(ph.this, global.class);

                startActivity(amphibiansActivityIntentss);
                return true;

            case R.id.local:
                Intent intentss = new Intent(ph.this, Covidupdates.class);
                startActivity(intentss);
                return true;
            case R.id.ph:
                Intent sss = new Intent(ph.this, ph.class);
                startActivity(sss);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}