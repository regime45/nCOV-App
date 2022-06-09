package ai.kun.socialdistancealarm.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import ai.kun.socialdistancealarm.R;

public class health_report extends AppCompatActivity {
    CheckBox cb2, cb3, cb4, cb5, cb6, cb7, cb8;
    Button b2;
    TextView txt;
    RadioButton genderradioButton, yesbutton;
    RadioGroup radioGroup,radioGroup_two;

    CheckBox checkBox_Yes_2, checkBox_No_2 ,checkBox_Yes_3, checkBox_No_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ac0029")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Health Report</font>"));

        // pending cases checkboxes
        //checkBox_Yes_2 = findViewById(R.id. checkBox_Yes_2);
        //checkBox_No_2 = findViewById(R.id. checkBox_No_2);

        // covid confirms
       // checkBox_Yes_3 = findViewById(R.id. checkBox_Yes_3);
        //checkBox_No_3 = findViewById(R.id. checkBox_No_3);

        cb2= findViewById(R.id.checkBox2);
        cb3= findViewById(R.id.checkBox3);
        cb4= findViewById(R.id.checkBox4);
        cb5= findViewById(R.id.checkBox5);
        cb6= findViewById(R.id.checkBox6);
        cb7= findViewById(R.id.checkBox7);
        cb8= findViewById(R.id.checkBox8);
        txt= findViewById (R.id. resulta);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup_two= (RadioGroup)findViewById(R.id.radioGroups_two);



        b2= findViewById (R.id. button_apply);

        StringBuffer result= new StringBuffer();
        result.append("");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRefer = database.getReference("covid_tool").child("covid_health_report").child("User");
                DatabaseReference newChildRef = myRefer.push();
                String key = newChildRef.getKey();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy 'at' hh:mm aa");
                String currentDateandTime = sdf.format(new Date());


                @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_APPEND);
                String name = sharedPreferences.getString("name", "");
                String  numbers = sharedPreferences.getString("contact", "");
                String  barang = sharedPreferences.getString("barangays", "");
                String  puroks = sharedPreferences.getString("puruks", "");
                String contact= "+63"+numbers;

                String imageURL_camera = sharedPreferences.getString("image_camera", "");
                String imageURL_gallery = sharedPreferences.getString("image_gallery", "");

               // String  yes_2_pending = sharedPreferences.getString("Yes_pending", "");
               // String    no_2_pending = sharedPreferences.getString("No_pending", "");

                if (name != null) {

                    myRefer.child(name ).child("name").setValue(name);
                    myRefer.child(name ).child("contact").setValue(contact);
                    myRefer.child(name ).child("barangay").setValue(barang);
                    myRefer.child(name ).child("purok").setValue( puroks);

                    myRefer.child(name ).child("imageURL").setValue(imageURL_gallery);
                    myRefer.child(name ).child("date").setValue(currentDateandTime);

                if (cb2.isChecked()){
                    result.append("" + cb2.getText().toString());
                  //  txt.setText(result.toString());
                    if (cb2.getText().toString()!=null && !cb2.getText().toString().isEmpty() ) {
                        myRefer.child(name).child("loss_of_smell").setValue(cb2.getText().toString());

                    }
                }

                else {
                    myRefer.child(name).child("loss_of_smell").setValue("");


                }
                if (cb3.isChecked()){
                    result.append("" + cb3.getText().toString());
                   // txt.setText(result.toString());
                    myRefer.child(name ).child("short_breath").setValue(cb3.getText().toString());
                }
                else {
                    myRefer.child(name).child("short_breath").setValue("");

                }
                if (cb4.isChecked()){
                    result.append("" + cb4.getText().toString());
                    //txt.setText(result.toString());
                    myRefer.child(name ).child("fatigue").setValue(cb4.getText().toString());
                }
                else {
                    myRefer.child(name).child("fatigue").setValue("");

                }

                if (cb5.isChecked()){
                    result.append("" + cb5.getText().toString());
                    //txt.setText(result.toString());
                    myRefer.child(name ).child("fever").setValue(cb5.getText().toString());
                }
                else {
                    myRefer.child(name).child("fever").setValue("");

                }
                if (cb6.isChecked()){
                    result.append("" + cb6.getText().toString());
                   // txt.setText(result.toString());
                    myRefer.child(name ).child("persist_cough").setValue(cb6.getText().toString());
                }
                else {
                    myRefer.child(name).child("persist_cough").setValue("");

                }
                if (cb7.isChecked()){
                    result.append("" + cb7.getText().toString());
                   // txt.setText(result.toString());
                    myRefer.child(name ).child("diarrhea").setValue(cb7.getText().toString());
                }
                else {
                    myRefer.child(name).child("diarrhea").setValue("");

                }
                    if (cb8.isChecked()){
                        result.append("" + cb8.getText().toString());
                        // txt.setText(result.toString());
                        myRefer.child(name ).child("sore_throat").setValue(cb8.getText().toString());
                    }
                    else {
                        myRefer.child(name).child("sore_throat").setValue("");

                    }

                }

                txt.setText("Succesfully Save");


                int selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) findViewById(selectedId);

                int selected = radioGroup_two.getCheckedRadioButtonId();
                yesbutton = (RadioButton) findViewById(selected);
                try{
                    // radiobuttn
                    if (selectedId == -1) {
                        myRefer.child(name).child("pending_result").setValue("No");
                        myRefer.child(name).child("confirm_case").setValue("No");
                    } else {
                        //Toast.makeText(health_report.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                        myRefer.child(name).child("pending_result").setValue(genderradioButton.getText());
                        myRefer.child(name).child("confirm_case").setValue(yesbutton.getText());
                    }
                }
                catch (NumberFormatException e)
                {
// log e if you want...
                }

            }
        });



    }


}