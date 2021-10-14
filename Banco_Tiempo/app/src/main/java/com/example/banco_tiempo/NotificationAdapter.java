package com.example.banco_tiempo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/*import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

 */

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolderNotifications> implements View.OnClickListener {

    Context context;
    private View.OnClickListener listener;
    ArrayList<NotificationList> notificationList;
    String message;

    public NotificationAdapter(ArrayList<NotificationList> notificationList, Context context){
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderNotifications(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotifications holder, int position) {

        holder.trabajo.setText( notificationList.get(position).getNombre());


        holder.tipo.setText(notificationList.get(position).getTipo());
        holder.desc.setText(notificationList.get(position).getDescripcion());
        //holder.cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
        holder.bindCardColor(notificationList.get(position));



        /*Transformation transformation = new RoundedCornersTransformation(100,5);
        Picasso.get().invalidate(notificationList.get(position).getImagen());
        Picasso.get().load(notificationList.get(position).getImagen()).resize(120,120).centerCrop().transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.myImage);

         */

    }

    @Override
    public int getItemCount() {return notificationList.size();}

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }



    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }


    public class ViewHolderNotifications extends RecyclerView.ViewHolder{

        TextView trabajo, tipo, desc;
        CardView cardType;
        Button btnA, btnR, btnC;//btnT

        //ImageView myImage;
        private NotificationAdapter adapter;

        public ViewHolderNotifications (@NonNull View itemView){
            super(itemView);
            trabajo = itemView.findViewById(R.id.userJob);
            tipo = itemView.findViewById(R.id.status);
            desc = itemView.findViewById(R.id.descripcion);

            btnA = itemView.findViewById(R.id.bAcept);
            btnR = itemView.findViewById(R.id.bReject);
            //btnT = itemView.findViewById(R.id.bTerminate);
            btnC = itemView.findViewById(R.id.bContact);

            cardType=itemView.findViewById(R.id.notificationCards);


            //Log.e("card", cardType.toString());

            //cardType.setCardBackgroundColor(Color.RED);

            //myImage = itemView.findViewById(R.id.iVUserPhoto);
            itemView.findViewById(R.id.bAcept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserOfferAccept(getAdapterPosition());
                    //btnA.setText("JalaA");
                    //cardType.setCardBackgroundColor(Color.RED);
                    //btnA.setTextColor(Color.RED);

                    //cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                }
            });

            itemView.findViewById(R.id.bReject).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnR.setText("JalaR");
                }
            });

            /*itemView.findViewById(R.id.bTerminate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnR.setText("JalaR");
                }
            });*/

            /*itemView.findViewById(R.id.bContact).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnR.setText("JalaR");
                }
            });*/

            /*itemView.findViewById(R.id.bAcept).setOnClickListener(view-> {
                //adapter.notificationList.remove(getAdapterPosition());
                //adapter.notifyItemRemoved(getAdapterPosition());
                //Log.e("aceptado",);
                btnA.setText("JalaA");
                //cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                //cardType.setCardBackgroundColor(Color.GREEN);

            });

             */

            /*itemView.findViewById(R.id.bReject).setOnClickListener(view-> {
                //Log.e("rechazado",);
                btnR.setText("JalaB");
            });
             */

        }
        public void setUserOfferAccept(int position){
            UserRequestOffer userRequestOffer = new UserRequestOffer();
            userRequestOffer.setIdNot(notificationList.get(position).getIdNot());
            userRequestOffer.setIdReceptor(notificationList.get(position).getIdReceptor());
            userRequestOffer.setIdEmisor(notificationList.get(position).getIdEmisor());
            userRequestOffer.setType("ACCEPTED");
            userRequestOffer.setIdServicio(notificationList.get(position).getIdServicio());
            getUserRequestOffer(userRequestOffer);
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
                                Log.e("GOD IS HERE", "Entramos perros");
                            }else{
                                Log.e("GOD IS NOT HERE", "No Entramos perros");
                            }
                        }catch (NullPointerException nullPointerException){
                            Log.e("GOD IS NOT HERE", "ERROOOOR");
                        }

                    } else {
                        message = "Ocurrió un error, favor de intentar más tarde";
                        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserRequestOfferResponse> call, Throwable t) {
                    message = t.getLocalizedMessage();
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        private void dinChange(Button a, Button r, Button c,TextView m, int op){
            switch (op){
                case 1:
                    a.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);
                    //t.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    m.setText("Pendiente");
                    break;
                case 2:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    m.setText("Aceptado");
                    break;
                case 3:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    m.setText("Rechazado");
                    break;


            }
        }
        void bindCardColor(final NotificationList item){
            //tipo.setText(item.getCate());

            if(item.getTipo().contains("ACCEPTED")){
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
                dinChange(btnA, btnR, btnC, tipo,2);
            }
            else if(item.getTipo().contains("REJECTED")){
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.cardColorRed)));
                dinChange(btnA, btnR, btnC, tipo,3);
            }
            else{
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeColor)));
                dinChange(btnA, btnR, btnC, tipo,1);
            }
            /*if(item.getCate().equals("q")){
                cardType.setCardBackgroundColor(Color.GREEN);
                //cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
            }
            if(item.getCate().equals("xd")){
                //cardType.setCardBackgroundColor(Color.RED);
            }

             */

        }

        public ViewHolderNotifications linkAdapter(NotificationAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
