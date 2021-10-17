package com.example.banco_tiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserData extends AppCompatActivity {

    String nombre, apellidoM, apellidoP, calle, colonia, municipio, estado, numInterno, codPostal;
    EditText eTnombre, eTapellidoM, eTapellidoP, eTcalle, eTcolonia, eTmunicipio, eTestado, eTnumInterno, eTcodPostal;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        setUserData();

        eTnombre.setText(nombre, TextView.BufferType.EDITABLE);
        eTapellidoM.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTapellidoP.setText(apellidoP, TextView.BufferType.EDITABLE);
        eTcalle.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTcolonia.setText(colonia, TextView.BufferType.EDITABLE);
        eTmunicipio.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTestado.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTnumInterno.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTcodPostal.setText("Texto de prueba", TextView.BufferType.EDITABLE);

    }

    public void setUserData() {
        nombre = preferences.getString("name","nombre");
        apellidoP = preferences.getString("lastname", "apellidoP");
        colonia = preferences.getString("colonia", "colonia");
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