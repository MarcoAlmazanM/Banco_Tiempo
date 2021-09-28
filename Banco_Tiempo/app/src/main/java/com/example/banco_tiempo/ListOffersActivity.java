package com.example.banco_tiempo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListOffersActivity extends AppCompatActivity {

    List<ElementList>ofertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offers);

        init();
    }

    public void init(){
        ofertas=new ArrayList<>();
        ofertas.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        ofertas.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        ofertas.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        ofertas.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));
        ofertas.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        ofertas.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        ofertas.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        ofertas.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));
        ofertas.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        ofertas.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        ofertas.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        ofertas.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));

        ListAdapter lista=new ListAdapter(ofertas, this, new ListAdapter.ClickListener() {
            @Override
            public void clickListener(ElementList element) {
                goDetails(element);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.listaOfertas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lista);
    }

    public void goDetails(ElementList element){
        Intent intent=new Intent(this, OfferDetails.class);
        intent.putExtra("ElementList", element);
        startActivity(intent);
    }

    //public void goDetails(View view){
      //  Intent intent=new Intent(ListOffersActivity.this, DetallesOfertas.class);
        //startActivity(intent);

    //}
}