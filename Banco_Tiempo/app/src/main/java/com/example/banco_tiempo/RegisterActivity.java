package com.example.banco_tiempo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkPrivacity;
    EditText eTNombre, eTAP,eTAM, eTCalle, eTNumInt,
            eTColonia,eTMunicipio, eTEstado, eTCP,
            eTEmailR, eTUsername, eTPassword, eTPasswordConfirm;
    String nombre, apellidoPaterno, apellidoMaterno, calle, numInt, colonia,
            municipio, estado, cP, email, username, password, passwordConfirm, message, passwordHash, sImage;

    ImageView profileImage;

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        registerIntent();
        // Image View
        profileImage = findViewById(R.id.iVProfPic);
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

        }
    }

    public void verifyData(View view){
        // Find Id EditText
        eTNombre =findViewById(R.id.eTNombre);
        eTAP = findViewById(R.id.eTAP);
        eTAM = findViewById(R.id.eTAM);
        eTEmailR = findViewById(R.id.eTEmailR);
        eTUsername = findViewById(R.id.eTUsernameR);
        eTPassword = findViewById(R.id.eTPasswordR);
        eTPasswordConfirm = findViewById(R.id.eTPasswordConfirm);

        // Set strings UI
        nombre = eTNombre.getText().toString();
        apellidoPaterno = eTAP.getText().toString();
        apellidoMaterno = eTAM.getText().toString();
        email = eTEmailR.getText().toString();
        username = eTUsername.getText().toString();
        password = eTPassword.getText().toString();
        passwordConfirm = eTPasswordConfirm.getText().toString();

        if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellidoPaterno) || TextUtils.isEmpty(apellidoMaterno)  ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(passwordConfirm)){
            message = "Todos los campos son requeridos.";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else if (!password.equals(passwordConfirm)){
            message = "Las contraseñas no coinciden .";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(sImage)){
            message = "La foto de perfil es requerida";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else{
            Resources resources = new Resources();
            passwordHash = resources.hash256(password);
            setContentView(R.layout.activity_register_second);
        }
    }

    public void verifyData2(View view){

        // Find Id EditText
        eTCalle = findViewById(R.id.eTCalle);
        eTNumInt = findViewById(R.id.eTNumInt);
        eTColonia = findViewById(R.id.eTColonia);
        eTMunicipio = findViewById(R.id.eTMunicipio);
        eTEstado = findViewById(R.id.eTEstado);
        eTCP = findViewById(R.id.eTCP);
        checkPrivacity = findViewById(R.id.cBPrivacity);

        // Set strings UI
        calle = eTCalle.getText().toString();
        numInt = eTNumInt.getText().toString();
        colonia = eTColonia.getText().toString();
        municipio = eTMunicipio.getText().toString();
        estado = eTEstado.getText().toString();
        cP = eTCP.getText().toString();

        if (TextUtils.isEmpty(calle)|| TextUtils.isEmpty(numInt) || TextUtils.isEmpty(colonia) ||
                TextUtils.isEmpty(municipio) || TextUtils.isEmpty(estado) || TextUtils.isEmpty(cP)){

        }else if(!checkPrivacity.isChecked()){
            message = "Se requiere aceptar las condiciones de uso y privacidad de la aplicación.";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else{
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setNombre(nombre);
            registerRequest.setApellidoP(apellidoPaterno);
            registerRequest.setApellidoM(apellidoMaterno);
            registerRequest.setCalle(calle);
            registerRequest.setNumero(numInt);
            registerRequest.setColonia(colonia);
            registerRequest.setMunicipio(municipio);
            registerRequest.setEstado(estado);
            registerRequest.setCPP(cP);
            registerRequest.setCorreo(email);
            registerRequest.setIdUsuario(username);
            registerRequest.setContrasena(passwordHash);
            registerRequest.setImage(sImage);
            registerUser(registerRequest);
        }
    }

    public void clickBtnProfileImage(View view){
        verifyStoragePermissions(RegisterActivity.this);
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(Intent.createChooser(i, "Select Picture"));
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
                            Transformation transformation = new RoundedCornersTransformation(100,5);
                            Picasso.get().load(Uri.parse(uri.toString())).resize(120,120).centerCrop().transform(transformation).into(profileImage);
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
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


    public void registerIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            RegisterResponse registerResponse = (RegisterResponse) intent.getSerializableExtra("data");
            try {
                if (registerResponse.getRegisterApproval() == 1) {
                    message = "Usuario Registrado Exitosamente";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                } else {
                    message = registerResponse.getError();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }catch (NullPointerException nullPointerException){
                message = "Error al registar, favor de intentar más tarde";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    startActivity(new Intent(RegisterActivity.this, RegisterActivity.class).putExtra("data", registerResponse));
                    finish();
                } else {
                    message ="Ocurrió un error, favor de intentar más tarde.";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goBackLogin(View view){
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void goBackRegister(View view){
        Intent register = new Intent(RegisterActivity.this, RegisterActivity.class);
        startActivity(register);
        finish();
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