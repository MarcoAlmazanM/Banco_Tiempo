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
import android.text.TextUtils;
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
import java.io.File;
import java.io.FileInputStream;
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
    String sCert;
    Button bCert;
    ImageView cert;
    TextView category, title, description;
    String cat, titxd, des, message;

    AutoCompleteTextView autoCTV, autoCTV2;
    ArrayAdapter<String> adapterItems, adapterItems2;
    String[] items = {"Carpintería", "Sastrería", "Repostería", "Tutoría", "Plomería"};
    //String[] items2 = {"Toluca", "Metepec", "Otro pueblo xd"};

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username;
    String colonia;

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


        cert = findViewById(R.id.certiView);
        bCert = findViewById(R.id.certi);

        autoCTV = findViewById(R.id.tVAutoComplete);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_empleos_item, items);
        autoCTV.setAdapter(adapterItems);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //Get username shared preferences
        username = preferences.getString("username","username");
        colonia = preferences.getString("colonia","colonia");

        autoCTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Categoria: "+item, Toast.LENGTH_SHORT).show();
            }
        });
        createOfferIntent();
    }

    public void pick(View view) {
        verifyStoragePermissions(CreateOffer.this);
        mGetContent.launch("image/*");
    }

    public void clickBtnCert(View view){
        verifyStoragePermissions(CreateOffer.this);
        certGetContent.launch("image/*");
    }

    public void addNewOffer(View view){
        img1 = findViewById(R.id.iVPhoto1);
        cert = findViewById(R.id.certiView);
        category = findViewById(R.id.tVAutoComplete);
        title = findViewById(R.id.tVAutoComplete2);
        description = findViewById(R.id.tVDescription);

        cat = category.getText().toString();
        titxd = title.getText().toString();
        des = description.getText().toString();

        if (TextUtils.isEmpty(cat) || TextUtils.isEmpty(titxd)  || TextUtils.isEmpty(des)) {
            message = "All inputs required ...";
            Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(sImage)){
            message = "An Image is necessary ...";
            Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
        }
        else {
            NewOfferRequest newOfferRequest = new NewOfferRequest();
            newOfferRequest.setUsername(username);
            newOfferRequest.setCategoria(cat);
            newOfferRequest.setNombre(titxd);
            newOfferRequest.setDescripcion(des);
            newOfferRequest.setImage(sImage);
            newOfferRequest.setColonia(colonia);
            if(TextUtils.isEmpty(sCert)){
                newOfferRequest.setCertificado("NULL");
            }else{
                newOfferRequest.setCertificado(sCert);
            }
            uploadNewOffer(newOfferRequest);
        }
    }

    public void createOfferIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            NewOfferResponse newOfferResponse = (NewOfferResponse) intent.getSerializableExtra("data");
            if (newOfferResponse.getTransactionApproval() == 1) {
                message = "New Offer created successfully";
                Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
            }else{
                message = "Error in New Offer Creation";
                Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadNewOffer(NewOfferRequest newOfferRequest) {
        Call<NewOfferResponse> newOfferResponseCall = ApiClient.getService().uploadNewOffer(newOfferRequest);
        newOfferResponseCall.enqueue(new Callback<NewOfferResponse>() {
            @Override
            public void onResponse(Call<NewOfferResponse> call, Response<NewOfferResponse> response) {
                if (response.isSuccessful()) {
                    NewOfferResponse newOfferResponse = response.body();
                    startActivity(new Intent(CreateOffer.this, CreateOffer.class).putExtra("data", newOfferResponse));
                    finish();
                } else {
                    message = "An error occurred, please try again...";
                    Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewOfferResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @SuppressLint("Range")
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onActivityResult(Uri uri) {
                    img1.setImageURI(uri);
                    selectedImage = MediaStore.Images.Media.getContentUri("external");
                    // Get the image file URI
                    String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};

                    // Obtain path image & fileName image
                    String path = getImagePath(uri);
                    File file = new File(path);
                    String fileName = file.getName();
                    String selectionClause = MediaStore.Images.ImageColumns.DISPLAY_NAME + "=?";
                    String[] args = {fileName};

                    Cursor cursor = getContentResolver().query(selectedImage, imageProjection,selectionClause, args, null);
                    if (cursor.getCount()>0) {
                        cursor.moveToPosition(0);
                        part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));
                        cursor.close();
                        try {
                            byte[] buffer = new byte[(int) file.length() + 100];
                            @SuppressWarnings("resource")
                            int length = new FileInputStream(file).read(buffer);
                            sImage = Base64.encodeToString(buffer, 0, length,
                                    Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(CreateOffer.this, "Algo Salió mal", Toast.LENGTH_SHORT);
                    }

                }
            });

    ActivityResultLauncher<String> certGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @SuppressLint("Range")
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onActivityResult(Uri uri) {
                    cert.setImageURI(uri);

                    selectedImage = MediaStore.Images.Media.getContentUri("external");
                    // Get the image file URI
                    String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};

                    // Obtain path image & fileName image
                    String path = getImagePath(uri);
                    File file = new File(path);
                    String fileName = file.getName();
                    String selectionClause = MediaStore.Images.ImageColumns.DISPLAY_NAME + "=?";
                    String[] args = {fileName};

                    Cursor cursor = getContentResolver().query(selectedImage, imageProjection,selectionClause, args, null);
                    if (cursor.getCount()>0) {
                        cursor.moveToPosition(0);
                        part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));
                        cursor.close();
                        try {
                            byte[] buffer = new byte[(int) file.length() + 100];
                            @SuppressWarnings("resource")
                            int length = new FileInputStream(file).read(buffer);
                            sCert = Base64.encodeToString(buffer, 0, length,
                                    Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(CreateOffer.this, "Algo Salió mal", Toast.LENGTH_SHORT);
                    }

                }
            });



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