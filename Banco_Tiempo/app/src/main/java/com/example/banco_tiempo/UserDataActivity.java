package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.CollapsingToolbarLayout;


public class UserDataActivity extends AppCompatActivity {
    Toolbar toolbar;
    ConstraintLayout userDataLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        userDataLayout = findViewById(R.id.user_data);
        toolbar = findViewById(R.id.toolbar);
        setTitle("User Data");
        setSupportActionBar(toolbar);

    }
}