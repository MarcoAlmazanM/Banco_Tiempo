package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkPrivacity;
    EditText eTNombre, eTAP,eTAM, eTCalle, eTNumInt,
            eTColonia,eTMunicipio, eTEstado, eTCP,
            eTEmailR, eTUsername, eTPassword, eTPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            RegisterResponse registerResponse = (RegisterResponse) intent.getSerializableExtra("data");
            Log.e("TAG","MSGGG---->"+registerResponse.getRegisterApproval() );
            if (registerResponse.getRegisterApproval() == 1) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }else{
                String message = registerResponse.getError();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
    public void verifyData(View view){
        eTNombre = findViewById(R.id.eTNombre);
        eTAP = findViewById(R.id.eTAP);
        eTAM = findViewById(R.id.eTAM);
        eTCalle = findViewById(R.id.eTCalle);
        eTNumInt = findViewById(R.id.eTNumInt);
        eTColonia = findViewById(R.id.eTColonia);
        eTMunicipio = findViewById(R.id.eTMunicipio);
        eTEstado = findViewById(R.id.eTEstado);
        eTCP = findViewById(R.id.eTCP);
        eTEmailR = findViewById(R.id.eTEmailR);
        eTUsername = findViewById(R.id.eTUsernameR);
        eTPassword = findViewById(R.id.eTPasswordR);
        eTPasswordConfirm = findViewById(R.id.eTPasswordConfirm);
        checkPrivacity = findViewById(R.id.cBPrivacity);

        if(TextUtils.isEmpty(eTNombre.getText().toString()) || TextUtils.isEmpty(eTAP.getText().toString()) ||
                TextUtils.isEmpty(eTAM.getText().toString()) || TextUtils.isEmpty(eTCalle.getText().toString())||
                TextUtils.isEmpty(eTNumInt.getText().toString()) || TextUtils.isEmpty(eTColonia.getText().toString()) ||
                TextUtils.isEmpty(eTMunicipio.getText().toString()) || TextUtils.isEmpty(eTEstado.getText().toString()) ||
                TextUtils.isEmpty(eTCP.getText().toString()) || TextUtils.isEmpty(eTEmailR.getText().toString()) ||
                TextUtils.isEmpty(eTUsername.getText().toString()) || TextUtils.isEmpty(eTPassword.getText().toString()) ||
                TextUtils.isEmpty(eTPasswordConfirm.getText().toString())){
            String message = "All inputs required ...";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(eTPassword.getText().toString()) != TextUtils.isEmpty(eTPassword.getText().toString())){
            String message = "Both Passwords have to be equal ...";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else if(checkPrivacity.isChecked() == false ){
            String message = "You need to accept the notice of privacy ...";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else{
            String password = hash256(eTPassword.getText().toString());
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setNombre(eTNombre.getText().toString());
            registerRequest.setApellidoP(eTAP.getText().toString());
            registerRequest.setApellidoM(eTAM.getText().toString());
            registerRequest.setCalle(eTCalle.getText().toString());
            registerRequest.setNumero(eTNumInt.getText().toString());
            registerRequest.setColonia(eTColonia.getText().toString());
            registerRequest.setMunicipio(eTMunicipio.getText().toString());
            registerRequest.setEstado(eTEstado.getText().toString());
            registerRequest.setCPP(eTCP.getText().toString());
            registerRequest.setCorreo(eTEmailR.getText().toString());
            registerRequest.setIdUsuario(eTUsername.getText().toString());
            registerRequest.setContrasena(password);
            registerUser(registerRequest);
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
                    String message = "An error occurred, please try again...";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Section Hash Sha 256
    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private String hash256(String password){
        MessageDigest digest=null;
        String hash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            hash = bytesToHexString(digest.digest());
        } catch(NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return hash;
    }
}