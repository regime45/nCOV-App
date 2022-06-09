package ai.kun.socialdistancealarm.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ai.kun.socialdistancealarm.MainActivity;
import ai.kun.socialdistancealarm.MenuActivity2;
import ai.kun.socialdistancealarm.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_LONG;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class Create_Account extends AppCompatActivity  {

    private TextView textView, texts, viewgender;
    private EditText editText, purok, number, barangay;
    private Button applyTextButton;
    private Button register, saveit;
    private Switch switch1;
    private Handler mHandler = new Handler();

    private CircleImageView  mImageView;
    private ProgressBar mProgressBar;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private Context context;

    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    String sImage;

    DBHelper DB;


    public static final String SHARED_PREFS = "sharedPrefs";


    private String text, puroks, numbers, barang, genders, name;
    private boolean switchOnOff;

    ImageView ivOutput, urlcontact;
    BottomNavigationView navigationView;
    private String imageURL;
    private String s;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RadioGroup rgGender;
    // Declare RadioButton object references for Male and Female
    RadioButton rbMale, rbFemale;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView selectedImage;
    Button cameraBtn,galleryBtn;
    String currentPhotoPath;
    StorageReference storageReference;


    Intent intent ;
    public  static final int RequestPermissionCode  = 1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        EnableRuntimePermission();

        sharedPreferences = getSharedPreferences("pref", 0);
        // Here pref is the name of the file and 0 for private mode.
        // To read values from SharedPreferences, you can use getInt() method on
        // sharedPreferences object.
        int genderSP = sharedPreferences.getInt("genderSP", 3);
        // on sharedPreferences object.
        editor = sharedPreferences.edit();

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#231D1D")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFFFF'>Contact Tracing</font>"));
        // navigationView = findViewById(R.id.bottom_navigation);
        //getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
//        navigationView.setSelectedItemId(R.id.nav_home);

        ivOutput=  (findViewById(R.id.iv_output));
        register = (findViewById(R.id.regis));
        saveit = (findViewById(R.id.saveit ));
        mProgressBar = findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("covid_tool");
        texts = (findViewById(R.id.myName));
        mImageView = (CircleImageView) findViewById(R.id.imageView);

        urlcontact = findViewById(R.id.conss);

        /// start bottom sheet
        BottomSheetDialog dialog = new BottomSheetDialog(Create_Account.this);
        dialog.setContentView(R.layout.regis);
        dialog.setCanceledOnTouchOutside(false);

        editText = dialog.findViewById(R.id.edittext);
        number = dialog.findViewById(R.id.number);
        barangay = dialog.findViewById(R.id.barangay);
        purok= dialog.findViewById(R.id.purok);
        saveit = dialog.findViewById(R.id.saveit);

        rgGender = dialog.findViewById(R.id.rgGender);
        rbMale = dialog.findViewById(R.id.rbMale);
        rbFemale = dialog.findViewById(R.id.rbFemale);
        FrameLayout bottomSheet = (FrameLayout) dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

        // end bottom sheet

        DB = new DBHelper(this);
        final Spinner gender= (Spinner)  dialog.findViewById(R.id.spinner_gender);

        //ImageView popupImv = new ImageView(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        urlcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://commons.wikimedia.org/wiki/File:Covid-19-Contact-tracing-05.gif"));
                startActivity(Getintent);
            }
        });




        if(genderSP == 1){
            rbMale.setChecked(true);
        }else if(genderSP == 0){
            rbFemale.setChecked(true);
        }

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbMale){
                    // Put the value 1 in the key "genderSP" using editor object
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");
                    //DatabaseReference newChildRef = myRef.push();
                    //String key = newChildRef.getKey();

                    String numbersss = number.getText().toString();
                    String namess =  "+63"+numbersss ;

                    if (namess  != null) {

                        myRef.child(namess).child("gender").setValue("Male");
                    }
                    editor.putInt("genderSP", 1);
                }else if(checkedId == R.id.rbFemale){
                    // Here, put the value 0 in the key "genderSP" using editor
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");
                    //DatabaseReference newChildRef = myRef.push();
                    //String key = newChildRef.getKey();
                    //Toast.makeText(Create_Account.this, "Female", Toast.LENGTH_SHORT).show();
                    String numbersss = number.getText().toString();
                    String namess =  "+63"+numbersss ;
                    if (namess != null) {

                        myRef.child(namess).child("gender").setValue("Female");
                    }
                    editor.putInt("genderSP",0);
                }
                // Now, to save the changes call commit() on editor.
                editor.commit();
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(Create_Account.this);


                /*
                openFileChooser();
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Create_Account.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }

                */
            }
        });

        saveit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder=new AlertDialog.Builder(Create_Account.this);
                builder.setTitle("Update");
                builder.setMessage("Ok?");
                // Intent intent = new Intent(view.getContext(), Fire_Activity.class);
                // retrieve display dimensions


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // handler delay
                        mHandler.postDelayed(mUpdateTimeTask, 2000);
                       //
                        //  textView.setText(editText.getText().toString());

                        saveData();

                        String name = editText.getText().toString();
                        String puroknum =purok.getText().toString();
                        String email = number.getText().toString();
                        String local = barangay.getText().toString();


                        String newstring =  email ;


                        String sTexts = "+63"+newstring;

                        MultiFormatWriter writer = new MultiFormatWriter();

                        try {
                            BitMatrix matrix= writer.encode(sTexts, BarcodeFormat.QR_CODE
                                    ,300, 300);


                            BarcodeEncoder encoder = new BarcodeEncoder();
                            Bitmap bitmap = encoder.createBitmap(matrix);

                            ivOutput.setImageBitmap(bitmap);
                            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //hidekeyboard
                            // manager.hideSoftInputFromWindow(textView.getApplicationWindowToken()
                            //      ,0);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");

                        //DatabaseReference newChildRef = myRef.push();
                        //String key = newChildRef.getKey();
                        String onetimekey = sTexts;

                        // new date format
                        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyyMMdd");

                        //expiration date
                        long cutoff = new Date().getTime()+ TimeUnit.MILLISECONDS.convert(14, TimeUnit.DAYS);
                        String limit_date = dateFormatGmt.format(new Date(cutoff));
                        Long expire = Long.parseLong( limit_date);

                        // Current date
                        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String currentDate = dateFormatGmt.format(new Date());
                        Long current = Long.parseLong(currentDate);

                        //date time creation
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy 'at' hh:mm aa");
                        String currentDateandTime = sdf.format(new Date());

                        if (onetimekey  != null) {

                            myRef.child(onetimekey ).child("name").setValue(name);
                            myRef.child(onetimekey ).child("contact").setValue(sTexts);
                            myRef.child(onetimekey ).child("barangay").setValue(local);
                            myRef.child(onetimekey ).child("purok").setValue(puroknum);
                            myRef.child(onetimekey ).child("created_At").setValue(currentDateandTime);
                            //current time
                            myRef.child(onetimekey ).child("current").setValue(currentDate);
                            myRef.child(onetimekey ).child("expiry").setValue(limit_date);
                            //expire date
                        }

                        Toast.makeText(getApplicationContext(),"Sucessfully updated",Toast.LENGTH_SHORT).show();
                        // Intent intent = new Intent(Create_Account.this, Create_Account.class);
                        // intent.putExtra("keymessage", sText);
                        //startActivity(intent);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
                // realance pleaase


            }


        });

        loadData();
        loadDatas();
        updateViews();
        updateViewss();
        temp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.health_report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.globals:
                String names = texts.getText().toString();
                if (names!=null && !names.isEmpty() ) {
                    Intent amphibiansActivityIntentss = new Intent(Create_Account.this, health_report.class);
                    startActivity(amphibiansActivityIntentss);

                }
                else {
                    Toast.makeText(Create_Account.this, "please register first",  Toast.LENGTH_LONG).show();
                }
                return true;




            case R.id.health:
                String namess = texts.getText().toString();
                if (namess!=null && !namess.isEmpty() ) {
                    Intent amphibiansActivityIntentsss = new Intent(Create_Account.this, health_report.class);
                    startActivity(amphibiansActivityIntentsss);
                }
                else {
                    Toast.makeText(Create_Account.this, "please register first",  Toast.LENGTH_LONG).show();
                }
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // do what you need to do here after the delay
            ProcessPhoenix.triggerRebirth(getApplicationContext());
        }
    };

    private void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Create_Account.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(Create_Account.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Create_Account.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    public class ViewDialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.newcustom_);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
           // mDialogNo.setBackgroundColor(Color.parseColor("#231D1D"));
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(Create_Account.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                        dialog.dismiss();
                    }
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    askCameraPermissions();
                    dialog.dismiss();
                }


            });

            ImageView exit = dialog.findViewById(R.id.clear);

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }


            });

            dialog.show();
        }
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "net.smallacademy.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            //String ss = String.valueOf(data.getData());
            Uri uri=data.getData();
            Toast.makeText(this, "Picture taken!"+uri, Toast.LENGTH_SHORT).show();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                //textView.setText(sImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
            // Initialize bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // set bitmap on imageView
            mImageView.setImageBitmap(bitmap);
           // Toast.makeText(getApplicationContext(),  sImage,Toast.LENGTH_SHORT).show();
            String ss =   sImage;
            savesdata(ss);



            ///// display image after decoding

        //  Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/geofencing-531cb.appspot.com/o/uploads%2F1645952134032.null?alt=media&token=19459254-2a0a-4003-8c57-fdee1f0f50b6").into(mImageView);
        }

        // Initialize bitmap
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                // set imgae
                //mImageView.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                String uriss = String.valueOf(f.getName());
                uploadImageToFirebase( uriss,contentUri);
                String supers = String.valueOf(Uri.fromFile(f));
                Toast.makeText(Create_Account.this, supers, Toast.LENGTH_SHORT).show();


                try {
                    Bitmap bitmaps= MediaStore.Images.Media.getBitmap(getContentResolver(),contentUri);
                    // initialize byte stream
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    // compress Bitmap
                    bitmaps.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    // Initialize byte array
                    byte[] bytes=stream.toByteArray();
                    // get base64 encoded string
                    sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                    // set encoded text on textview
                    //textView.setText(sImage);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmaps = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // set bitmap on imageView
                mImageView.setImageBitmap(bitmaps);

                String ss =   sImage;
                savesdata(ss);
              //
            }
        }
    }
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy 'at' hh:mm aa");
                                    String currentDateandTime = sdf.format(new Date());
                                    // HashMap<String, String> hashMap= new HashMap<>();
                                    //hashMap.put("imageURL",String.valueOf(uri));
                                    // hashMap.put("name",  text);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");

                                    @SuppressLint("WrongConstant") SharedPreferences sharedPreferencess = getSharedPreferences("MySharedPref", MODE_APPEND);

                                    numbers = sharedPreferencess.getString("contact", "");

                                    String onetimekey =  "+63"+numbers ;
                                    if (onetimekey != null ) {
                                        //Strng uploadId = mEditTextFileName.getText().toString().trim();
                                        myRef.child(onetimekey).child("imageURL").setValue(String.valueOf(uri));

                                    }

                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                   editor.putString("image_gallery", String.valueOf(uri));
                                   editor.commit();
                                }
                            });
                            Toast.makeText(Create_Account.this, "Upload successful", LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Create_Account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
// camera
    private void uploadImageToFirebase(String name, Uri contentUri) {

        if (contentUri != null) {
            final StorageReference image = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExt(contentUri));
            image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");
                            String onetimekey =  "+63"+numbers ;
                            if (onetimekey != null) {
                                //Strng uploadId = mEditTextFileName.getText().toString().trim();
                                myRef.child(onetimekey).child("imageURL").setValue(String.valueOf(uri));
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("image_camera", String.valueOf(uri));
                            editor.commit();

                        }
                    });

                    Toast.makeText(Create_Account.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Create_Account.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        }
    }


    private void savesdata(String sss) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ImageURL", sss);
        editor.commit();
    }

    public void loadDatas() {
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_APPEND);
        imageURL = sharedPreferences.getString("ImageURL", "");
    }

    private void updateViewss() {
        byte[] bytes = Base64.decode(imageURL, Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // set bitmap on imageView
        mImageView.setImageBitmap(bitmap);
    }

    private void temp() {
        final Spinner spinner_gender
                = (Spinner)findViewById(R.id.spinner_gender);
        final Spinner spinner_name
                = (Spinner)findViewById(R.id.spinner_name);
        // Create an ArrayAdapter using the string array and
        // a default spinner layout
        ArrayAdapter<CharSequence> ad_gender
                = ArrayAdapter.createFromResource(
                this, R.array.gender_type,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of
        // choices appears
        ad_gender.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_gender.setAdapter(ad_gender);

        spinner_gender.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int i, long l)
                    {

                        if (adapterView.getSelectedItem()
                                .toString()
                                .equals("Female")) {
                            ArrayAdapter<CharSequence> ad_name
                                    = ArrayAdapter.createFromResource(
                                    getApplicationContext(),
                                    R.array.girls,
                                    android.R.layout
                                            .simple_spinner_item);
                            spinner_name.setAdapter(ad_name);
                        }
                        else {
                            ArrayAdapter<CharSequence> ad_name
                                    = ArrayAdapter.createFromResource(
                                    getApplicationContext(),
                                    R.array.boys,
                                    android.R.layout
                                            .simple_spinner_item);
                            spinner_name.setAdapter(ad_name);
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> adapterView)
                    {
                    }
                });

        spinner_name.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view,
                            int i, long l)
                    {
                        /*
                        Snackbar
                                .make(findViewById(R.id.layout),
                                        spinner_gender.getSelectedItem().toString()

                                                + "."
                                                + adapterView.getSelectedItem().toString(),
                                        BaseTransientBottomBar
                                                .LENGTH_LONG)
                                .show();

                         */
                        String temp1 = spinner_gender.getSelectedItem().toString();

                        String temp2 =  adapterView.getSelectedItem().toString();
                        adapt(temp1 , temp2 );

                    }



                    @Override
                    public void onNothingSelected(
                            AdapterView<?> adapterView)
                    {
                    }
                });

    }

    private void adapt(String temp1, String temp2) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("covid_tool").child("contact_tracing_user");
        //DatabaseReference newChildRef = myRef.push();
        //String key = newChildRef.getKey();
        String temparature = ""+temp1+"."+temp2;
        String onetimekey =  "+63"+numbers ;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String hour = sdf.format(new Date());

        if (onetimekey  != null) {

            myRef.child(onetimekey ).child("temparature").setValue( temparature );
        }


    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", editText.getText().toString());
        editor.putString("contact", number.getText().toString());
        editor.putString("barangays", barangay.getText().toString());
        editor.putString("puruks", purok.getText().toString());

        editor.commit();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_APPEND);
        text = sharedPreferences.getString("name", "");
        numbers = sharedPreferences.getString("contact", "");
        barang = sharedPreferences.getString("barangays", "");
        puroks = sharedPreferences.getString("puruks", "");



        // switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews() {
        editText.setText(text);
        number.setText(numbers);
        barangay.setText(barang);
        purok.setText(puroks);
        texts.setText(text);


        // switch1.setChecked(switchOnOff);

        // String str= ""+newstring+ "" +text ;

        String sText = "+63"+numbers ;

        // String sText = ""+text+""+ numbers+ "" +barangays;

        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix= writer.encode(sText, BarcodeFormat.QR_CODE
                    ,300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);

            ivOutput.setImageBitmap(bitmap);
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //hidekeyboard
//            manager.hideSoftInputFromWindow(textView.getApplicationWindowToken()
            //      ,0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}