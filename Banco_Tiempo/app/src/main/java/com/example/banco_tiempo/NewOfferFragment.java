package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewOfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOfferFragment extends Fragment {

    String message;
    List<UserOffersDetails> offersD;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String username;
    Integer statusDocuments;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewOfferFragment() {
        // Required empty public constructor
    }

    TextView btnCreaOffer;
    ArrayList<OfferVO> listOffer;
    View vista;

    RecyclerView recyclerOfertas;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewOfferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewOfferFragment newInstance(String param1, String param2) {
        NewOfferFragment fragment = new NewOfferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_new_offer, container, false);
        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","username");
        statusDocuments = preferences.getInt("documentosApproval",0);
        setUserOffersValues();

        listOffer = new ArrayList<>();

        btnCreaOffer = (TextView) vista.findViewById(R.id.btnNOffer);
        clickBtnCreateOffer(btnCreaOffer);


        return vista;
    }

    public void setUserOffersValues(){
        UserOffersRequest userOffersRequest = new UserOffersRequest();
        userOffersRequest.setUsername(username);
        getUserOffers(userOffersRequest);

    }

    public void getUserOffers(UserOffersRequest userOffersRequest){
        Call<UserOffersResponse> userOffersResponseCall = ApiClient.getService().getUserOffers(userOffersRequest);
        userOffersResponseCall.enqueue(new Callback<UserOffersResponse>() {
            @Override
            public void onResponse(Call<UserOffersResponse> call, Response<UserOffersResponse> response) {
                if (response.isSuccessful()) {
                    UserOffersResponse userOffersResponse = response.body();
                    offersD = new ArrayList<>(Arrays.asList(userOffersResponse.getOfertas()));
                    llenarLista();

                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserOffersResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void llenarLista() {

        for (int i = 0; i < offersD.size(); i++){
            String nombre = offersD.get(i).getNombre();
            String descripcion = offersD.get(i).getDescripcion();
            String categoria = offersD.get(i).getCategoria();
            String imagen = offersD.get(i).getImagen();

            OfferVO oferta = new OfferVO(nombre, descripcion, imagen, categoria);
            listOffer.add(oferta);
        }

        recyclerOfertas = (RecyclerView) vista.findViewById(R.id.rVNewOffer);

        recyclerOfertas.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterNewOffer myadapter = new AdapterNewOffer(listOffer);

        recyclerOfertas.setAdapter(myadapter);
    }

    public void clickBtnCreateOffer(TextView btnCreateOffer){
        btnCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusDocuments == 1){
                    Intent creaOferta = new Intent(getActivity().getApplicationContext(), CreateOffer.class);
                    getActivity().startActivity(creaOferta);
                }else{
                    message = "No puedes crear una oferta si tus documentos no han sido aprobados.";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}