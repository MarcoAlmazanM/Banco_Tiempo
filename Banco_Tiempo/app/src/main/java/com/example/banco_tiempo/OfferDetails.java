package com.example.banco_tiempo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class OfferDetails extends AppCompatActivity {

    TextView nombre;
    TextView servicio;
    TextView descripcion;
    TextView id;

    ImageView perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        ElementList oferta=(ElementList) getIntent().getSerializableExtra("ElementList");
        nombre=findViewById(R.id.textView);
        servicio=findViewById(R.id.trabajo);
        descripcion=findViewById(R.id.jobDescription);
        perfil=findViewById(R.id.userJobImage);
        id = findViewById(R.id.serviceGiven);

        nombre.setText(oferta.getNombreUsuario());
        servicio.setText(oferta.getNombre());
        descripcion.setText(oferta.getDescripcion());
        Integer value = oferta.getIdServicio();
        id.setText((("#"+value.toString())));

        String url = oferta.getImage();
        Transformation transformation = new RoundedCornersTransformation(50,5);
        Picasso.get().invalidate(url);
        Picasso.get().load(url).transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(perfil);
    }
}
