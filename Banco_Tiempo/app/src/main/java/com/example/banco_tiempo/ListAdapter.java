package com.example.banco_tiempo;

import java.util.List;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<ElementList>mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter.ClickListener action;

    public interface ClickListener{
        void clickListener(ElementList element);
    }

    public ListAdapter(List<ElementList> itemList, Context context, ListAdapter.ClickListener action){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
        this.action=action;
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

    public void setItems(List<ElementList> items){
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView badge, perfil;
        TextView nombre, trabajo;
        CardView cardView;

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
            nombre.setText(item.getNombre());
            trabajo.setText(item.getTrabajo());
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    action.clickListener(item);
                }
            });
        }
    }
}
