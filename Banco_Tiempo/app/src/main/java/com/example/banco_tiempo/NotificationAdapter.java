package com.example.banco_tiempo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

 */

import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>
        implements View.OnClickListener{

    Context context;
    private View.OnClickListener listener;
    ArrayList<NotificationList> notificationList;

    public NotificationAdapter(ArrayList<NotificationList> notificationList){
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trabajo.setText(notificationList.get(position).getTrabajo());
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
    private void test(){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView trabajo;
        Button btnA, btnR;
        //ImageView myImage;
        private NotificationAdapter adapter;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            trabajo = itemView.findViewById(R.id.userJob);

            btnA = itemView.findViewById(R.id.bAcept);
            btnR = itemView.findViewById(R.id.bReject);
            //myImage = itemView.findViewById(R.id.iVUserPhoto);
            itemView.findViewById(R.id.bAcept).setOnClickListener(view-> {
                //adapter.notificationList.remove(getAdapterPosition());
                //adapter.notifyItemRemoved(getAdapterPosition());
                //Log.e("aceptado",);
                btnA.setText("JalaA");
            });

            itemView.findViewById(R.id.bReject).setOnClickListener(view-> {
                //Log.e("rechazado",);
                btnR.setText("JalaB");
            });
        }

        public MyViewHolder linkAdapter(NotificationAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
