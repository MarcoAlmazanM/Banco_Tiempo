package com.example.banco_tiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateOffer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateOffer extends Fragment {

    Context applicationContext = MainActivity.getContextOfApplication();
    LinearLayout principal;
    RelativeLayout secondary;
    RatingBar bar;
    Button btnRateOffer;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username;
    String message;

    View root;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RateOffer() {
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
    public static RateOffer newInstance(String param1, String param2) {
        RateOffer fragment = new RateOffer();
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

        root = inflater.inflate(R.layout.fragment_rate_offer, container, false);

        bar = root.findViewById(R.id.rBCOferta);
        bar.setStepSize(1);
        btnRateOffer = root.findViewById(R.id.btnRateOffer);

        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","username");

        setUserAcceptedServices();

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

    public void setUserAcceptedServices(){
        UserNotificationsRequest userNotificationsRequest = new UserNotificationsRequest();
        userNotificationsRequest.setUsername(username);
        getUserAcceptedServices(userNotificationsRequest);
    }

    public void getUserAcceptedServices(UserNotificationsRequest userNotificationsRequest){
        Call<UserAcceptedServicesResponse> userAcceptedServicesResponseCall = ApiClient.getService().getUserAcceptedServices(userNotificationsRequest);
        userAcceptedServicesResponseCall.enqueue(new Callback<UserAcceptedServicesResponse>() {
            @Override
            public void onResponse(Call<UserAcceptedServicesResponse> call, Response<UserAcceptedServicesResponse> response) {
                if (response.isSuccessful()) {
                    UserAcceptedServicesResponse userAcceptedServicesResponse = response.body();
                    try{
                        if(userAcceptedServicesResponse.getSomething() == 1){
                            
                        }else{
                            principal = root.findViewById(R.id.principalRateOfferLayout);
                            principal.setVisibility(View.GONE);
                            secondary = root.findViewById(R.id.secondaryRateOfferLayout);
                            secondary.setVisibility(View.VISIBLE);
                        }

                    }catch(NullPointerException nullPointerException){
                        message = "Ocurrió un error al procesar las ofertas";
                        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserAcceptedServicesResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

}