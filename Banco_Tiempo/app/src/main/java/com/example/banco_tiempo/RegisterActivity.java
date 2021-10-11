package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkPrivacity;
    EditText eTNombre, eTAP,eTAM, eTCalle, eTNumInt,
            eTColonia,eTMunicipio, eTEstado, eTCP,
            eTEmailR, eTUsername, eTPassword, eTPasswordConfirm;
    String nombre, apellidoPaterno, apellidoMaterno, calle, numInt, colonia,
            municipio, estado, cP, email, username, password, passwordConfirm, message, passwordHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        registerIntent();
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
                TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)){
            message = "Todos los campos son requeridos ...";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else if (!password.equals(passwordConfirm)){
            message = "Las contraseñas no coinciden .";
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
            registerUser(registerRequest);
        }
    }


    public void registerIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            RegisterResponse registerResponse = (RegisterResponse) intent.getSerializableExtra("data");
            if (registerResponse.getRegisterApproval() == 1) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }else{
                message = registerResponse.getError();
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
}