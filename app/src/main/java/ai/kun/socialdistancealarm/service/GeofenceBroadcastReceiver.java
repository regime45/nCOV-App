package ai.kun.socialdistancealarm.service;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ai.kun.socialdistancealarm.presenter.MapsActivity2;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
//        Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show();

                @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_APPEND);
                String name = sharedPreferences.getString("name", "");
                String lat = sharedPreferences.getString("last_latitude", "");
                String lon = sharedPreferences.getString("last_longitude", "");
                //// Toast.makeText(context, ""+lat+ ""+lon, Toast.LENGTH_SHORT).show();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("covid_tool").child("trigger").child("user_enter");

                DatabaseReference newChildRef = myRef.push();
                String key = newChildRef.getKey();

                if (key!= null) {
                    myRef.child(key).child("name").setValue(name);
                    // myRef.child(name).child("Radius").setValue(contact);
                    myRef.child(key).child("last_latitude").setValue(lat);
                    myRef.child(key).child("last_longitude").setValue(lon);


                }
                //     Toast.makeText(context, " save successfully...", Toast.LENGTH_SHORT).show();


                myRef.orderByChild("name").equalTo(name).addListenerForSingleValueEvent (new ValueEventListener()  {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            //   Toast.makeText(Fire_Activity.this, "naa na  "  , Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                notificationHelper.sendHighPriorityNotification("ENTER LOCKDOWN AREA", "", MapsActivity2.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("DWELL IN LOCKDOWN AREA", "", MapsActivity2.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("EXIT LOCKDOWN AREA", "", MapsActivity2.class);
                break;
        }

    }
}
