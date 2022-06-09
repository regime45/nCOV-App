package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ai.kun.socialdistancealarm.R;

public class Symptom_checker extends AppCompatActivity {
    CheckBox cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11, cb12, cb13;
    FloatingActionButton btnReset, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_checker);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ac0029")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Symptom Checker</font>"));

        cb2= findViewById(R.id.checkBox2);
        cb3= findViewById(R.id.checkBox3);
        cb4= findViewById(R.id.checkBox4);
        cb5= findViewById(R.id.checkBox5);
        cb6= findViewById(R.id.checkBox6);
        cb7= findViewById(R.id.checkBox7);
        cb8= findViewById(R.id.checkBox8);
        cb9= findViewById(R.id.checkBox9);
        cb10= findViewById(R.id.checkBox10);
        cb11= findViewById(R.id.checkBox11);
        cb12= findViewById(R.id.checkBox12);
        cb13= findViewById(R.id.checkBox13);

        btnReset = findViewById (R.id. imageButton);
        btnSubmit = findViewById (R.id. imageButton2);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             StringBuffer result= new StringBuffer();
                //result.append("You Select\n");
                String results = "Severely Affected";
                String suspected = "Suspected";
                String mildcondition = "Severely Affected";
                String conditon= "Mild Condition";
                String good= "Good condition";

                /*

                if (cb2.isChecked()){
                    result.append("\n" + cb2.getText().toString());
                }
                if (cb3.isChecked()){
                    result.append("\n" + cb3.getText().toString());
                }
                if (cb4.isChecked()){
                    result.append("\n" + cb4.getText().toString());
                }
                if (cb5.isChecked()){
                    result.append("\n" + cb5.getText().toString());
                }
                if (cb6.isChecked()){
                    result.append("\n" + cb6.getText().toString());
                }
                if (cb7.isChecked()){
                    result.append("\n" + cb7.getText().toString());
                }
                if (cb8.isChecked()){
                    result.append("\n" + cb8.getText().toString());
                }
                if (cb9.isChecked()){
                    result.append("\n" + cb9.getText().toString());
                }
                if (cb10.isChecked()){
                    result.append("\n" + cb10.getText().toString());
                }
                if (cb11.isChecked()){
                    result.append("\n" + cb11.getText().toString());
                }
                if (cb12.isChecked()){
                    result.append("\n" + cb12.getText().toString());
                }
                if (cb13.isChecked()){
                    result.append("\n" + cb13.getText().toString());
                }
                */
                if (cb3.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb4.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb5.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb6.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb7.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb8.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb9.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb10.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb11.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb12.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}
                if (cb13.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);}



                if (cb2.isChecked()&&cb3.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb4.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb5.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb6.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb7.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb8.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb9.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb10.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb11.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb12.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb13.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }

                if (cb3.isChecked()&&cb4.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +good);
                }

                if (cb2.isChecked()&&cb3.isChecked()&&cb5.isChecked()&&cb8.isChecked()&&cb11.isChecked()){
                    TextView suspecteds= findViewById(R.id.resulta);
                    suspecteds.setText("" +suspected);}


                if (cb2.isChecked()&&cb3.isChecked()&&cb4.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb3.isChecked()&&cb4.isChecked()&&cb5.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb3.isChecked()&&cb4.isChecked()&&cb5.isChecked()&&cb6.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }
                if (cb2.isChecked()&&cb3.isChecked()&&cb4.isChecked()&&cb5.isChecked()&&cb6.isChecked()
                        &&cb7.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }

                if (cb3.isChecked()&&cb4.isChecked()&&cb5.isChecked()&&cb6.isChecked()&&cb7.isChecked()&&!cb2.isChecked()
                ){
                    TextView mildss= findViewById(R.id.resulta);
                    mildss.setText("" +suspected);}

                if (cb6.isChecked()&&cb7.isChecked()&&cb8.isChecked()&&cb9.isChecked()&&cb5.isChecked()&&!cb2.isChecked()
                ){
                    TextView mildss= findViewById(R.id.resulta);
                    mildss.setText("" +suspected);}

                if (cb8.isChecked()&&cb9.isChecked()&&cb10.isChecked()&&cb11.isChecked()&&cb12.isChecked()&&!cb2.isChecked()
                ){
                    TextView mildss= findViewById(R.id.resulta);
                    mildss.setText("" +suspected);}

                if (cb10.isChecked()&&cb9.isChecked()&&cb11.isChecked()&&cb12.isChecked()&&cb13.isChecked()&&!cb2.isChecked()
                ){
                    TextView mildss= findViewById(R.id.resulta);
                    mildss.setText("" +conditon);}
                if (cb8.isChecked()&&cb10.isChecked()&&cb9.isChecked()&&cb11.isChecked()&&cb12.isChecked()&&cb13.isChecked()&&!cb2.isChecked()
                ){
                    TextView mildss= findViewById(R.id.resulta);
                    mildss.setText("" +suspected);}

                if (cb2.isChecked()&&!cb3.isChecked()&&!cb4.isChecked()&&!cb5.isChecked()
                        &&!cb6.isChecked()&&!cb7.isChecked()&&!cb8.isChecked()&&!cb9.isChecked()
                        &&!cb10.isChecked()&&!cb11.isChecked()&&!cb12.isChecked()&&!cb13.isChecked()){
                    TextView resultss= findViewById(R.id.resulta);
                    resultss.setText("" +results);
                }


                if (!cb2.isChecked()&&!cb3.isChecked()&&!cb4.isChecked()&&!cb5.isChecked()
                        &&!cb6.isChecked()&&!cb7.isChecked()&&!cb8.isChecked()&&!cb9.isChecked()
                        &&!cb10.isChecked()&&!cb11.isChecked()&&!cb12.isChecked()&&!cb13.isChecked());
                   // resultss.setText("" +results);
                    //result.append("\nNone");
               // messagesymptom.message(Symptom_checker.this, result.toString());



            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cb2.isChecked()){
                    cb2.setChecked(false);
                }
                if(cb3.isChecked()){
                    cb3.setChecked(false);
                }
                if(cb4.isChecked()){
                    cb4.setChecked(false);
                }
                if(cb5.isChecked()){
                    cb5.setChecked(false);
                }
                if(cb6.isChecked()){
                    cb6.setChecked(false);
                }
                if(cb7.isChecked()){
                    cb7.setChecked(false);

                }if(cb8.isChecked()){
                    cb8.setChecked(false);
                }
                if(cb9.isChecked()){
                    cb9.setChecked(false);
                }
                if(cb10.isChecked()){
                    cb10.setChecked(false);
                }
                if(cb11.isChecked()){
                    cb11.setChecked(false);
                }
                if(cb12.isChecked()){
                    cb12.setChecked(false);
                }
                if(cb13.isChecked()){
                    cb13.setChecked(false);
                }


            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked= ((CheckBox) v).isChecked();
                if(checked)
                    messagesymptom.message(Symptom_checker.this, "cardinal symptom");
                else
                    messagesymptom.message(Symptom_checker.this, "keep safe");
            }
        });
    }
}