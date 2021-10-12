package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewOfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOfferFragment extends Fragment {

    String message;
    List<UserOffersDetails> offersD;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String username;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewOfferFragment() {
        // Required empty public constructor
    }

    Button btnCreaOffer;
    ArrayList<OfferVO> listOffer;

    RecyclerView recyclerOfertas;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewOfferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewOfferFragment newInstance(String param1, String param2) {
        NewOfferFragment fragment = new NewOfferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_new_offer, container, false);
        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("username","username");
        setUserOffersValues();

        listOffer = new ArrayList<>();

        recyclerOfertas = (RecyclerView) vista.findViewById(R.id.rVNewOffer);

        recyclerOfertas.setLayoutManager(new LinearLayoutManager(getContext()));
        
        llenarLista();

        AdapterNewOffer myadapter = new AdapterNewOffer(listOffer);


        recyclerOfertas.setAdapter(myadapter);

        btnCreaOffer = (Button)vista.findViewById(R.id.btnNOffer);
        clickBtnCreateOffer(btnCreaOffer);


        return vista;
    }

    public void setUserOffersValues(){
        UserOffersRequest userOffersRequest = new UserOffersRequest();
        userOffersRequest.setUsername(username);
        getUserOffers(userOffersRequest);
    }

    public void getUserOffers(UserOffersRequest userOffersRequest){
        Call<UserOffersResponse> userOffersResponseCall = ApiClient.getService().getUserOffers(userOffersRequest);
        userOffersResponseCall.enqueue(new Callback<UserOffersResponse>() {
            @Override
            public void onResponse(Call<UserOffersResponse> call, Response<UserOffersResponse> response) {
                if (response.isSuccessful()) {
                    UserOffersResponse userOffersResponse = response.body();
                    offersD = new ArrayList<>(Arrays.asList(userOffersResponse.getOfertas()));

                } else {
                    message = "Ocurrió un error, favor de intentar más tarde";
                    Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserOffersResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void llenarLista() {
        /*
        listOffer.add(new OfferVO("Sastre", "Descripción de prueba", R.drawable.baseline_account_circle_black_48));
        listOffer.add(new OfferVO("Carpintero", "Descripción de prueba", R.drawable.baseline_account_circle_black_48));
        listOffer.add(new OfferVO("Plomero", "Cruzando la frontera me encontré con el\n" +
                "Era un tipo medio raro pero me cayo bien,\n" +
                "Me dijo viajo en carretera espero pronto\n" +
                "Llegar al rodeo que me espera allá.\n" +
                "Me dijo con certeza que no hay mas emoción\n" +
                "Que romper un sombrero disparar un cañón\n" +
                "Salvar la vida de un jinete cuando mal anda su suerte\n" +
                "Soy payaso de rodeo\n" +
                "Les digo ven, ven, ven, animalito ven,\n" +
                "Ven y sígueme y veras lo que vas a aprender,\n" +
                "No ves que soy muy poco artístico\n" +
                "Muy listo muy gracioso soy payaso de rodeo\n" +
                "Así llevamos largo tiempo luego se marcho,\n" +
                "Dejándome un mensaje que recuerdo hoy\n" +
                "Lo peligroso es gracioso\n" +
                "Lo difícil es hermoso\n" +
                "Lo mas grande es el rodeo\n" +
                "Me dijo con certeza que no hay mas emoción\n" +
                "Que romper un sombrero disparar un cañón\n" +
                "Salvar la vida de un jinete cuando mal anda su suerte\n" +
                "Ser payaso de rodeo\n" +
                "Les digo ven, ven, ven, animalito ven,\n" +
                "Ven y sígueme y veras lo que vas a aprender,\n" +
                "No ves que soy muy poco artístico\n" +
                "Muy listo muy gracioso soy payaso de rodeo.", R.drawable.baseline_account_circle_black_48));
        listOffer.add(new OfferVO("Tutor", "Agua. Tierra. Fuego. Aire. Hace muchos años, las cuatro naciones vivían en armonía. Pero todo cambió cuando la Nación del Fuego atacó. Sólo el Avatar, maestro de los cuatro elementos, podía detenerlos, pero cuando el mundo más lo necesitaba, desapareció. Después de cien años mi hermano y yo encontramos al nuevo Avatar, un Maestro Aire llamado Aang. Aunque sus habilidades para controlar el aire eran grandiosas, tenía mucho que aprender antes de poder salvar al mundo. Y yo creo que Aang podrá salvarnos.", R.drawable.baseline_account_circle_black_48));
        listOffer.add(new OfferVO("Pintor", "Descripción de prueba", R.drawable.baseline_account_circle_black_48));
        listOffer.add(new OfferVO("Médico", "Descripción de prueba", R.drawable.baseline_account_circle_black_48));
        */

        listOffer = new ArrayList<>();

        for (int i = 0; i < offersD.size(); i++){
            String nombre = offersD.get(i).getNombre();
            String descripcion = offersD.get(i).getDescripcion();
            String categoria = offersD.get(i).getCategoria();
            String imagen = offersD.get(i).getImagen();

            OfferVO oferta = new OfferVO(nombre, descripcion, imagen, categoria);
            listOffer.add(oferta);
        }
    }

    public void clickBtnCreateOffer(Button btnCreateOffer){
        btnCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent creaOferta = new Intent(getActivity().getApplicationContext(), CreateOffer.class);
                getActivity().startActivity(creaOferta);

            }
        });
    }
}