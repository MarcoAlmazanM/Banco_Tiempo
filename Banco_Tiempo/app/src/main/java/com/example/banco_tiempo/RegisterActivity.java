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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
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

    Boolean saveUserData =false;

    Integer codigo;

    TextInputLayout tNombre, tAP,tAM, tCalle, tNumInt,
            tColonia,tMunicipio, tEstado, tCP,
            tEmailR, tUsername, tPassword, tPasswordConfirm;

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
        //registerIntent();
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
            if(saveUserData){
                userInfo2();
            }
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


    public void userInfo(Integer codigo){
        tNombre = findViewById(R.id.textInputName);
        tAM = findViewById(R.id.textInputAM);
        tAP = findViewById(R.id.textInputAP);
        tUsername=findViewById(R.id.textInputUsername);
        tEmailR = findViewById(R.id.textInputEmail);
        tPassword = findViewById(R.id.textInputPassword);
        tPasswordConfirm = findViewById(R.id.textInputPasswordConf);
        tNombre.getEditText().setText(nombre);
        tAM.getEditText().setText(apellidoMaterno);
        tAP.getEditText().setText(apellidoPaterno);
        if(codigo == 0){
            tUsername.getEditText().setTextColor(Color.parseColor("#ff0000"));
            tUsername.getEditText().setText(username);
            tEmailR.getEditText().setText(email);
        }else if(codigo == 1){
            tUsername.getEditText().setText(username);
            tEmailR.getEditText().setTextColor(Color.parseColor("#ff0000"));
            tEmailR.getEditText().setText(email);
        }else if(codigo == 2){
            tUsername.getEditText().setText(username);
            tEmailR.getEditText().setText(email);
        }
        tPassword.getEditText().setText(password);
        tPasswordConfirm.getEditText().setText(password);
    }

    public void userInfo2(){
        tCalle = findViewById(R.id.textInputCalle);
        tColonia = findViewById(R.id.textInputColonia);
        tMunicipio = findViewById(R.id.textInputMunicipio);
        tEstado = findViewById(R.id.textInputEstado);
        tNumInt = findViewById(R.id.textInputNumInt);
        checkPrivacity = findViewById(R.id.cBPrivacity);
        tCP = findViewById(R.id.textInputCP);
        tCalle.getEditText().setText(calle);
        tColonia.getEditText().setText(colonia);
        tMunicipio.getEditText().setText(municipio);
        tEstado.getEditText().setText(estado);
        tNumInt.getEditText().setText(numInt);
        tCP.getEditText().setText(cP);
        checkPrivacity.setChecked(true);
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    try {
                        if (registerResponse.getRegisterApproval() == 1) {
                            message = "Usuario Registrado Exitosamente";
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(login);
                            finish();
                        } else {
                            setContentView(R.layout.activity_register);
                            message = registerResponse.getError();
                            if(message.equals("Usuario duplicado")){
                                codigo = 0;
                            }else if(message.equals("Correo duplicado")){
                                codigo=1;
                            }
                            userInfo(codigo);
                            saveUserData = true;
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }catch (NullPointerException nullPointerException){
                        message = "Error al registar, favor de intentar más tarde";
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
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
        setContentView(R.layout.activity_register);
        codigo = 2;
        userInfo(codigo);
        /*Intent register = new Intent(RegisterActivity.this, RegisterActivity.class);
        startActivity(register);
        finish();*/
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