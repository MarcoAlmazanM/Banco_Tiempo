package com.example.banco_tiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDetails extends AppCompatActivity {

    Toolbar toolbar;

    TextView nombre;
    TextView servicio;
    TextView descripcion;
    TextView id;
    TextView trabajo2;

    ElementList oferta;

    ImageView perfil;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String username, message, token;
    Integer statusDocumentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        toolbar = findViewById(R.id.toolbar);
        setTitle("Detalles de la Oferta");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","NULL");
        statusDocumentos = preferences.getInt("documentosApproval",0);

        oferta=(ElementList) getIntent().getSerializableExtra("ElementList");
        nombre=findViewById(R.id.userName2);
        servicio=findViewById(R.id.trabajo);
        trabajo2=findViewById(R.id.userTrabajo);
        descripcion=findViewById(R.id.jobDescription);
        perfil=findViewById(R.id.userJobImage);
        id = findViewById(R.id.serviceGiven);

        nombre.setText(oferta.getNombreUsuario());
        servicio.setText(oferta.getNombre());
        descripcion.setText(oferta.getDescripcion());
        trabajo2.setText(oferta.getNombre());
        Integer value = oferta.getIdServicio();
        id.setText((("#"+value.toString())));

        String url = oferta.getImage();
        Transformation transformation = new RoundedCornersTransformation(50,5);
        Picasso.get().invalidate(url);
        Picasso.get().load(url).transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(perfil);

        FirebaseMessaging.getInstance().getToken()

                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w("FCM Token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        //String token = task.getResult();
                        token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d("TOKEN", token);
                        //Toast.makeText(NotificationAdapter.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void RequestOffer(View view){
        if(statusDocumentos == 1){
            UserRequestOffer userRequestOffer = new UserRequestOffer();
            userRequestOffer.setIdServicio(oferta.getIdServicio());
            userRequestOffer.setIdEmisor(oferta.getIdUsuario());
            userRequestOffer.setIdReceptor(username);
            userRequestOffer.setType("REQUEST");
            getUserRequestOffer(userRequestOffer);
        }else{
            message = "No puedes solicitar un servicio si tus documentos no han sido aprobados.";
            Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void getUserRequestOffer(UserRequestOffer userRequestOffer){
        Call<UserRequestOfferResponse> userRequestOfferResponseCall = ApiClient.getService().getUserRequestOffer(userRequestOffer);
        userRequestOfferResponseCall.enqueue(new Callback<UserRequestOfferResponse>() {
            @Override
            public void onResponse(Call<UserRequestOfferResponse> call, Response<UserRequestOfferResponse> response) {
                if (response.isSuccessful()) {
                    UserRequestOfferResponse userRequestOfferResponse = response.body();
                    try{
                        if(userRequestOfferResponse.getTransactionApproval() == 1){
                            message = "La solicitud del servicio esta esperando a ser aceptada, favor de dirigirse al apartado de notificaciones.";
                            Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
                            FcmNotificationSenderAct notificationSenderAct = new FcmNotificationSenderAct(token,"Servicio solicitado correctamente","Revisa tus notificaciones",getApplicationContext(), OfferDetails.this);
                            notificationSenderAct.SendNotifications();
                        }else{
                            message = userRequestOfferResponse.getError();
                            Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
                        }
                    }catch (NullPointerException nullPointerException){
                        message = "Ocurrió un error al procesar la solicitud del servicio";
                        Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
                    }

                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserRequestOfferResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(OfferDetails.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
