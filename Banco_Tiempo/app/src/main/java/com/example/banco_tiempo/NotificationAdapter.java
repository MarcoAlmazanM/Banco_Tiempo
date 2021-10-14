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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolderNotifications> {
            //implements View.OnClickListener

    Context context;
    private View.OnClickListener listener;
    ArrayList<NotificationList> notificationList;

    public NotificationAdapter(ArrayList<NotificationList> notificationList, Context context){
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolderNotifications(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotifications holder, int position) {
        holder.trabajo.setText(notificationList.get(position).getTrabajo());
        holder.tipo.setText(notificationList.get(position).getInfo());
        holder.desc.setText(notificationList.get(position).getCate());
        //holder.cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
        holder.bindCardColor(notificationList.get(position));



        /*Transformation transformation = new RoundedCornersTransformation(100,5);
        Picasso.get().invalidate(notificationList.get(position).getImagen());
        Picasso.get().load(notificationList.get(position).getImagen()).resize(120,120).centerCrop().transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.myImage);

         */

    }

    @Override
    public int getItemCount() {return notificationList.size();}

    /*public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

     */

    /*@Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

     */
    private void test(){

    }

    public class ViewHolderNotifications extends RecyclerView.ViewHolder{

        TextView trabajo, tipo, desc;
        CardView cardType;
        Button btnA, btnR, btnT, btnC;

        //ImageView myImage;
        private NotificationAdapter adapter;

        public ViewHolderNotifications (@NonNull View itemView){
            super(itemView);
            trabajo = itemView.findViewById(R.id.userJob);
            tipo = itemView.findViewById(R.id.status);
            desc = itemView.findViewById(R.id.descripcion);

            btnA = itemView.findViewById(R.id.bAcept);
            btnR = itemView.findViewById(R.id.bReject);
            btnT = itemView.findViewById(R.id.bTerminate);
            btnC = itemView.findViewById(R.id.bContact);

            cardType=itemView.findViewById(R.id.notificationCards);


            //Log.e("card", cardType.toString());

            //cardType.setCardBackgroundColor(Color.RED);

            //myImage = itemView.findViewById(R.id.iVUserPhoto);
            itemView.findViewById(R.id.bAcept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnA.setText("JalaA");
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
        private void dinChange(Button a, Button r, Button t, Button c,TextView m, int op){
            switch (op){
                case 1:
                    a.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);
                    t.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    m.setText("Pendiente");
                    break;
                case 2:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    t.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    m.setText("Aceptado");
                    break;
                case 3:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    t.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    m.setText("Rechazado");
                    break;


            }
        }
        void bindCardColor(final NotificationList item){
            //tipo.setText(item.getCate());

            if(item.getInfo().toString().contains("q")){
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
                dinChange(btnA, btnR, btnT, btnC, tipo,2);
            }
            else if(item.getInfo().toString().contains("xd")){
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.cardColorRed)));
                dinChange(btnA, btnR, btnT, btnC, tipo,3);
            }
            else{
                cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeColor)));
                dinChange(btnA, btnR, btnT, btnC, tipo,1);
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
