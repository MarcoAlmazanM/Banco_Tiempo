package com.example.banco_tiempo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , NotificationAdapter.OnUpdateListener {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View headerView;
    TextView nameTextView;
    ActionBarDrawerToggle toggle;
    ImageView userProfileImage;

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

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toggle.setHomeAsUpIndicator(R.drawable.burger_icon);

        mDrawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content, new ProfileFragment());
                ft.addToBackStack(null);
                ft.commit();
                mDrawerLayout.closeDrawers();
            }
        });
    }

    public void imageIntent(){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ImageResponse imageResponse = (ImageResponse) intent.getSerializableExtra("data");
            if (imageResponse.getTransactionApproval() == 1) {
                preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("foto", imageResponse.getUrl());
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

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //super.onBackPressed();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setTitle("Salir de la Aplicación");
            builder1.setMessage("¿Seguro que desea salir de la aplicación?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    });
            builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        } else {
            while (count > 0) {
                getSupportFragmentManager().popBackStack();
                count = count - 1;
            }
            setTitle("Inicio");
        }
    }


    private void selectItemNav(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch(item.getItemId()){
            case R.id.nav_home:
                ft.replace(R.id.content, new FragmentInicio());
                break;
            case R.id.nav_profile:
                ft.replace(R.id.content, new ProfileFragment());
                break;
            case R.id.nav_newOffer:
                ft.replace(R.id.content, new NewOfferFragment());
                break;
            case R.id.nav_notification:
                ft.replace(R.id.content, new NotificationFragment());
                break;
            case R.id.nav_rateOffer:
                ft.replace(R.id.content, new RateOffer());

                break;
            case R.id.nav_settings:
                ft.replace(R.id.content, new SettingsFragment());

                break;

        }
        ft.addToBackStack(null);
        ft.commit();
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Salir de la Sesión");
        builder1.setMessage("¿Seguro que desea cerrar su sesión?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putBoolean("SaveSession",false);
                        editor.apply();
                        String message = "Sesión cerrada con éxito";
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                });
        builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    public void onUpdate(Integer text) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, new NotificationFragment());
        ft.addToBackStack(null);
        ft.commit();
        setTitle("Hola");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}