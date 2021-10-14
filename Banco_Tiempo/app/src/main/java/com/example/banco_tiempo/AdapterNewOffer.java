package com.example.banco_tiempo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class AdapterNewOffer
        extends RecyclerView.Adapter<AdapterNewOffer.MyViewHolder>
        implements View.OnClickListener{

    Context context;
    private View.OnClickListener listener;
    ArrayList<OfferVO> listaOffer;
    ArrayList<Drawable> gradients;
    Integer counter = 0;

    public AdapterNewOffer(ArrayList<OfferVO> listaOffer){
        this.listaOffer = listaOffer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_offer_row, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(listaOffer.get(position).getTrabajo());
        holder.myText2.setText(listaOffer.get(position).getInfo());
        Transformation transformation = new RoundedCornersTransformation(100,5);
        Picasso.get().invalidate(listaOffer.get(position).getImagen());
        Picasso.get().load(listaOffer.get(position).getImagen()).resize(120,120).centerCrop().transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.myImage);

        if (counter > 9) {
            counter = 0;
        }

        holder.relativeLayout.setBackground(gradients.get(counter));

        counter++;
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
        Button btnAccept;
        Drawable drawable;
        RelativeLayout relativeLayout;
        private AdapterNewOffer adapter;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            myText1 = itemView.findViewById(R.id.tVJob);
            myText2 = itemView.findViewById(R.id.tVDescription);
            myImage = itemView.findViewById(R.id.iVUserPhoto);

            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_admin, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_compu, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_admin, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_admin, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_admin, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_admin, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_inge, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_edu, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_comida, null);
            gradients.add(drawable);

            relativeLayout = itemView.findViewById(R.id.rLlayout);
            itemView.findViewById(R.id.btnDelOffer).setOnClickListener(view-> {
                adapter.listaOffer.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });
        }

        public MyViewHolder linkAdapter(AdapterNewOffer adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
