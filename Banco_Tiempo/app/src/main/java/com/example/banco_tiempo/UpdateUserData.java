package com.example.banco_tiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText eTnombre, eTapellidoM, eTapellidoP, eTcalle, eTcolonia, eTmunicipio, eTestado, eTnumInterno, eTcodPostal;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircularProgressButton circularProgressButton;

    TextView btnUpdateUD, btnCancelUpdate;

    TextInputLayout tNombre, tApellidoM, tApellidoP, tCalle, tColonia, tMunicipio, tEstado, tNumInterno, tCodPostal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_user_data);

        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        eTnombre = findViewById(R.id.eTNombreU);
        eTapellidoM = findViewById(R.id.eTApellidoMU);
        eTapellidoP = findViewById(R.id.eTApellidoPU);
        eTcalle = findViewById(R.id.eTCalleU);
        eTcolonia = findViewById(R.id.eTColoniaU);
        eTmunicipio = findViewById(R.id.eTMunicipioU);
        eTestado = findViewById(R.id.eTEstadoU);
        eTnumInterno = findViewById(R.id.eTNumeroIU);
        eTcodPostal = findViewById(R.id.eTCodigoPU);

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

        String regex = "\\w{1,255}";
        String regexCP = "\\d{5}";
        Pattern pattern = Pattern.compile(regex);
        Pattern patternCP = Pattern.compile(regexCP);

        Matcher matcher1 = pattern.matcher(nombre);
        Matcher matcher2 = pattern.matcher(apellidoM);
        Matcher matcher3 = pattern.matcher(apellidoP);
        Matcher matcher4 = pattern.matcher(calle);
        Matcher matcher5 = pattern.matcher(colonia);
        Matcher matcher6 = pattern.matcher(municipio);
        Matcher matcher7 = pattern.matcher(estado);
        Matcher matcher8 = pattern.matcher(numInterno);
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

        if (!flag1) {
            tNombre = findViewById(R.id.textInputNombreU);
            colorText(tNombre, nombre);
        }
        if (!flag2) {
            tApellidoM = findViewById(R.id.textInputApellidoMU);
            colorText(tApellidoM, apellidoM);
        }
        if (!flag3) {
            tApellidoP = findViewById(R.id.textInputApellidoPU);
            colorText(tApellidoP, apellidoP);
        }
        if (!flag4) {
            tCalle = findViewById(R.id.textInputCalleU);
            colorText(tCalle, calle);
        }
        if (!flag5) {
            tColonia = findViewById(R.id.textInputColoniaU);
            colorText(tColonia, colonia);
        }
        if (!flag6) {
            tMunicipio = findViewById(R.id.textInputMunicipioU);
            colorText(tMunicipio, municipio);
        }
        if (!flag7) {
            tEstado = findViewById(R.id.textInputEstadoU);
            colorText(tEstado, estado);
        }
        if (!flag8) {
            tNumInterno = findViewById(R.id.textInputNumeroIU);
            colorText(tNumInterno, numInterno);
        }
        if (!flag9) {
            tCodPostal = findViewById(R.id.textInputCodigoPU);
            colorText(tCodPostal, codPostal);
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
        eTnombre.setText(nombre, TextView.BufferType.EDITABLE);
        eTapellidoM.setText(apellidoM, TextView.BufferType.EDITABLE);
        eTapellidoP.setText(apellidoP, TextView.BufferType.EDITABLE);
        eTcalle.setText(calle, TextView.BufferType.EDITABLE);
        eTcolonia.setText(colonia, TextView.BufferType.EDITABLE);
        eTmunicipio.setText(municipio, TextView.BufferType.EDITABLE);
        eTestado.setText(estado, TextView.BufferType.EDITABLE);
        eTnumInterno.setText(numInterno, TextView.BufferType.EDITABLE);
        eTcodPostal.setText(codPostal, TextView.BufferType.EDITABLE);
    }

    public void getUpdateUserInfo(){
        nombre = eTnombre.getText().toString();
        apellidoM = eTapellidoM.getText().toString();
        apellidoP = eTapellidoP.getText().toString();
        calle = eTcalle.getText().toString();
        colonia = eTcolonia.getText().toString();
        municipio = eTmunicipio.getText().toString();
        estado = eTestado.getText().toString();
        numInterno = eTnumInterno.getText().toString();
        codPostal = eTcodPostal.getText().toString();
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
                else{
                    message = "Los datos en color rojo son incorrectos, favor de verificarlos.";
                    Toast.makeText(UpdateUserData.this,message,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}