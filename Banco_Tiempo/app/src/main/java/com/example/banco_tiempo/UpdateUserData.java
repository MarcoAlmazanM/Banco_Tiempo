package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateUserData extends AppCompatActivity {

    String nombre, apellidoM, apellidoP, calle, colonia, municipio, estado, numInterno, codPostal;
    EditText eTnombre, eTapellidoM, eTapellidoP, eTcalle, eTcolonia, eTmunicipio, eTestado, eTnumInterno, eTcodPostal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_data);

        eTnombre = findViewById(R.id.eTNombreU);
        eTapellidoM = findViewById(R.id.eTApellidoMU);
        eTapellidoP = findViewById(R.id.eTApellidoPU);
        eTcalle = findViewById(R.id.eTCalleU);
        eTcolonia = findViewById(R.id.eTColoniaU);
        eTmunicipio = findViewById(R.id.eTMunicipioU);
        eTestado = findViewById(R.id.eTEstadoU);
        eTnumInterno = findViewById(R.id.eTNumeroIU);
        eTcodPostal = findViewById(R.id.eTCodigoPU);

        eTnombre.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTapellidoM.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTapellidoP.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTcalle.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTcolonia.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTmunicipio.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTestado.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTnumInterno.setText("Texto de prueba", TextView.BufferType.EDITABLE);
        eTcodPostal.setText("Texto de prueba", TextView.BufferType.EDITABLE);

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