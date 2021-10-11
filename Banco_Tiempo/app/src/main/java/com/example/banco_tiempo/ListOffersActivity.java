package com.example.banco_tiempo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banco_tiempo.ListAdapter.*;

import java.util.ArrayList;
import java.util.List;

//implements SearchView.OnQueryTextListener
public class ListOffersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ConstraintLayout listOffersLayout;
    Toolbar toolbar;

    List<ElementList>ofertas;
    ListAdapter adapter;
    //List<ElementList>filtrado;
    SearchView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offers);

        setTitle("Lista de Ofertas");
        setSupportActionBar(toolbar);
        initViews();
        init();
        initListener();

        //searchList.setOnQueryTextListener(this);
    }
    private void initListener(){
        searchList.setOnQueryTextListener(this);
    }

    private void initViews(){
        searchList=findViewById(R.id.searchList);
        listOffersLayout = findViewById(R.id.listOffers);
        toolbar = findViewById(R.id.toolbar);
    }

    /*private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);

        items = getItems();
        adapter = new RecyclerAdapter(items, this);
        rvLista.setAdapter(adapter);
    }

     */

   /* private List<ElementList> getItems() {
        List<ElementList> elementLists = new ArrayList<>();
        elementLists.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        elementLists.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        elementLists.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        elementLists.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));
        elementLists.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        elementLists.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        elementLists.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        elementLists.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));
        elementLists.add(new ElementList("Marco", "Carpintero", "#ff0000", "foto"));
        elementLists.add(new ElementList("Sandra", "ABCDE", "#ff0000", "foto"));
        elementLists.add(new ElementList("Pedro", "AAAAAAAAAAAAA", "#ffcd00", "foto"));
        elementLists.add(new ElementList("Sandra", "AFGHJ", "#ffcd00", "foto"));
        return elementLists;
    }

    */

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


        //ofertas=getItems();

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

        //filtrado=new ArrayList<>();
        //filtrado.addAll(ofertas);
    }



    /*public void filtro(@NonNull String search){
        int len=search.length();
        if(len==0){
            ofertas.clear();
            ofertas.addAll(filtrado);
        }else{
            List<ElementList>elements=filtrado.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
            ofertas.clear();
            ofertas.addAll(elements);
        }
    }

     */

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




    //public void goDetails(View view){
      //  Intent intent=new Intent(ListOffersActivity.this, DetallesOfertas.class);
        //startActivity(intent);

    //}
}