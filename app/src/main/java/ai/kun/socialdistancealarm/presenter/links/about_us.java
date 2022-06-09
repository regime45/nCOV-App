package ai.kun.socialdistancealarm.presenter.links;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ai.kun.socialdistancealarm.R;

public class about_us extends AppCompatActivity {
    private WebView mywebview, mywebviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ac0029")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>About Us</font>"));

        String mes = getIntent().getStringExtra("about_us" );
        mywebview = (WebView) findViewById(R.id.webView);
        mywebview .requestFocus();
        mywebview .getSettings().setLightTouchEnabled(false);
        //mywebview .getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.setInitialScale(100);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl(mes);
        mywebview.setWebViewClient(new WebViewClient());

    }
}