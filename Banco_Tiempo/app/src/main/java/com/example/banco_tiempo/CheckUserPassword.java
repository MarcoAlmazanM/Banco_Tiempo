package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUserPassword extends AppCompatActivity {

    TextInputLayout tPassword;
    EditText eTPassword;
    String password, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_password);
    }

    public void btnChangeUserPassword(View view){
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

    public boolean validateContra() {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        boolean flag = matcher.matches();

        if (!flag) {
            tPassword = findViewById(R.id.textInputUserContra);
            colorText(tPassword, password);
        }
        return flag;
    }

    public void verifyData(View view){
        // Find Id EditText
        eTPassword = findViewById(R.id.eTUserPassword);

        // Set strings UI
        password = eTPassword.getText().toString();

        if(TextUtils.isEmpty(password)){
            message = "La contraseña es requerida.";
            Toast.makeText(CheckUserPassword.this, message, Toast.LENGTH_LONG).show();
        } else{
            if (validateContra()) {

                Intent changePassword = new Intent(CheckUserPassword.this, ChangePassword.class);
                startActivity(changePassword);

            }
            else {
                message = "La contraseña introducida no es correcta.";
                Toast.makeText(CheckUserPassword.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void userContra(){
        tPassword = findViewById(R.id.textInputUserContra);
    }
}