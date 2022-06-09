package ai.kun.socialdistancealarm.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ai.kun.socialdistancealarm.R;

public class Covidupdates extends AppCompatActivity {

    private DatabaseReference Db;
    private TextView totals, recoverss, deathss, date, newconfirmpercents ;

    private TextView newconfirm, newdeath, newrecover, totality;
    BottomNavigationView navigationView;
    ImageView report, daily, chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidupdates);
        Window window =Covidupdates.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Covidupdates.this, R.color.header));


        totality=  (findViewById(R.id.Totality));
        date=   (findViewById(R.id.date));
        // newcases=   (findViewById(R.id.newcases));


        // newconfirmpercents= (findViewById(R.id.newconfirmpercent));
        update();
        date();
        daily();
        totals= (findViewById(R.id.totalcases));
        recoverss=  (findViewById(R.id.recovries));
        deathss=  (findViewById(R.id.deaths));
        newrecover=  (findViewById(R.id.newrecover));
        newdeath=  (findViewById(R.id.newdeath));
        newconfirm=  (findViewById(R.id.newconfirm));

    }

    private void daily() {
        SimpleDateFormat days = new SimpleDateFormat("dd");
        String day = days .format(new Date());

        SimpleDateFormat months = new SimpleDateFormat("MMM");
        String month = months.format(new Date());

        SimpleDateFormat years = new SimpleDateFormat("yyyy");
        String year = years.format(new Date());
        Db = FirebaseDatabase.getInstance().getReference("covid_tool").child("covid_updates").child("daily_report")
                .child(day).child(month).child(year );
        Db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                int total_total = 0;
                int total_recover = 0;
                int total_deat= 0;


                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();



                        Object value_total = map.get("total_cases");
                        int avalue = Integer.parseInt(String.valueOf(value_total));
                        total_total += avalue;

                        NumberFormat formatter = new DecimalFormat("###,###,###");
                        newconfirm.setText(String.valueOf("" + formatter.format(total_total)));


                        Object value_recover = map.get("recoveries");
                        String rec = String.valueOf(value_recover);
                        int bvalue = 0;
                        bvalue = Integer.parseInt(rec);
                        total_recover += bvalue;
                        newrecover.setText(String.valueOf("" + formatter.format(total_recover)));

                        Object value_d = map.get("death");
                        String det = String.valueOf(value_d);
                        int cvalue = 0;
                        cvalue = Integer.parseInt(det);
                        total_deat += cvalue;
                        newdeath.setText(String.valueOf("" + formatter.format(total_deat)));

                        int over = total_total + total_recover + total_deat;
                        String overs = String.valueOf(over);

                        // totality.setText(overs);


                        //newcases(total_total, total_recover, total_deat);

                    }
                    catch (NumberFormatException e)
                    {
// log e if you want...
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    private void date() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("covid_tool");
        Query lastQuery = databaseReference.child("covid_updates").child("total").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String value = childSnapshot.child("date").getValue(String.class);

                    date.setText(value);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void update() {
        Db = FirebaseDatabase.getInstance().getReference("covid_tool").child("covid_updates").child("total");
        Db.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                int total_total = 0;
                int total_recover = 0;
                int total_deat= 0;
                int total_actives = 0;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();

                        Object value_total = map.get("total_cases");
                        int avalue = Integer.parseInt(String.valueOf(value_total));
                        total_total += avalue;

                        NumberFormat formatter = new DecimalFormat("###,###,###");
                        totals.setText(String.valueOf("" + formatter.format(total_total)));


                        Object value_recover = map.get("recoveries");
                        String rec = String.valueOf(value_recover);
                        int bvalue = 0;
                        bvalue = Integer.parseInt(rec);
                        total_recover += bvalue;
                        recoverss.setText(String.valueOf("" + formatter.format(total_recover)));

                        Object value_d = map.get("death");
                        String det = String.valueOf(value_d);
                        int cvalue = 0;
                        cvalue = Integer.parseInt(det);
                        total_deat += cvalue;
                        deathss.setText(String.valueOf("" + formatter.format(total_deat)));

                        int over = total_total + total_recover + total_deat;

                        totality.setText(String.valueOf("" + formatter.format(over)));


                        //newcases(total_total, total_recover, total_deat);

                    }
                    catch (NumberFormatException e)
                    {
// log e if you want...
                    }
                }



            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
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
                Intent amphibiansActivityIntentss = new Intent(Covidupdates.this, global.class);
                startActivity(amphibiansActivityIntentss);
                return true;

            case R.id.local:
                Intent intentss = new Intent(Covidupdates.this, Covidupdates.class);
                startActivity(intentss);
                return true;

            case R.id.ph:
                Intent sss = new Intent(Covidupdates.this, ph.class);
                startActivity(sss);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }



}