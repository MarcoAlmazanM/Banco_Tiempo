package com.example.banco_tiempo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOffer extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout createOfferLayout;

    ImageView img1;
    Uri selectedImage;
    String part_image;
    String sImage;
    TextView imgPath;

    AutoCompleteTextView autoCTV, autoCTV2;
    ArrayAdapter<String> adapterItems, adapterItems2;
    String[] items = {"Carpintería", "Sastrería", "Repostería", "Tutoría", "Plomería"};
    String[] items2 = {"Toluca", "Metepec", "Otro pueblo xd"};

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);
        createOfferLayout = findViewById(R.id.user_new_offer);
        Log.e("TAG",createOfferLayout.toString());
        toolbar = findViewById(R.id.toolbar);
        setTitle("Create New Offer");
        setSupportActionBar(toolbar);

        img1 = findViewById(R.id.iVPhoto1);
        imgPath = findViewById(R.id.tVImageN);

        autoCTV = findViewById(R.id.tVAutoComplete);
        autoCTV2 = findViewById(R.id.tVAutoComplete2);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_empleos_item, items);
        autoCTV.setAdapter(adapterItems);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_empleos_item, items2);
        autoCTV2.setAdapter(adapterItems2);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //Get username shared preferences
        username = preferences.getString("username","username");

        autoCTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Empleo: "+item, Toast.LENGTH_SHORT).show();
            }
        });
        autoCTV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Ubicación: "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pick(View view) {
        verifyStoragePermissions(CreateOffer.this);
        mGetContent.launch("image/*");
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @SuppressLint("Range")
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onActivityResult(Uri uri) {
                    selectedImage = MediaStore.Images.Media.getContentUri("external");                                                         // Get the image file URI
                    String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
                    Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);
                    if (cursor.getCount()>0) {
                        cursor.moveToPosition(0);
                        part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));

                        Uri filename = Uri.parse(imageProjection[0]);

                        cursor.close();


                        // Get the image file absolute path
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                            byte[] bytes = stream.toByteArray();
                            sImage = Base64.encodeToString(bytes,Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        img1.setImageBitmap(bitmap);
                        // Set the ImageView with the bitmap of the image
                    }

                    else {
                        Toast.makeText(CreateOffer.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                    }

                }
            });

    public void addNewOffer(View view){
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sImage);
        imageRequest.setUsername(username);
        imageRequest.setType("ComprobantePicture");
        uploadImageServer(imageRequest);
    }

    public void uploadImageServer(ImageRequest imageRequest){
        Call<ImageResponse> registerResponseCall = ApiClient.getService().uploadImageServer(imageRequest);
        registerResponseCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse>  call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    ImageResponse registerResponse = response.body();
                    String message = "Image Uploaded Successfully";
                    Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();

                } else {
                    String message = "An error occurred, please try again...";
                    Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}