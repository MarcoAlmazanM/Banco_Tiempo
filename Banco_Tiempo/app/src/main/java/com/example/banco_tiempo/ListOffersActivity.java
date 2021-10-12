package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//implements SearchView.OnQueryTextListener
public class ListOffersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ConstraintLayout listOffersLayout;
    Toolbar toolbar;

    List<ElementList>ofertas;
    List<OffersDetails>offersD;
    ListAdapter adapter;
    //List<ElementList>filtrado;
    SearchView searchList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String categoria;
    String colonia;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offers);

        setTitle("Lista de Ofertas");
        setSupportActionBar(toolbar);


        ofertas = new ArrayList<>();
        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        categoria = preferences.getString("categoria","NULL");
        colonia = preferences.getString("colonia", "NULL");
        setOffersValues();


        initViews();
        initListener();

        //searchList.setOnQueryTextListener(this);
    }

    public void setOffersValues(){
        OffersRequest offersRequest = new OffersRequest();
        offersRequest.setCategoria(categoria);
        offersRequest.setColonia(colonia);
        getOffers(offersRequest);
    }

    public void getOffers(OffersRequest offersRequest){
        Call<OffersResponse> offersResponseCall = ApiClient.getService().getOffers(offersRequest);
        offersResponseCall.enqueue(new Callback<OffersResponse>() {
            @Override
            public void onResponse(Call<OffersResponse> call, Response<OffersResponse> response) {
                if (response.isSuccessful()) {
                    OffersResponse offersResponse = response.body();
                    offersD = new ArrayList<>(Arrays.asList(offersResponse.getOfertas()));
                    Log.e("lol", Arrays.deepToString(offersD.toArray()));
                    init();
                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(ListOffersActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OffersResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(ListOffersActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initListener(){
        searchList.setOnQueryTextListener(this);
    }

    private void initViews(){
        searchList=findViewById(R.id.searchList);
        listOffersLayout = findViewById(R.id.listOffers);
        toolbar = findViewById(R.id.toolbar);
    }


    public void init(){

        ofertas=new ArrayList<>();

        for (int i = 0; i < offersD.size(); i++){
            Integer idServicio = offersD.get(i).getIdServicio();
            String idUsuario = offersD.get(i).getIdUsuario();
            String colonia = offersD.get(i).getColonia();
            String nombre = offersD.get(i).getNombre();
            String descripcion = offersD.get(i).getDescripcion();
            String certificado = offersD.get(i).getCertificado();
            String image = offersD.get(i).getImage();
            String nombreUsuario = offersD.get(i).getNombreUsuario();
            String apellidoUsuario = offersD.get(i).getApellidoUsuario();
            String foto = offersD.get(i).getFoto();

            ElementList oferta = new ElementList(idServicio,idUsuario,colonia,
                                                nombre,descripcion,certificado,
                                                image,nombreUsuario,apellidoUsuario,
                                                foto, "#ff0000");


            ofertas.add(oferta);
        }
        /*
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
        */

        adapter=new ListAdapter(ofertas, this, new ListAdapter.ClickListener() {
            @Override
            public void clickListener(ElementList element) {
                goDetails(element);

            }
        });
        RecyclerView recyclerView=findViewById(R.id.listaOfertas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void goDetails(ElementList element){
        Intent intent=new Intent(this, OfferDetails.class);
        intent.putExtra("ElementList", element);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //filtro(s);
        //adapter=new ListAdapter(ofertas,this, adapter.action);
        adapter.filtro(s);
        return false;
    }

}