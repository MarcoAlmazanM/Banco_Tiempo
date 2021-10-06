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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDocumentsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 9544;
    Toolbar toolbar;
    ConstraintLayout userDocumentsLayout;

    Uri selectedImage;
    String part_image;

    TextView inePath, domPath, antPath, certPath;
    ImageView ine, dom, ant, cert;
    Button bIne, bDom, bAnt, bCert;
    String sIne, sDom, sAnt, sCert;

    ActivityResultLauncher<String>ineGetContent;
    ActivityResultLauncher<String>domGetContent;
    ActivityResultLauncher<String>antGetContent;
    ActivityResultLauncher<String>certGetContent;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username;

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_documents);
        userDocumentsLayout = findViewById(R.id.user_documents);
        toolbar = findViewById(R.id.toolbar);
        setTitle("User Documents");
        setSupportActionBar(toolbar);

        inePath = findViewById(R.id.rutaIne);
        ine = findViewById(R.id.ineView);
        bIne = findViewById(R.id.ine);

        domPath = findViewById(R.id.rutaDom);
        dom = findViewById(R.id.domView);
        bDom = findViewById(R.id.dom);

        antPath = findViewById(R.id.rutaAnt);
        ant = findViewById(R.id.antView);
        bAnt = findViewById(R.id.ant);

        certPath = findViewById(R.id.rutaCert);
        cert = findViewById(R.id.certView);
        bCert = findViewById(R.id.cert);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //Get username shared preferences
        username = preferences.getString("username","username");


        ineGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @SuppressLint("Range")
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onActivityResult(Uri result) {
                ine.setImageURI(result);
                selectedImage = MediaStore.Images.Media.getContentUri("external");                                                         // Get the image file URI
                String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);

                if (cursor.getCount()>0) {
                    cursor.moveToPosition(0);
                    part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));

                    //Uri filename = Uri.parse(imageProjection[0]);

                    cursor.close();
                    bIne.setText("Documento cargado");

                    // Get the image file absolute path
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                        byte[] bytes = stream.toByteArray();
                        sIne = Base64.encodeToString(bytes,Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //image.setImageBitmap(bitmap);
                    // Set the ImageView with the bitmap of the image
                }

                else {
                    Toast.makeText(UserDocumentsActivity.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                }
            }
        });

        domGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @SuppressLint("Range")
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onActivityResult(Uri result) {
                dom.setImageURI(result);

                selectedImage = MediaStore.Images.Media.getContentUri("external");                                                         // Get the image file URI
                String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);

                if (cursor.getCount()>0) {
                    cursor.moveToPosition(0);
                    part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));

                    //Uri filename = Uri.parse(imageProjection[0]);

                    cursor.close();

                    bDom.setText("Documento cargado");

                    // Get the image file absolute path
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                        byte[] bytes = stream.toByteArray();
                        sDom = Base64.encodeToString(bytes,Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //image.setImageBitmap(bitmap);
                    // Set the ImageView with the bitmap of the image
                }

                else {
                    Toast.makeText(UserDocumentsActivity.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                }
            }
        });

        antGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @SuppressLint("Range")
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onActivityResult(Uri result) {
                ant.setImageURI(result);

                selectedImage = MediaStore.Images.Media.getContentUri("external");                                                         // Get the image file URI
                String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);

                if (cursor.getCount()>0) {
                    cursor.moveToPosition(0);
                    part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));

                    //Uri filename = Uri.parse(imageProjection[0]);

                    cursor.close();

                    bAnt.setText("Documento cargado");

                    // Get the image file absolute path
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                        byte[] bytes = stream.toByteArray();
                        sAnt = Base64.encodeToString(bytes,Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //image.setImageBitmap(bitmap);
                    // Set the ImageView with the bitmap of the image
                }

                else {
                    Toast.makeText(UserDocumentsActivity.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                }
            }
        });

        certGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @SuppressLint("Range")
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onActivityResult(Uri result) {
                cert.setImageURI(result);

                selectedImage = MediaStore.Images.Media.getContentUri("external");                                                         // Get the image file URI
                String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);

                if (cursor.getCount()>0) {
                    cursor.moveToPosition(0);
                    part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));

                    //Uri filename = Uri.parse(imageProjection[0]);

                    cursor.close();

                    bCert.setText("Documento cargado");

                    // Get the image file absolute path
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
                        byte[] bytes = stream.toByteArray();
                        sCert = Base64.encodeToString(bytes,Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //image.setImageBitmap(bitmap);
                    // Set the ImageView with the bitmap of the image
                }

                else {
                    Toast.makeText(UserDocumentsActivity.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                }
            }
        });


    }

    public void clickBtnIne(View view){
        ineGetContent.launch("image/*");
    }
    public void clickBtnDom(View view){
        domGetContent.launch("image/*");
    }
    public void clickBtnAnt(View view){
        antGetContent.launch("image/*");
    }
    public void clickBtnCert(View view){
        certGetContent.launch("image/*");
    }


    // Upload the images to the remote database
    public void uploadIne(View view) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sIne);
        imageRequest.setUsername(username);
        imageRequest.setType("INEPicture");
        uploadImageServer(imageRequest);
    }

    public void uploadDom(View view) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sDom);
        imageRequest.setUsername(username);
        imageRequest.setType("ComprobantePicture");
        uploadImageServer(imageRequest);
    }

    public void uploadAnt(View view) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sAnt);
        imageRequest.setUsername(username);
        imageRequest.setType("CartaAntecedentesPicture");
        uploadImageServer(imageRequest);
    }

    public void uploadCert(View view) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sCert);
        imageRequest.setUsername("JoseLuis");
        imageRequest.setType("ProfilePicture");
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
                    Toast.makeText(UserDocumentsActivity.this, message, Toast.LENGTH_LONG).show();

                } else {
                    String message = "An error occurred, please try again...";
                    Toast.makeText(UserDocumentsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(UserDocumentsActivity.this, message, Toast.LENGTH_LONG).show();
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