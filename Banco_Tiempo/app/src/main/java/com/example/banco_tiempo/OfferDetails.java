package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class OfferDetails extends AppCompatActivity {

    TextView nombre;
    TextView servicio;
    TextView descripcion;

    ImageView perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        ElementList oferta=(ElementList) getIntent().getSerializableExtra("ElementList");
        nombre=findViewById(R.id.userName);
        servicio=findViewById(R.id.userJob);
        descripcion=findViewById(R.id.jobDescription);
        perfil=findViewById(R.id.userJobImage);

        nombre.setText(oferta.getNombre());
        servicio.setText(oferta.getTrabajo());
        //descripcion.setText(oferta.get);
        //perfil.setImageBitmap(oferta.get);
    }
}
