package com.example.banco_tiempo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<ElementList>mData;
    private List<ElementList>oData;

    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter.ClickListener action;

    public ListAdapter(List<ElementList> itemList,Context context, ListAdapter.ClickListener action){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
        this.action=action;
        this.oData=new ArrayList<>();
        oData.addAll(mData);
    }
    public interface ClickListener{
        void clickListener(ElementList element);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.from(parent.getContext()).inflate(R.layout.element_list,parent, false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_op));
        holder.bindData(mData.get(position));
    }

    public void filtro(@NonNull String search){
        int len=search.length();
        if(len==0){
            mData.clear();
            mData.addAll(oData);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<ElementList> collect = mData.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(search))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(collect);
            }else{
                mData.clear();
                for(ElementList i: oData){
                    if(i.getNombre().toLowerCase().contains(search)){
                        mData.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setItems(List<ElementList> items){
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView badge, perfil;
        TextView nombre, trabajo;
        CardView cardView;
        TextView trabajo1;

        ViewHolder(View itemView){
            super(itemView);
            badge=itemView.findViewById(R.id.badge);
            perfil=itemView.findViewById(R.id.userPhoto);
            nombre=itemView.findViewById(R.id.userName);
            trabajo=itemView.findViewById(R.id.userJob);
            cardView=itemView.findViewById(R.id.jobCards);

        }

        void bindData(final ElementList item){
            badge.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombreUsuario());
            trabajo.setText(item.getNombre());
            //Set profile picture
            String url = item.getFoto();
            Transformation transformation = new RoundedCornersTransformation(100,5);
            Picasso.get().invalidate(url);
            Picasso.get().load(url).resize(120,120).centerCrop().transform(transformation).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(perfil);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    action.clickListener(item);
                }
            });
        }
    }


}



