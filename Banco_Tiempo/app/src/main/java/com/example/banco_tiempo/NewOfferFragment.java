package com.example.banco_tiempo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewOfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOfferFragment extends Fragment {

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

    RecyclerView recyclerOfertas;
    String s1[], s2[];
    int images[] = {R.drawable.baseline_account_circle_black_48, R.drawable.baseline_account_circle_black_48, R.drawable.baseline_account_circle_black_48, R.drawable.baseline_account_circle_black_48, R.drawable.baseline_account_circle_black_48, R.drawable.baseline_account_circle_black_48};

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
        View vista = inflater.inflate(R.layout.fragment_new_offer, container, false);

        recyclerOfertas = (RecyclerView) vista.findViewById(R.id.rVNewOffer);

        s1 = getResources().getStringArray(R.array.empleos);
        s2 = getResources().getStringArray(R.array.descripciones);

        AdapterNewOffer myadapter = new AdapterNewOffer(getContext(), s1, s2, images);

        recyclerOfertas.setAdapter(myadapter);
        recyclerOfertas.setLayoutManager(new LinearLayoutManager(getContext()));

        return vista;
    }
}