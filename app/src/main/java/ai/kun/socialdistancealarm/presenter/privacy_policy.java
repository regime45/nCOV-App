package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import ai.kun.socialdistancealarm.MenuActivity2;
import ai.kun.socialdistancealarm.R;

public class privacy_policy extends AppCompatActivity {
    ImageView policy;
    Button submit, ok;
    CheckBox cb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        policy = (ImageView) findViewById(R.id.policy);

        ok = findViewById(R.id.submit_na);
        cb1 = findViewById(R.id.agree);
/*
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policy.setImageResource(R.drawable.ic_baseline_camera_24);

            }
        });

 */

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()){
                    Intent amphibiansActivityIntent = new Intent(privacy_policy.this, MenuActivity2.class);
                    startActivity(amphibiansActivityIntent);
                }

                }

        });
    }
}