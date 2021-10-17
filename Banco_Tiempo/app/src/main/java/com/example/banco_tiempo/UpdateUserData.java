package com.example.banco_tiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class UpdateUserData extends AppCompatActivity {

    String nombre, apellidoM, apellidoP, calle, colonia, municipio, estado, numInterno, codPostal;
    Integer cp;
    EditText eTnombre, eTapellidoM, eTapellidoP, eTcalle, eTcolonia, eTmunicipio, eTestado, eTnumInterno, eTcodPostal;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircularProgressButton circularProgressButton;

    TextView btnUpdateUD, btnCancelUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_user_data);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        eTnombre = findViewById(R.id.eTNombreU);
        eTapellidoM = findViewById(R.id.eTApellidoMU);
        eTapellidoP = findViewById(R.id.eTApellidoPU);
        eTcalle = findViewById(R.id.eTCalleU);
        eTcolonia = findViewById(R.id.eTColoniaU);
        eTmunicipio = findViewById(R.id.eTMunicipioU);
        eTestado = findViewById(R.id.eTEstadoU);
        eTnumInterno = findViewById(R.id.eTNumeroIU);
        eTcodPostal = findViewById(R.id.eTCodigoPU);

        obtainUserData();

        circularProgressButton = findViewById(R.id.btnActualizarDatos);

        View view = findViewById(android.R.id.content).getRootView();
        updateUserData(circularProgressButton, view);
    }

    public void obtainUserData() {
        nombre = preferences.getString("name","Nombre");
        apellidoP = preferences.getString("lastName", "Apellido Paterno");
        apellidoM = preferences.getString("lastNameM","Apellido Materno");
        calle = preferences.getString("calle","Calle");
        colonia = preferences.getString("colonia", "Colonia");
        municipio = preferences.getString("municipio","Municipio");
        estado = preferences.getString("estado","Estado");
        numInterno = preferences.getString("numInt","Numero Interno");
        cp = preferences.getInt("codigoP",0);
        codPostal = cp.toString();
        setActualUserData();
    }
    public void setActualUserData(){
        eTnombre.setText(nombre, TextView.BufferType.EDITABLE);
        eTapellidoM.setText(apellidoM, TextView.BufferType.EDITABLE);
        eTapellidoP.setText(apellidoP, TextView.BufferType.EDITABLE);
        eTcalle.setText(calle, TextView.BufferType.EDITABLE);
        eTcolonia.setText(colonia, TextView.BufferType.EDITABLE);
        eTmunicipio.setText(municipio, TextView.BufferType.EDITABLE);
        eTestado.setText(estado, TextView.BufferType.EDITABLE);
        eTnumInterno.setText(numInterno, TextView.BufferType.EDITABLE);
        eTcodPostal.setText(codPostal, TextView.BufferType.EDITABLE);
    }
    public void updateUserData (CircularProgressButton btn, View view) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_update, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;// lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.setElevation(20);
                }

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setTouchInterceptor(new View.OnTouchListener()
                {

                    public boolean onTouch(View v, MotionEvent event)
                    {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {

                            popupWindow.dismiss();
                            return true;
                        }

                        return false;
                    }
                });


                // dismiss the popup window when touched
                btnUpdateUD = (TextView) popupView.findViewById(R.id.btnUpdateUD);

                btnUpdateUD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getuserInfo();
                        popupWindow.dismiss();
                    }
                });

                btnCancelUpdate = (TextView) popupView.findViewById(R.id.btnCancelUpdate);

                btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

    }

    public void getuserInfo(){
        nombre = eTnombre.getText().toString();
        apellidoM = eTapellidoM.getText().toString();
        apellidoP = eTapellidoP.getText().toString();
        calle = eTcalle.getText().toString();
        colonia = eTcolonia.getText().toString();
        municipio = eTmunicipio.getText().toString();
        estado = eTestado.getText().toString();
        numInterno = eTnumInterno.getText().toString();
        codPostal = eTcodPostal.getText().toString();
    }
}