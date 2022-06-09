package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ai.kun.socialdistancealarm.R;
import ai.kun.socialdistancealarm.presenter.links.ContactActivity;
import ai.kun.socialdistancealarm.presenter.links.about_us;
import ai.kun.socialdistancealarm.presenter.links.blogs;
import ai.kun.socialdistancealarm.presenter.links.developers;
import ai.kun.socialdistancealarm.presenter.links.privacy_policies;
import ai.kun.socialdistancealarm.presenter.links.rate_us;
import ai.kun.socialdistancealarm.presenter.links.term_of_use;

public class About extends AppCompatActivity {
    CardView about, Doh, term, policy, developer, contact, blog, rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ac0029")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Links</font>"));
        Window window =About.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(About.this, R.color.header));

         about= findViewById(R.id.about);
         Doh= findViewById(R.id.doh);
         term = findViewById(R.id.term);
         policy= findViewById(R.id.policy);
         developer= findViewById(R.id.developer);
         contact= findViewById(R.id.contact);
         blog =findViewById(R.id.blog);
         rates =findViewById(R.id.rates);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this, about_us.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website/about" ;
                amphibiansActivityIntent.putExtra("about_us", terms);
                startActivity(amphibiansActivityIntent);

            }
        });
       blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this, blogs.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website" ;
                amphibiansActivityIntent.putExtra("blog", terms);
                startActivity(amphibiansActivityIntent);

            }
        });

       rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent amphibiansActivityIntent = new Intent(About.this, rate_us.class);
                startActivity(amphibiansActivityIntent);

            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent amphibiansActivityIntent = new Intent(About.this, privacy_policies.class);
                startActivity(amphibiansActivityIntent);

            }
        });

      developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this, developers.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website/developers" ;
                amphibiansActivityIntent.putExtra("developers", terms);
                startActivity(amphibiansActivityIntent);

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this, ContactActivity.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website/contact" ;
                amphibiansActivityIntent.putExtra("contact", terms);
                startActivity(amphibiansActivityIntent);

            }
        });



        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this, term_of_use.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website/about-4" ;
                amphibiansActivityIntent.putExtra("term_use", terms);
                startActivity(amphibiansActivityIntent);



            }
        });


        Doh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(About.this, "please register first",  Toast.LENGTH_LONG).show();
                Intent amphibiansActivityIntent = new Intent(About.this,ph.class);
                startActivity(amphibiansActivityIntent);



            }
        });
    }
}