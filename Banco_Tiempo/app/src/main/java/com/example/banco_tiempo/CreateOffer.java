package com.example.banco_tiempo;

import androidx.activity.result.ActivityResult;
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
import android.graphics.Color;
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

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOffer extends AppCompatActivity {
    Toolbar toolbar;
    ConstraintLayout createOfferLayout;

    ImageView imgOffer;
    Uri selectedImage;
    String part_image;
    String sImage;
    String sCert;
    TextView bCert;
    ImageView cert;
    TextView category, title, description;
    String cat, titxd, des, message;
    TextInputLayout tTitulo, tCategoria, tDescripcion;

    AutoCompleteTextView autoCTV;
    ArrayAdapter<String> adapterItems;
    String[] items = {"Administración", "Computación", "Matemáticas", "Ingeniería",
            "Actuaría", "Educación", "Artes, Diseño, y Comunicación", "Comida",
            "Limpieza", "Construcción", "Deportes", "Cuidado Personal", "Ventas",
            "Agricultura, Pesca, y Silvicultura", "Instalación, Mantenimiento, y Reparación",
            "Producción de Materiales", "Transporte de Personas y Materiales"};

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
        toolbar = findViewById(R.id.toolbar);
        setTitle("Create New Offer");
        setSupportActionBar(toolbar);

        imgOffer = findViewById(R.id.iVPhoto1);


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

    public void colorText(TextInputLayout myInputLayout, String myString) {

        myInputLayout.getEditText().setTextColor(Color.parseColor("#ff0000"));
        myInputLayout.getEditText().setText(myString);
        myInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                myInputLayout.getEditText().setTextColor(Color.BLACK);
            }
        });

    }

    public boolean validateFields() {

        //String regex = "\\w{1,255}";
        //String regex = "^(\\w ?){1,127}$";
        String regex = ".{1,255}";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher1 = pattern.matcher(cat);
        Matcher matcher2 = pattern.matcher(titxd);
        Matcher matcher3 = pattern.matcher(des);

        boolean flag1 = matcher1.matches();
        boolean flag2 = matcher2.matches();
        boolean flag3 = matcher3.matches();

        if (!flag1) {
            tCategoria = findViewById(R.id.textInputCategoriaOffer);
            colorText(tCategoria, cat);
        }
        if (!flag2) {
            tTitulo = findViewById(R.id.textInputTitleOffer);
            colorText(tTitulo, titxd);
        }
        if (!flag3) {
            tDescripcion = findViewById(R.id.textInputDescOffer);
            colorText(tDescripcion, des);
        }

        boolean flag = flag1 && flag2 && flag3;

        return flag;
    }

    public void pick(View view) {
        verifyStoragePermissions(CreateOffer.this);
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(Intent.createChooser(i, "Select Picture"));
    }

    public void clickBtnCert(View view){
        verifyStoragePermissions(CreateOffer.this);
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        certGetContent.launch(Intent.createChooser(i, "Select Picture"));
    }

    private void changeBtn(TextView b) {
        b.setText("Documento cargado");
        b.setBackgroundColor(getColor(R.color.gradientLightGreen));
        b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.username, 0);
    }

    public void addNewOffer(View view){
        imgOffer = findViewById(R.id.iVPhoto1);
        cert = findViewById(R.id.certiView);
        category = findViewById(R.id.tVAutoComplete);
        title = findViewById(R.id.tVAutoComplete2);
        description = findViewById(R.id.tVDescription);

        cat = category.getText().toString();
        //cat = Normalizer.normalize(cat, Normalizer.Form.NFD);
        //cat = cat.replaceAll("[^\\p{ASCII}]", "");

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
            if (validateFields()) {
                NewOfferRequest newOfferRequest = new NewOfferRequest();
                newOfferRequest.setUsername(username);
                newOfferRequest.setCategoria(cat);
                newOfferRequest.setNombre(titxd);
                newOfferRequest.setDescripcion(des);
                newOfferRequest.setImage(sImage);
                newOfferRequest.setColonia(colonia);
                if (TextUtils.isEmpty(sCert)) {
                    newOfferRequest.setCertificado("NULL");
                } else {
                    newOfferRequest.setCertificado(sCert);
                }
                uploadNewOffer(newOfferRequest);
            }
            else{
                message = "Los campos en color rojo son incorrectos, por favor revise su contenido.";
                Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void createOfferIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            NewOfferResponse newOfferResponse = (NewOfferResponse) intent.getSerializableExtra("data");
            try {
                if (newOfferResponse.getTransactionApproval() == 1) {
                    message = "Oferta creada correctamente";
                    Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
                } else {
                    message = "Error al crear la oferta";
                    Toast.makeText(CreateOffer.this, message, Toast.LENGTH_LONG).show();
                }
            }catch(NullPointerException nullPointerException){
                message = "Error al crear la oferta, favor de intentar más tarde";
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


    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        if (null != uri) {

                            imgOffer.setImageURI(uri);
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                byte[] byteArray = outputStream.toByteArray();
                                //Encode Base 64 Image
                                sImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> certGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        if (null != uri) {
                            changeBtn(bCert);
                            cert.setImageURI(uri);
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                byte[] byteArray = outputStream.toByteArray();
                                //Encode Base 64 Image
                                sCert = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


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