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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterNewOffer
        extends RecyclerView.Adapter<AdapterNewOffer.MyViewHolder>
        implements View.OnClickListener{

    Context context;
    private View.OnClickListener listener;
    ArrayList<OfferVO> listaOffer;
    ArrayList<Drawable> gradients = new ArrayList<>();
    Integer counter = 0;
    String message;

    public AdapterNewOffer(ArrayList<OfferVO> listaOffer, Context context){

        this.listaOffer = listaOffer;
        this.context = context;
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

        if (counter > 8) {
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
        String token;
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
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_mate, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_actua, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_artes, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_inge, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_edu, null);
            gradients.add(drawable);
            drawable = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.home_gradient_comida, null);
            gradients.add(drawable);

            relativeLayout = itemView.findViewById(R.id.rLlayout);
            itemView.findViewById(R.id.btnDelOffer).setOnClickListener(view-> {
                setDeleteUserOffer(getAdapterPosition());
            });

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

        public void setDeleteUserOffer(int position){
            DeleteUserOfferRequest deleteUserOfferRequest = new DeleteUserOfferRequest();
            deleteUserOfferRequest.setIdServicio(listaOffer.get(position).getIdServicio());
            getDelUserOffer(deleteUserOfferRequest);
        }

        public void getDelUserOffer(DeleteUserOfferRequest deleteUserOfferRequest){
            Call<DeleteUserOfferResponse> deleteUserOfferResponseCall = ApiClient.getService().getDelUserOffer(deleteUserOfferRequest);
            deleteUserOfferResponseCall.enqueue(new Callback<DeleteUserOfferResponse>() {
                @Override
                public void onResponse(Call<DeleteUserOfferResponse> call, Response<DeleteUserOfferResponse> response) {
                    if (response.isSuccessful()) {
                        DeleteUserOfferResponse deleteUserOfferResponse = response.body();
                        try{
                            if(deleteUserOfferResponse.getTransactionApproval() == 1){
                                message = "La oferta se ha eliminado correctamente.";
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                adapter.listaOffer.remove(getAdapterPosition());
                                adapter.notifyItemRemoved(getAdapterPosition());
                                //deleteOffer = true;
                                FcmNotificationSenderOffer notificationSenderOffer = new FcmNotificationSenderOffer(token,"Estatus de oferta","Oferta eliminada correctamente",context.getApplicationContext(), AdapterNewOffer.this);
                                notificationSenderOffer.SendNotifications();
                            }else{
                                message = "No se puede eliminar la oferta porque esta actualmente activa.";
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }

                        }catch (NullPointerException nullPointerException){
                            message = "Ocurrió un error al borrar la oferta, favor de intentarlo de nuevo.";
                            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        message = "Ocurrió un error, favor de intentar más tarde";
                        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteUserOfferResponse> call, Throwable t) {
                    message = t.getLocalizedMessage();
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });
        }

        public MyViewHolder linkAdapter(AdapterNewOffer adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
