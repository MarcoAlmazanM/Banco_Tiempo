package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicio extends Fragment {

    CardView cardViewAdmi;
    CardView cardViewMate;
    CardView cardViewActu;
    CardView cardViewArte;
    CardView cardViewLimp;
    CardView cardViewDepo;
    CardView cardViewVent;
    CardView cardViewMant;
    CardView cardViewTran;



    CardView cardViewComida;
    CardView cardViewMateriales, cardViewAgri, cardViewPer, cardViewConst, cardViewEdu, cardViewInge, cardViewCompu;
    
    View root;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicio newInstance(String param1, String param2) {
        FragmentInicio fragment = new FragmentInicio();
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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_inicio, container, false);
        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        cardViewAdmi = root.findViewById(R.id.cVAdmin);
        cardViewMate = root.findViewById(R.id.cVMate);
        cardViewActu = root.findViewById(R.id.cVActu);
        cardViewArte = root.findViewById(R.id.cVArte);
        cardViewLimp = root.findViewById(R.id.cVLimp);
        cardViewDepo = root.findViewById(R.id.cVDepo);
        cardViewVent = root.findViewById(R.id.cVVent);
        cardViewMant = root.findViewById(R.id.cVMant);
        cardViewTran = root.findViewById(R.id.cVTran);


        onClickCardView1(cardViewAdmi);
        onClickCardView3(cardViewMate);
        onClickCardView5(cardViewActu);
        onClickCardView7(cardViewArte);
        onClickCardView9(cardViewLimp);
        onClickCardView11(cardViewDepo);
        onClickCardView13(cardViewVent);
        onClickCardView15(cardViewMant);
        onClickCardView17(cardViewTran);

        cardViewComida = root.findViewById(R.id.cVComida);
        cardViewAgri = root.findViewById(R.id.cVAgri);
        cardViewPer = root.findViewById(R.id.cVPer);
        cardViewConst = root.findViewById(R.id.cVCons);
        cardViewEdu = root.findViewById(R.id.cVEdu);
        cardViewInge = root.findViewById(R.id.cVInge);
        cardViewCompu = root.findViewById(R.id.cVCompu);
        cardViewMateriales = root.findViewById(R.id.cVPMate);
        onClickCardView8(cardViewComida);
        onClickCardView2(cardViewCompu);
        onClickCardView4(cardViewInge);
        onClickCardView6(cardViewEdu);
        onClickCardView10(cardViewConst);
        onClickCardView12(cardViewPer);
        onClickCardView14(cardViewAgri);
        onClickCardView16(cardViewMateriales);
        return root;
    }

    public void onClickCardView1(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Administración");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView3(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Matemáticas");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView5(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Actuaría");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView7(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Artes, Diseño, y Comunicación");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView8(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Comida");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView9(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Limpieza");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView11(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Deportes");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView13(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Ventas");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView15(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Instalación, Mantenimiento, y Reparación");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView17(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Transporte de Personas y Materiales");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView2(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Computación");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView4(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Ingeniería");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView6(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Educación");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView10(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Construcción");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView12(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Cuidado Personal");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView14(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Agricultura, Pesca, y Silvicultura");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }

    public void onClickCardView16(CardView cardViewAdmin){
        cardViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("categoria", "Producción de Materiales");
                editor.apply();
                Intent listOffers = new Intent(getActivity().getApplicationContext(), ListOffersActivity.class);
                getActivity().startActivity(listOffers);
            }
        });
    }
}
