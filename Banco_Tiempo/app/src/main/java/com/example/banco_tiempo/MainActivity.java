package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View headerView;
    TextView nameTextView;
    ImageView userProfileImage;

    ActionBarDrawerToggle toggle;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets application context
        contextOfApplication = getApplicationContext();

        //SET UI
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        userProfileImage =(ImageView)headerView.findViewById(R.id.user_profile_image);
        nameTextView = (TextView)headerView.findViewById(R.id.nameTextView);
        toolbar = findViewById(R.id.toolbar);
        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //Set Header Nav
        String name = preferences.getString("name","Nombre del Usuario");
        String lastName = preferences.getString("lastName","");

        nameTextView.setText( name + " " + lastName);

        imageIntent();

        String imageUrl = preferences.getString("foto",null);

        if (imageUrl.equals("NULL")){
            userProfileImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_account_circle_black_48,null));
        }else{
            Picasso.get().invalidate(imageUrl);
            Transformation transformation = new RoundedCornersTransformation(100,5);
            Picasso.get().load(imageUrl).resize(120,120).centerCrop().transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(userProfileImage);
        }


        //Always Display Inicio UI
        getSupportFragmentManager().beginTransaction().add(R.id.content, new FragmentInicio()).commit();
        setTitle("Inicio");

        // Setup Toolbar
        setSupportActionBar(toolbar);


        toggle = setUpDrawerToggle();
        mDrawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);
    }

    public void imageIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ImageResponse imageResponse = (ImageResponse) intent.getSerializableExtra("data");
            if(imageResponse.getTransactionApproval() == 1){
                preferences = getSharedPreferences("userData",Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("foto",imageResponse.getUrl());
                editor.apply();
            }
        }
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
                ft.replace(R.id.content, new FragmentInicio()).commit();
                break;
            case R.id.nav_profile:
                ft.replace(R.id.content, new ProfileFragment()).commit();
                break;
            case R.id.nav_searchOffers:
                ft.replace(R.id.content, new FilterFragment()).commit();
                break;
            case R.id.nav_newOffer:
                ft.replace(R.id.content, new NewOfferFragment()).commit();
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

    public void closeSession(MenuItem item) {
        editor.putBoolean("SaveSession",false);
        editor.apply();
        String message = "Closed Session";
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}