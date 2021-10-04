package com.example.banco_tiempo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateOffer extends AppCompatActivity {

    ImageView img1;
    Uri selectedImage;
    String part_image;
    String sImage;
    TextView imgPath;
    AutoCompleteTextView autoCTV, autoCTV2;
    ArrayAdapter<String> adapterItems, adapterItems2;
    String[] items = {"Carpintería", "Sastrería", "Repostería", "Tutoría", "Plomería"};
    String[] items2 = {"Toluca", "Metepec", "Otro pueblo xd"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        img1 = findViewById(R.id.iVPhoto1);
        imgPath = findViewById(R.id.tVImageN);

        autoCTV = findViewById(R.id.tVAutoComplete);
        autoCTV2 = findViewById(R.id.tVAutoComplete2);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_empleos_item, items);
        autoCTV.setAdapter(adapterItems);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_empleos_item, items2);
        autoCTV2.setAdapter(adapterItems2);

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
                        img1.setImageBitmap(bitmap);
                        // Set the ImageView with the bitmap of the image
                    }

                    else {
                        Toast.makeText(CreateOffer.this,"Algo Salio mal", Toast.LENGTH_SHORT);
                    }

                }
            });
}