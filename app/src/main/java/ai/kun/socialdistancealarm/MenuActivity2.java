package ai.kun.socialdistancealarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import ai.kun.socialdistancealarm.presenter.Covidupdates;
import ai.kun.socialdistancealarm.presenter.Create_Account;
import ai.kun.socialdistancealarm.presenter.MapsActivity2;
import ai.kun.socialdistancealarm.presenter.Symptom_checker;
import ai.kun.socialdistancealarm.presenter.health_report;
import ai.kun.socialdistancealarm.presenter.tips;
import ai.kun.socialdistancealarm.service.ForegroundService;

public class MenuActivity2 extends AppCompatActivity {
    CardView distancing;
    CardView symptom;
    CardView Actmap;
    CardView updates;
    CardView health_report;
    CardView contact, tips, about;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    private static final int REQUEST_ENABLE_BT = 1; // Unique request code
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        //adapter.enable();

        //check if bluetooth is ON
            if(!adapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
            adapter.enable();
            Toast.makeText(getApplicationContext(),"Bluetooth Turned ON",Toast.LENGTH_SHORT).show();
        }

        // check if location is On
        enableLocationSettings();
        requestAppPermissions();

        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
       // getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFFFF'>Covid-19 Monitoring Tool</font>"));

        //set up notitle
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // set up full screen
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
           WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); //<< this


        setContentView(R.layout.menueactivity_menu2);

        distancing = findViewById(R.id.distancing);
        symptom = findViewById(R.id.symptomchecker);
        Actmap = findViewById(R.id.geofence);
        updates = findViewById(R.id.actupdate);
        contact= findViewById(R.id.contacts);
        health_report = findViewById(R.id.health_report);
        tips = findViewById(R.id.tipss);
        about = findViewById(R.id.about);


        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_APPEND);
        String name = sharedPreferences.getString("name", "");
        //Toast.makeText(MenuActivity2.this, name,  Toast.LENGTH_LONG).show();


            distancing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check if user is exist
                    if (name!=null && !name.isEmpty() ) {
                    Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, MainActivity.class);
                    startActivity(amphibiansActivityIntent);
                    }
                    else{
                        Toast.makeText(MenuActivity2.this, "please register first",  Toast.LENGTH_LONG).show();
                        Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, Create_Account.class);
                        startActivity(amphibiansActivityIntent);

                    }

                }
            });

       tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is exist

                    Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, ai.kun.socialdistancealarm.presenter.tips.class);
                    startActivity(amphibiansActivityIntent);



            }
        });

       about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is exist

                Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, ai.kun.socialdistancealarm.presenter.About.class);
                startActivity(amphibiansActivityIntent);



            }
        });


        health_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, ai.kun.socialdistancealarm.presenter.links.about_us.class);
                String terms =  "https://regimepadillo1234.wixsite.com/website/about" ;
                amphibiansActivityIntent.putExtra("about_us", terms);
                startActivity(amphibiansActivityIntent);



            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, Create_Account.class);
                startActivity(amphibiansActivityIntent);


            }
        });

       symptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, Symptom_checker.class);
                startActivity(amphibiansActivityIntent);


            }
        });

        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, Covidupdates.class);
                startActivity(amphibiansActivityIntent);


            }
        });


        Actmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name!=null && !name.isEmpty() ) {
                  //  final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
              //  if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    enableLocationSettings();
                    Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, MapsActivity2.class);
                    startActivity(amphibiansActivityIntent);
                    requestAppPermissions();
               // }
                }
                else{
                    Toast.makeText(MenuActivity2.this, "please register first",  Toast.LENGTH_LONG).show();
                    Intent amphibiansActivityIntent = new Intent(MenuActivity2.this, Create_Account.class);
                    startActivity(amphibiansActivityIntent);
                }
            }
        });

    }

    // enable location foreground tracking service

    private void requestAppPermissions() {

        Dexter.withActivity(MenuActivity2.this)

                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            //interact.downloadImage(array);
                            startService(new Intent(MenuActivity2.this, ForegroundService.class));
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            showSettingsDialog();
                            //finish();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity2.this);

        builder.setTitle("Need Permissions");

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");

        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void openSettings() {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

        Uri uri = Uri.fromParts("package", getPackageName(), null);

        intent.setData(uri);

        startActivityForResult(intent, 101);

    }

    protected void enableLocationSettings() {

        LocationRequest locationRequest = LocationRequest.create()

                .setInterval(1000)

                .setFastestInterval(3000)

                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()

                .addLocationRequest(locationRequest);

        LocationServices

                .getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                   // startUpdatingLocation(...);
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(this, 123);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });

    }
}