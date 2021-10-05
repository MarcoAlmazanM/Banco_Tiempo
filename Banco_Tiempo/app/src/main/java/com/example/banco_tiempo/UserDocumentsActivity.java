package com.example.banco_tiempo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
    /*TextView imgPath;
    ImageView image;
    Uri selectedImage;
    String part_image;
    */
    TextView inePath, domPath, antPath, certPath;
    ImageView ine, dom, ant, cert;
    Button bIne, bDom, bAnt, bCert;

    ActivityResultLauncher<String>ineGetContent;
    ActivityResultLauncher<String>domGetContent;
    ActivityResultLauncher<String>antGetContent;
    ActivityResultLauncher<String>certGetContent;

    String sImage;
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

        inePath = findViewById(R.id.ine);
        ine = findViewById(R.id.ineView);
        bIne = findViewById(R.id.ine);

        domPath = findViewById(R.id.dom);
        dom = findViewById(R.id.domView);
        bDom = findViewById(R.id.dom);

        antPath = findViewById(R.id.ant);
        ant = findViewById(R.id.antView);
        bAnt = findViewById(R.id.ant);

        certPath = findViewById(R.id.cert);
        cert = findViewById(R.id.certView);
        bCert = findViewById(R.id.cert);

        ineGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                ine.setImageURI(result);


            }
        });

        bIne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ineGetContent.launch("image/*");
            }
        });

        domGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                dom.setImageURI(result);
                
            }
        });

        bDom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                domGetContent.launch("image/*");
            }
        });

        antGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                ant.setImageURI(result);
            }
        });

        bAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antGetContent.launch("image/*");
            }
        });

        certGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                cert.setImageURI(result);
            }
        });

        bCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certGetContent.launch("image/*");
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    // Method for starting the activity for selecting image from phone storage
   /* public void pick(View view) {
        verifyStoragePermissions(UserDocumentsActivity.this);
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
                        imgPath.setText(part_image);

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
                        image.setImageBitmap(bitmap);
                        //imgPath.setImageBitmap(bitmap);
                        // Set the ImageView with the bitmap of the image
                    }

                    else {
                        Toast.makeText(UserDocumentsActivity.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                    }

                }
            });

    // Method to get the absolute path of the selected image from its URI

*/
    // Upload the image to the remote database
    public void uploadImage(View view) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sImage);
        imageRequest.setUsername("JoseLuis");
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