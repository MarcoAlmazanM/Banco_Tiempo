package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckUserPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_password);
    }

    public void btnChangeUserPassword(View view){
        Intent changePassword = new Intent(CheckUserPassword.this, ChangePassword.class);
        startActivity(changePassword);
    }
}