package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserData extends AppCompatActivity {

    String nombre, apellidoM, apellidoP, calle, colonia, municipio, estado, numInterno, codPostal, message, idUsuario;
    Integer cp, statusHoras, statusDocumentos;
    TextInputLayout eTnombre, eTapellidoM, eTapellidoP, eTcalle, eTcolonia, eTmunicipio, eTestado, eTnumInterno, eTcodPostal;
    Toolbar toolbar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircularProgressButton circularProgressButton;

    TextView btnUpdateUD, btnCancelUpdate;

    TextView tn, tap, tam, tca, tco, tm, te, tni, tcp;

    TextInputLayout tNombre, tApellidoM, tApellidoP, tCalle, tColonia, tMunicipio, tEstado, tNumInterno, tCodPostal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_user_data);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.WHITE);
        setTitle("Actualizar Información");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        eTnombre = findViewById(R.id.textInputNombreU);
        eTapellidoM = findViewById(R.id.textInputApellidoMU);
        eTapellidoP = findViewById(R.id.textInputApellidoPU);
        eTcalle = findViewById(R.id.textInputCalleU);
        eTcolonia = findViewById(R.id.textInputColoniaU);
        eTmunicipio = findViewById(R.id.textInputMunicipioU);
        eTestado = findViewById(R.id.textInputEstadoU);
        eTnumInterno = findViewById(R.id.textInputNumeroIU);
        eTcodPostal = findViewById(R.id.textInputCodigoPU);

        obtainUserData();

        circularProgressButton = findViewById(R.id.btnActualizarDatos);

        View view = findViewById(android.R.id.content).getRootView();
        getUpdateUserData(circularProgressButton, view);
    }

    public void colorText(TextInputLayout myInputLayout, String myString) {

        myInputLayout.getEditText().setTextColor(Color.parseColor("#ff0000"));
        myInputLayout.getEditText().setText(myString);
        myInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                myInputLayout.getEditText().setTextColor(Color.BLACK);
            }
        });

    }

    public boolean validateFields() {

        String regex_nombres = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        String regexCP = "\\d{5}";
        String regex_colonia = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        String regex_numI = "^[0-9A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{0,255}$";
        String regex_calle = "^[0-9A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern_nombre = Pattern.compile(regex_nombres);
        Pattern pattern_calle = Pattern.compile(regex_calle);
        Pattern pattern_colonia = Pattern.compile(regex_colonia);
        Pattern pattern_numI = Pattern.compile(regex_numI);
        Pattern patternCP = Pattern.compile(regexCP);

        Matcher matcher1 = pattern_nombre.matcher(nombre);
        Matcher matcher2 = pattern_nombre.matcher(apellidoM);
        Matcher matcher3 = pattern_nombre.matcher(apellidoP);
        Matcher matcher4 = pattern_calle.matcher(calle);
        Matcher matcher5 = pattern_colonia.matcher(colonia);
        Matcher matcher6 = pattern_colonia.matcher(municipio);
        Matcher matcher7 = pattern_colonia.matcher(estado);
        Matcher matcher8 = pattern_numI.matcher(numInterno);
        Matcher matcher9 = patternCP.matcher(codPostal);

        boolean flag1 = matcher1.matches();
        boolean flag2 = matcher2.matches();
        boolean flag3 = matcher3.matches();
        boolean flag4 = matcher4.matches();
        boolean flag5 = matcher5.matches();
        boolean flag6 = matcher6.matches();
        boolean flag7 = matcher7.matches();
        boolean flag8 = matcher8.matches();
        boolean flag9 = matcher9.matches();

        tn = findViewById(R.id.tVNameNotAcceptedU);
        tap = findViewById(R.id.tVAPNotAcceptedU);
        tam = findViewById(R.id.tVAMNotAcceptedU);
        tca = findViewById(R.id.tVCalleNotAcceptedU);
        tco = findViewById(R.id.tVColoniaNotAcceptedU);
        tm = findViewById(R.id.tVMunicipioNotAcceptedU);
        te = findViewById(R.id.tVEstadoNotAcceptedU);
        tni = findViewById(R.id.tVNINotAcceptedU);
        tcp = findViewById(R.id.tVCPNotAcceptedU);

        if (!flag1) {
            tn.setVisibility(View.VISIBLE);
            tn.setTextColor(Color.RED);
            tNombre = findViewById(R.id.textInputNombreU);
            colorText(tNombre,nombre);
            message = "El nombre no acepta caracteres especiales.";
            tn.setText(message);
        }
        else{
            tn.setVisibility(View.GONE);
        }
        if (!flag2) {
            tam.setVisibility(View.VISIBLE);
            tam.setTextColor(Color.RED);
            tApellidoM = findViewById(R.id.textInputApellidoMU);
            colorText(tApellidoM,apellidoM);
            message = "El apelldio materno no acepta caracteres especiales.";
            tam.setText(message);
        }
        else{
            tam.setVisibility(View.GONE);
        }
        if (!flag3) {
            tap.setVisibility(View.VISIBLE);
            tap.setTextColor(Color.RED);
            tApellidoP = findViewById(R.id.textInputApellidoPU);
            colorText(tApellidoP,apellidoP);
            message = "El apellido paterno no acepta caracteres especiales.";
            tap.setText(message);
        }
        else{
            tap.setVisibility(View.GONE);
        }
        if (!flag4) {
            tca.setVisibility(View.VISIBLE);
            tca.setTextColor(Color.RED);
            tCalle = findViewById(R.id.textInputCalleU);
            colorText(tCalle,calle);
            message = "La calle no acepta caracteres especiales.";
            tca.setText(message);
        }
        else{
            tca.setVisibility(View.GONE);
        }
        if (!flag5) {
            tco.setVisibility(View.VISIBLE);
            tco.setTextColor(Color.RED);
            tColonia = findViewById(R.id.textInputColoniaU);
            colorText(tColonia,colonia);
            message = "La colonia no acepta caracteres especiales.";
            tco.setText(message);
        }
        else{
            tco.setVisibility(View.GONE);
        }
        if (!flag6) {
            tm.setVisibility(View.VISIBLE);
            tm.setTextColor(Color.RED);
            tMunicipio = findViewById(R.id.textInputMunicipioU);
            colorText(tMunicipio,municipio);
            message = "El municipio no acepta caracteres especiales.";
            tm.setText(message);
        }
        else{
            tm.setVisibility(View.GONE);
        }
        if (!flag7) {
            te.setVisibility(View.VISIBLE);
            te.setTextColor(Color.RED);
            tEstado = findViewById(R.id.textInputEstadoU);
            colorText(tEstado,estado);
            message = "El estado no acepta caracteres especiales.";
            te.setText(message);
        }
        else{
            te.setVisibility(View.GONE);
        }
        if (!flag8) {
            tni.setVisibility(View.VISIBLE);
            tni.setTextColor(Color.RED);
            tNumInterno = findViewById(R.id.textInputNumeroIU);
            colorText(tNumInterno,numInterno);
            message = "El número interno no acepta caracteres especiales.";
            tni.setText(message);
        }
        else{
            tni.setVisibility(View.GONE);
        }
        if (!flag9) {
            tcp.setVisibility(View.VISIBLE);
            tcp.setTextColor(Color.RED);
            tCodPostal = findViewById(R.id.textInputCodigoPU);
            colorText(tCodPostal,codPostal);
            message = "El código postal solo puede tener una longitud de 5 dígitos.";
            tcp.setText(message);
        }
        else{
            tcp.setVisibility(View.GONE);
        }

        boolean flag = flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9;

        return flag;
    }

    public void obtainUserData() {
        idUsuario = preferences.getString("username","NULL");
        nombre = preferences.getString("name","Nombre");
        apellidoP = preferences.getString("lastName", "Apellido Paterno");
        apellidoM = preferences.getString("lastNameM","Apellido Materno");
        calle = preferences.getString("calle","Calle");
        colonia = preferences.getString("colonia", "Colonia");
        municipio = preferences.getString("municipio","Municipio");
        estado = preferences.getString("estado","Estado");
        numInterno = preferences.getString("numInt","Numero Interno");
        cp = preferences.getInt("codigoP",0);
        statusHoras = preferences.getInt("statusHours",0);
        statusDocumentos =preferences.getInt("documentosApproval",0);
        codPostal = cp.toString();
        setActualUserData();
    }
    public void setActualUserData(){
        eTnombre.getEditText().setText(nombre);
        eTapellidoM.getEditText().setText(apellidoM);
        eTapellidoP.getEditText().setText(apellidoP);
        eTcalle.getEditText().setText(calle);
        eTcolonia.getEditText().setText(colonia);
        eTmunicipio.getEditText().setText(municipio);
        eTestado.getEditText().setText(estado);
        eTnumInterno.getEditText().setText(numInterno);
        eTcodPostal.getEditText().setText(codPostal);
    }

    public void getUpdateUserInfo(){
        nombre = eTnombre.getEditText().getText().toString();
        apellidoM = eTapellidoM.getEditText().getText().toString();
        apellidoP = eTapellidoP.getEditText().getText().toString();
        calle = eTcalle.getEditText().getText().toString();
        colonia = eTcolonia.getEditText().getText().toString();
        municipio = eTmunicipio.getEditText().getText().toString();
        estado = eTestado.getEditText().getText().toString();
        numInterno = eTnumInterno.getEditText().getText().toString();
        codPostal = eTcodPostal.getEditText().getText().toString();
    }

    public void setUpdateUserData(){
        UpdateUserDataRequest updateUserDataRequest = new UpdateUserDataRequest();
        updateUserDataRequest.setIdUsuario(idUsuario);
        updateUserDataRequest.setNombre(nombre);
        updateUserDataRequest.setApellidoM(apellidoM);
        updateUserDataRequest.setApellidoP(apellidoP);
        updateUserDataRequest.setCalle(calle);
        updateUserDataRequest.setColonia(colonia);
        updateUserDataRequest.setMunicipio(municipio);
        updateUserDataRequest.setEstado(estado);
        updateUserDataRequest.setNumero(numInterno);
        updateUserDataRequest.setCP(Integer.parseInt(codPostal));
        updateUserData(updateUserDataRequest);
    }

    public void updateUserData(UpdateUserDataRequest updateUserDataRequest){
        Call<UpdateUserDataResponse> updateUserDataResponseCall = ApiClient.getService().updateUserData(updateUserDataRequest);
        updateUserDataResponseCall.enqueue(new Callback<UpdateUserDataResponse>() {
            @Override
            public void onResponse(Call<UpdateUserDataResponse> call, Response<UpdateUserDataResponse> response) {
                if (response.isSuccessful()) {
                    UpdateUserDataResponse updateUserDataResponse = response.body();
                    try {
                        if(updateUserDataResponse.getTransactionApproval() == 1){
                            message = "Datos actualizados correctamente, favor de iniciar sesión nuevamente.";
                            Toast.makeText(UpdateUserData.this, message, Toast.LENGTH_LONG).show();
                            editor.putBoolean("SaveSession",false);
                            editor.apply();
                            Intent menu = new Intent(UpdateUserData.this, LoginActivity.class);
                            startActivity(menu);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                            finish();
                        }else{
                            message = updateUserDataResponse.getError();
                            Toast.makeText(UpdateUserData.this, message, Toast.LENGTH_LONG).show();
                        }

                    }catch (NullPointerException nullPointerException){
                        message = "Error al actualizar los datos, favor de intentar más tarde";
                        Toast.makeText(UpdateUserData.this, message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    message ="Ocurrió un error, favor de intentar más tarde.";
                    Toast.makeText(UpdateUserData.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserDataResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(UpdateUserData.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUpdateUserData(CircularProgressButton btn, View view) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUpdateUserInfo();
                if (validateFields()) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_update, null);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;// lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.setElevation(20);
                    }

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window token
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popupWindow.dismiss();
                                return true;
                            }

                            return false;
                        }
                    });


                    // dismiss the popup window when touched
                    btnUpdateUD = (TextView) popupView.findViewById(R.id.btnUpdateUD);

                    btnUpdateUD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpdateUserData();
                            popupWindow.dismiss();
                        }
                    });

                    btnCancelUpdate = (TextView) popupView.findViewById(R.id.btnCancelUpdate);

                    btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}