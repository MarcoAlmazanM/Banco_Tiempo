package com.example.banco_tiempo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rate_offer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rate_offer extends Fragment {

    RatingBar bar;
    Button btnRateOffer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public rate_offer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rate_offer.
     */
    // TODO: Rename and change types and number of parameters
    public static rate_offer newInstance(String param1, String param2) {
        rate_offer fragment = new rate_offer();
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

        View root = inflater.inflate(R.layout.fragment_rate_offer, container, false);

        bar = root.findViewById(R.id.rBCOferta);
        bar.setStepSize(1);
        btnRateOffer = root.findViewById(R.id.btnRateOffer);

        clickBtnRateOffer(btnRateOffer);
        // Inflate the layout for this fragment
        return root;
    }

    public void clickBtnRateOffer(Button btnRateOffer){
        btnRateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer rating = (int)bar.getRating();
                Log.e("lol", String.valueOf(rating));
            }
        });
    }

}