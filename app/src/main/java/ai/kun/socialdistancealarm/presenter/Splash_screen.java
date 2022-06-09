package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ai.kun.socialdistancealarm.MainActivity;
import ai.kun.socialdistancealarm.MenuActivity2;
import ai.kun.socialdistancealarm.R;

public class Splash_screen extends AppCompatActivity {
    ImageView splash;
    TextView welcome;
    ProgressBar horizantal_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash = (ImageView) findViewById(R.id.splash);
        welcome = findViewById(R.id.name_welcome);

        splash.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise));

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(r, 2000); // will be delayed for 1.5 seconds


    }


    Runnable r = new Runnable() {
        @Override
        public void run() {
            @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_APPEND);
            String name = sharedPreferences.getString("name", "");
            //Toast.makeText(MenuActivity2.this, name,  Toast.LENGTH_LONG).show();

            // return true if user is existed
            if (name!=null && !name.isEmpty() ) {
                //String welcome_boy = "Welcome   "+ name;
               // welcome.setText(welcome_boy);
                Intent amphibiansActivityIntent = new Intent(Splash_screen.this, MenuActivity2.class);
              //  Toast.makeText(Splash_screen.this, name,  Toast.LENGTH_LONG).show();
                startActivity(amphibiansActivityIntent);

            }
            //if user first time open the app
            else{
                Intent amphibiansActivityIntent = new Intent(Splash_screen.this, privacy.class);
                startActivity(amphibiansActivityIntent);

            }
        }
    };
}