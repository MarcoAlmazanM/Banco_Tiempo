package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailChangePassword extends AppCompatActivity {

    TextInputLayout tEmailU;
    String emailU, message;
    EditText eTEmailU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change_password);
    }

    public void btnSendEmailPassword(View view){
        verifyData(view);
    }

    public void colorText(TextInputLayout myInputLayout, String myString) {

        myInputLayout.getEditText().setTextColor(Color.parseColor("#ff0000"));
        myInputLayout.getEditText().setText(myString);
        myInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInputLayout.getEditText().setTextColor(Color.BLACK);
            }
        });

    }

    public boolean validateEmail() {
        boolean flag = true;

        try {
            InternetAddress internetAddress = new InternetAddress(emailU);
            internetAddress.validate();
        } catch (AddressException exception) {
            flag = false;
            tEmailU = findViewById(R.id.textInputUserEmail);
            colorText(tEmailU, emailU);
        }

        return flag;
    }

    public void verifyData(View view){
        // Find Id EditText
        eTEmailU = findViewById(R.id.eTEmailChangeP);

        // Set strings UI
        emailU = eTEmailU.getText().toString();

        if(TextUtils.isEmpty(emailU)){
            message = "El correo es requerido.";
            Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
        } else{
            if (validateEmail()) {
                setRecoverPasswordRequest();
            }
            else {
                message = "El correo introducido no es válido.";
                Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setRecoverPasswordRequest(){
        RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest();
        recoverPasswordRequest.setEmail(emailU);
        recoverPassResponse(recoverPasswordRequest);
    }

    public void recoverPassResponse(RecoverPasswordRequest recoverPasswordRequest){
        Call<RecoverPasswordResponse> recoverPasswordResponseCall = ApiClient.getService().recoverPassResponse(recoverPasswordRequest);
        recoverPasswordResponseCall.enqueue(new Callback<RecoverPasswordResponse>() {
            @Override
            public void onResponse(Call<RecoverPasswordResponse> call, Response<RecoverPasswordResponse> response) {
                if (response.isSuccessful()) {
                    RecoverPasswordResponse recoverPasswordResponse = response.body();
                    try {
                        if(recoverPasswordResponse.getEmailApproval() == 1){
                            message = "En breve recibirá un correo electrónico, para restablecer su contraseña.";
                            Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
                        }else{
                            message = recoverPasswordResponse.getError();
                            Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
                        }

                    }catch (NullPointerException nullPointerException){
                        message = "Error al registar, favor de intentar más tarde";
                        Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    message ="Ocurrió un error, favor de intentar más tarde.";
                    Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RecoverPasswordResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void userEmail(){
        tEmailU = findViewById(R.id.textInputUserEmail);
    }
}