package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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

                //Código
                message = "Se ha enviado un enlace de verificación a su correo.";
                Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();

            }
            else {
                message = "El correo introducido no es válido.";
                Toast.makeText(EmailChangePassword.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void userEmail(){
        tEmailU = findViewById(R.id.textInputUserEmail);
    }
}