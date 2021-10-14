package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    String message, username;
    Button bAcept, bReject;
    List<UserNotifications> notifications;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<NotificationList> listaNotificacion;
    View vista;

    RecyclerView notificacion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        vista = inflater.inflate(R.layout.fragment_notification, container, false);

        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","username");
        setUserNotificationsValues();

        listaNotificacion = new ArrayList<>();

        /*bAcept = (Button)vista.findViewById(R.id.bAcept);
        bReject = (Button)vista.findViewById(R.id.bReject);

         */
        //clickBtnCreateOffer(btnCreaOffer);


        return vista;
    }

    private void llenarLista() {

        for (int i = 0; i < notifications.size(); i++){
            String nombre = notifications.get(i).getIdEmisor();
            String descripcion = notifications.get(i).getIdReceptor();
            String categoria = notifications.get(i).getTipo();

            NotificationList oferta = new NotificationList(categoria, nombre, descripcion);
            listaNotificacion.add(oferta);
        }
        
        /*NotificationList oferta = new NotificationList("Test", "test", "test");
        NotificationList oferta2 = new NotificationList("Test2", "test2","test2");
        listaNotificacion.add(oferta);
        listaNotificacion.add(oferta2);
         */

        notificacion = (RecyclerView) vista.findViewById(R.id.listaNotificacion);

        notificacion.setLayoutManager(new LinearLayoutManager(getContext()));

        NotificationAdapter myadapter = new NotificationAdapter(listaNotificacion);

        notificacion.setAdapter(myadapter);
    }

    public void setUserNotificationsValues(){
        UserNotificationsRequest userNotificationsRequest = new UserNotificationsRequest();
        userNotificationsRequest.setUsername(username);
        getUserNotifications(userNotificationsRequest);
    }

    public void getUserNotifications(UserNotificationsRequest userNotificationsRequest){
        Call<UserNotificationsResponse> userNotificationsResponseCall = ApiClient.getService().getUserNotifications(userNotificationsRequest);
        userNotificationsResponseCall.enqueue(new Callback<UserNotificationsResponse>() {
            @Override
            public void onResponse(Call<UserNotificationsResponse> call, Response<UserNotificationsResponse> response) {
                if (response.isSuccessful()) {
                    UserNotificationsResponse userNotificationsResponse = response.body();
                    notifications = new ArrayList<>(Arrays.asList(userNotificationsResponse.getNotificaciones()));
                    llenarLista();
                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserNotificationsResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }



    /*public void clickBtnCreateOffer(Button btnCreateOffer){
        btnCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent creaOferta = new Intent(getActivity().getApplicationContext(), CreateOffer.class);
                getActivity().startActivity(creaOferta);

            }
        });
    }*/
}