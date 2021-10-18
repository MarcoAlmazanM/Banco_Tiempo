package com.example.banco_tiempo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import android.widget.ImageView;
/*import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

 */

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
        holder.cardType.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_op));
        holder.trabajo.setText( notificationList.get(position).getNombre());
        holder.tipo.setText(notificationList.get(position).getTipo());
        holder.desc.setText(notificationList.get(position).getDescripcion());
        holder.nombre.setText(notificationList.get(position).getNombreUsuario());
        holder.correo.setText(notificationList.get(position).getCorreo());
        holder.ap.setText(notificationList.get(position).getNombreApellidoP());
        holder.am.setText(notificationList.get(position).getNombreApellidoM());
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

        TextView trabajo, tipo, desc, nombre, nombrePlaceHolder, ap, am, correo, correoPlaceholder, mensajeUsuario;
        CardView cardType;
        Button btnA, btnR;//btnT
        ImageView eliminar;
        LinearLayout linearLayout;

        //ImageView myImage;
        private NotificationAdapter adapter;

        public ViewHolderNotifications (@NonNull View itemView){
            super(itemView);
            trabajo = itemView.findViewById(R.id.userJob);
            tipo = itemView.findViewById(R.id.status);
            desc = itemView.findViewById(R.id.descripcion);
            nombre = itemView.findViewById(R.id.nombreCliente);
            nombrePlaceHolder = itemView.findViewById(R.id.nombreClienteph);
            ap = itemView.findViewById(R.id.ap);
            am = itemView.findViewById(R.id.am);
            correo = itemView.findViewById(R.id.correo);
            correoPlaceholder = itemView.findViewById(R.id.tVCorreo);
            mensajeUsuario = itemView.findViewById(R.id.mensajeEstado);

            btnA = itemView.findViewById(R.id.bAcept);
            btnR = itemView.findViewById(R.id.bReject);

            eliminar = itemView.findViewById(R.id.iVEraseRejected);
            cardType=itemView.findViewById(R.id.notificationCards);
            linearLayout = itemView.findViewById(R.id.cardLinearLayout);



            itemView.findViewById(R.id.bAcept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserOfferAccept(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.bReject).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserOfferRejected(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.iVEraseRejected).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUserOfferEnded(getAdapterPosition());
                }
            });


        }

        public void setUserOfferEnded(int position){
            UserRequestOffer userRequestOffer = new UserRequestOffer();
            userRequestOffer.setIdNot(notificationList.get(position).getIdNot());
            userRequestOffer.setType("ENDED");
            getUserRequestOffer(userRequestOffer);
        }

        public void setUserOfferRejected(int position){
            UserRequestOffer userRequestOffer = new UserRequestOffer();
            userRequestOffer.setIdNot(notificationList.get(position).getIdNot());
            userRequestOffer.setType("REJECTED");
            getUserRequestOffer(userRequestOffer);
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
                                message = "El servicio se ha aceptado correctamente, en breve un usuario se contactará con usted.";
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }else if(userRequestOfferResponse.getTransactionApproval() == 2){
                                message = "El servicio se ha rechazado correctamente.";
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }else if(userRequestOfferResponse.getTransactionApproval() == 3){
                                message = "Notificación eliminada correctamente.";
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                            else{
                                message = userRequestOfferResponse.getError();
                                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }

                        }catch (NullPointerException nullPointerException){
                            message = "Ocurrió un error al procesar la acción, favor de intentarlo de nuevo.";
                            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

        private void dinChange(Button a, Button r,TextView m,TextView n, TextView correo, TextView ap, TextView am, TextView mensajeUsuario, TextView correoPlaceholder,TextView nombrePLaceholder,ImageView eliminar, int op){
            switch (op){

                //REQUEST
                case 1:
                    a.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);
                    //t.setVisibility(View.GONE);
                    n.setVisibility(View.VISIBLE);
                    correo.setVisibility(View.INVISIBLE);
                    ap.setVisibility(View.VISIBLE);
                    am.setVisibility(View.VISIBLE);
                    correoPlaceholder.setVisibility(View.INVISIBLE);
                    nombrePLaceholder.setVisibility(View.VISIBLE);
                    mensajeUsuario.setVisibility(View.GONE);
                    eliminar.setVisibility(View.GONE);

                    m.setText("Pendiente");
                    break;

                    //ACCEPTED
                case 2:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.VISIBLE);
                    n.setVisibility(View.VISIBLE);
                    correo.setVisibility(View.VISIBLE);
                    ap.setVisibility(View.VISIBLE);
                    am.setVisibility(View.VISIBLE);
                    correoPlaceholder.setVisibility(View.VISIBLE);
                    nombrePLaceholder.setVisibility(View.VISIBLE);
                    mensajeUsuario.setVisibility(View.GONE);
                    eliminar.setVisibility(View.GONE);
                    m.setText("Aceptado");
                    break;

                    //REJECTED
                case 3:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.GONE);
                    n.setVisibility(View.INVISIBLE);
                    correo.setVisibility(View.INVISIBLE);
                    ap.setVisibility(View.INVISIBLE);
                    am.setVisibility(View.INVISIBLE);
                    correoPlaceholder.setVisibility(View.INVISIBLE);
                    nombrePLaceholder.setVisibility(View.INVISIBLE);
                    mensajeUsuario.setVisibility(View.VISIBLE);
                    eliminar.setVisibility(View.VISIBLE);
                    mensajeUsuario.setText("La solicitud ha sido rechazada");
                    m.setText("Rechazado");
                    break;
                    //CONTACTING
                case 4:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.GONE);
                    n.setVisibility(View.VISIBLE);
                    correo.setVisibility(View.VISIBLE);
                    ap.setVisibility(View.VISIBLE);
                    am.setVisibility(View.VISIBLE);
                    correoPlaceholder.setVisibility(View.VISIBLE);
                    nombrePLaceholder.setVisibility(View.VISIBLE);
                    mensajeUsuario.setVisibility(View.INVISIBLE);
                    eliminar.setVisibility(View.GONE);
                    m.setText("En contacto");
                    break;

                    //WAITING
                case 5:
                    a.setVisibility(View.GONE);
                    r.setVisibility(View.GONE);
                    //t.setVisibility(View.GONE);
                    n.setVisibility(View.INVISIBLE);
                    correo.setVisibility(View.INVISIBLE);
                    ap.setVisibility(View.INVISIBLE);
                    am.setVisibility(View.INVISIBLE);
                    correoPlaceholder.setVisibility(View.INVISIBLE);
                    nombrePLaceholder.setVisibility(View.INVISIBLE);
                    mensajeUsuario.setVisibility(View.VISIBLE);
                    eliminar.setVisibility(View.GONE);
                    mensajeUsuario.setText("En espera de solicitud");
                    m.setText("En espera de aprobacion");
                    break;

            }
        }
        void bindCardColor(final NotificationList item){


            if(item.getTipo().equals("ACCEPTED")){
            
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.home_gradient_green));
                dinChange(btnA, btnR, tipo,nombre,correo,ap,am,mensajeUsuario,correoPlaceholder,nombrePlaceHolder,eliminar,2);
            }
            else if(item.getTipo().equals("REJECTED")){
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.home_gradient_admin));
                dinChange(btnA, btnR, tipo,nombre,correo,ap,am,mensajeUsuario,correoPlaceholder,nombrePlaceHolder,eliminar,3);
            }
            else if(item.getTipo().equals("WAITING")){
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.home_gradient_inge));
                dinChange(btnA, btnR, tipo,nombre,correo,ap,am,mensajeUsuario,correoPlaceholder,nombrePlaceHolder,eliminar,5);
            }
            else if(item.getTipo().equals("CONTACTING")){
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.home_gradient_edu));
                dinChange(btnA, btnR, tipo,nombre,correo,ap,am,mensajeUsuario,correoPlaceholder,nombrePlaceHolder,eliminar,4);
            }
            else{
                //cardType.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.drawable.home_gradient_actua)));
                linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.home_gradient_actua));
                dinChange(btnA, btnR, tipo,nombre,correo,ap,am,mensajeUsuario,correoPlaceholder,nombrePlaceHolder,eliminar,1);
            }

        }

        public ViewHolderNotifications linkAdapter(NotificationAdapter adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
