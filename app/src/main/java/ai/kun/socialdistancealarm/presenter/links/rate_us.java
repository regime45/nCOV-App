package ai.kun.socialdistancealarm.presenter.links;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ai.kun.socialdistancealarm.R;

public class rate_us extends AppCompatActivity {
    private WebView mywebview, mywebviews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ac0029")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Evaluation</font>"));
        mywebview = (WebView) findViewById(R.id.webView);
        mywebview .requestFocus();
        mywebview .getSettings().setLightTouchEnabled(false);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.setInitialScale(100);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfRpdtCOhnpmwfyGegvrjtmc3vf-vf_Ja1fq8tjWFSZ3gVl1g/viewform");
        mywebview.setWebViewClient(new WebViewClient());
    }
}