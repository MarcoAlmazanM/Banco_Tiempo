package com.example.banco_tiempo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNewOffer
        extends RecyclerView.Adapter<AdapterNewOffer.MyViewHolder>
        implements View.OnClickListener{

    Context context;
    private View.OnClickListener listener;
    ArrayList<OfferVO> listaOffer;

    public AdapterNewOffer(ArrayList<OfferVO> listaOffer){
        this.listaOffer = listaOffer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_offer_row, null, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(listaOffer.get(position).getTrabajo());
        holder.myText2.setText(listaOffer.get(position).getInfo());
        holder.myImage.setImageResource(listaOffer.get(position).getImagen());
    }

    @Override
    public int getItemCount() {return listaOffer.size();}

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1, myText2;
        ImageView myImage;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            myText1 = itemView.findViewById(R.id.tVJob);
            myText2 = itemView.findViewById(R.id.tVDescription);
            myImage = itemView.findViewById(R.id.iVUserPhoto);
        }
    }
}
