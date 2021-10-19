package com.example.banco_tiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ramijemli.percentagechartview.PercentageChartView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateOffer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateOffer extends Fragment {

    UserAcceptedServicesResponse userAcceptedServicesResponse;
    Context applicationContext = MainActivity.getContextOfApplication();
    ScrollView principal;
    RelativeLayout secondary;
    RatingBar bar;
    PercentageChartView chartView;
    Button btnRateOffer;
    Integer asistance;
    private RadioButton rBYes, rBNo;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username;
    String message;
    String token;

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
        FirebaseMessaging.getInstance().getToken()

                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w("FCM Token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_rate_offer, container, false);

        bar = root.findViewById(R.id.rBCOferta);
        bar.setStepSize(1);
        btnRateOffer = root.findViewById(R.id.btnRateOffer);
        rBNo = root.findViewById(R.id.rBtnNo);
        rBYes = root.findViewById(R.id.rBtnSi);

        rBNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setIsIndicator(true);
                bar.setRating(0);
            }

        });

        rBYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setIsIndicator(false);
            }

        });


        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","username");

        chartView = root.findViewById(R.id.pVprogressPie2);

        uploadEffect();



        clickBtnRateOffer(btnRateOffer);
        // Inflate the layout for this fragment
        return root;
    }

    public void clickBtnRateOffer(Button btnRateOffer ){
        btnRateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer rating = (int)bar.getRating();
                verifyData();
                setUserEndedService(rating);
            }
        });
    }

    private void verifyData(){
        if(rBYes.isChecked()){
            asistance = 1;
        }else{
            asistance = 0;
        }
    }

    private void setUserEndedService(Integer rating){
        EndedServiceRequest  endedServiceRequest = new EndedServiceRequest();
        endedServiceRequest.setIdServicio(userAcceptedServicesResponse.getIdServicio());
        endedServiceRequest.setIdEmisor(userAcceptedServicesResponse.getIdEmisor());
        endedServiceRequest.setIdReceptor(username);
        endedServiceRequest.setIdNotificacion(userAcceptedServicesResponse.getIdNot());
        endedServiceRequest.setAsistencia(asistance);
        endedServiceRequest.setStars("V" + rating);
        sendEndedServiceRequest(endedServiceRequest);
    }

    private void sendEndedServiceRequest(EndedServiceRequest endedServiceRequest){
        Call<EndedServiceResponse> endedServiceResponseCall = ApiClient.getService().sendEndedServiceRequest(endedServiceRequest);
        endedServiceResponseCall.enqueue(new Callback<EndedServiceResponse>() {
            @Override
            public void onResponse(Call<EndedServiceResponse> call, Response<EndedServiceResponse> response) {
                if (response.isSuccessful()) {

                    EndedServiceResponse endedServiceResponse = response.body();
                    try{
                        if(endedServiceResponse.getTransactionApproval() == 1){
                            FcmNotificationSenderRate notificationSenderRate = new FcmNotificationSenderRate(token,"Ofertas","La oferta se ha calificado correctamente",applicationContext, RateOffer.this);
                            notificationSenderRate.SendNotificationRate();
                            message = "La oferta ha sido calificada.";
                            Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.content, new RateOffer());
                            ft.addToBackStack(null);
                            ft.commit();
                        }else{
                            message = "Ocurrió un error al procesar la calificación de la oferta, favor de volver a intentarlo.";
                            Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }

                    }catch(NullPointerException nullPointerException){
                        message = "Ocurrió un error al procesar la calificación de la oferta";
                        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EndedServiceResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadEffect(){
        chartView.setProgress(100, true);
        setUserAcceptedServices();
    }

    public void setRateOffer(){

        ImageView userJobImage = root.findViewById(R.id.userJobImage);
        TextView trabajo = root.findViewById(R.id.trabajo);
        TextView serviceGiven = root.findViewById(R.id.serviceGiven);
        TextView userName = root.findViewById(R.id.userName2);
        TextView userTrabajo = root.findViewById(R.id.userTrabajo);
        TextView descripcion = root.findViewById(R.id.jobDescription);

        Transformation transformation = new RoundedCornersTransformation(50,5);
        Picasso.get().invalidate(userAcceptedServicesResponse.getImage());
        Picasso.get().load(userAcceptedServicesResponse.getImage()).transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(userJobImage);
        trabajo.setText(userAcceptedServicesResponse.getNombre());
        serviceGiven.setText(String.valueOf(userAcceptedServicesResponse.getIdServicio()));
        userName.setText(userAcceptedServicesResponse.getIdEmisor());
        userTrabajo.setText(userAcceptedServicesResponse.getCategoria());
        descripcion.setText(userAcceptedServicesResponse.getDescripcion());

        chartView.setVisibility(View.GONE);
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

                    userAcceptedServicesResponse = response.body();
                    try{
                        if(userAcceptedServicesResponse.getSomething() == 1){
                            principal = root.findViewById(R.id.principalRateOfferLayout);
                            principal.setVisibility(View.VISIBLE);
                            setRateOffer();
                        }else{
                            secondary = root.findViewById(R.id.secondaryRateOfferLayout);
                            secondary.setVisibility(View.VISIBLE);
                            chartView.setVisibility(View.GONE);
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
