package com.example.banco_tiempo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {

    Toolbar toolbar;
    String newPassword1, newPassword2, message;
    TextInputLayout tNewPassword1, tNewPassword2;
    EditText eTNewPassword1, eTNewPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.WHITE);
        setTitle("Actualizar Información");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void btnChangePasswordW(View view){
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

    public boolean validatePassword() {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(newPassword1);

        boolean flag = matcher.matches();

        if (!flag) {
            tNewPassword1 = findViewById(R.id.textInputNewPassword);
            colorText(tNewPassword1, newPassword1);
            tNewPassword2 = findViewById(R.id.textInputNewPassword2);
            colorText(tNewPassword2, newPassword1);
        }
        return flag;
    }

    public void verifyData(View view){
        // Find Id EditText
        eTNewPassword1 = findViewById(R.id.eTNewPassword);
        eTNewPassword2 = findViewById(R.id.eTNewPassword2);

        // Set strings UI
        newPassword1 = eTNewPassword1.getText().toString();
        newPassword2 = eTNewPassword2.getText().toString();

        if(TextUtils.isEmpty(newPassword1) || TextUtils.isEmpty(newPassword2)){
            message = "Hay uno o más campos vacíos.";
            Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();
        }
        else if (!newPassword1.equals(newPassword2)){
            message = "Las contraseñas no coinciden.";
            Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();
        } else{
            if (validatePassword()) {

                //Código
                message = "Se ha restablecido la contraseña.";
                Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();

            }
            else {
                message = "La contraseña no tiene el formato adecuado.";
                Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}