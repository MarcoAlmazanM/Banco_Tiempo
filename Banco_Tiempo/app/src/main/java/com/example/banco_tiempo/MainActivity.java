package com.example.banco_tiempo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View headerView;
    TextView nameTextView;

    ActionBarDrawerToggle toggle;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UI
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        nameTextView = (TextView)headerView.findViewById(R.id.nameTextView);
        toolbar = findViewById(R.id.toolbar);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = preferences.edit();

        nameTextView.setText(preferences.getString("Nombre","Nombre del Usuario") + " " + preferences.getString("Apellido","Nombre del Usuario"));
        //Always Display Home UI
        getSupportFragmentManager().beginTransaction().add(R.id.content, new HomeFragment()).commit();
        setTitle("Home");

        // Setup Toolbar
        setSupportActionBar(toolbar);


        toggle = setUpDrawerToggle();
        mDrawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);
    }

    private ActionBarDrawerToggle setUpDrawerToggle(){
        return new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch(item.getItemId()){
            case R.id.nav_home:
                ft.replace(R.id.content, new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                ft.replace(R.id.content, new ProfileFragment()).commit();
                break;
            case R.id.nav_event:
                ft.replace(R.id.content, new HomeFragment()).commit();
                break;
            case R.id.nav_notification:
                ft.replace(R.id.content, new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                ft.replace(R.id.content, new SettingsFragment()).commit();
                break;
        }
        setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}