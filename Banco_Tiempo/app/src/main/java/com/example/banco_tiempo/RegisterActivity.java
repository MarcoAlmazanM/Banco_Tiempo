package com.example.banco_tiempo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkPrivacity;
    EditText eTNombre, eTAP,eTAM, eTCalle, eTNumInt,
            eTColonia,eTMunicipio, eTEstado, eTCP,
            eTEmailR, eTUsername, eTPassword, eTPasswordConfirm;
    String nombre, apellidoPaterno, apellidoMaterno, calle, numInt, colonia,
            municipio, estado, cP, email, username, password, passwordConfirm, message, passwordHash, sImage;

    Boolean saveUserData =false;

    Integer codigo;

    TextInputLayout tNombre, tAP,tAM, tCalle, tNumInt,
            tColonia,tMunicipio, tEstado, tCP,
            tEmailR, tUsername, tPassword, tPasswordConfirm;

    ImageView profileImage;

    Uri uri;

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        //registerIntent();
        // Image View
        profileImage = findViewById(R.id.iVProfPic);
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

        }
    }

    public void colorText(TextInputLayout myInputLayout, String myString) {

        myInputLayout.getEditText().setTextColor(Color.parseColor("#ff0000"));
        myInputLayout.getEditText().setText(myString);
        myInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInputLayout.getEditText().setTextColor(Color.BLACK);
            }
        });

    }

    public boolean validateName() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);

        boolean flag = matcher.matches();

        if (!flag) {
            tNombre = findViewById(R.id.textInputName);
            colorText(tNombre, nombre);
        }
        return flag;
    }

    public boolean validateMiddleName() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apellidoPaterno);

        boolean flag = matcher.matches();

        if (!flag) {
            tAP = findViewById(R.id.textInputAP);
            colorText(tAP, apellidoPaterno);
        }
        return flag;
    }

    public boolean validateLastName() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apellidoMaterno);

        boolean flag = matcher.matches();

        if (!flag) {
            tAM = findViewById(R.id.textInputAM);
            colorText(tAM, apellidoMaterno);
        }
        return flag;
    }

    public boolean validateEmail() {
        boolean flag = true;

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException exception) {
            flag = false;
            tEmailR = findViewById(R.id.textInputEmail);
            colorText(tEmailR, email);
        }

        return flag;
    }

    public boolean validateUsername() {
        String regex = "^[0-9A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);

        boolean flag = matcher.matches();

        if (!flag) {
            tUsername = findViewById(R.id.textInputUsername);
            colorText(tUsername, username);
        }
        return flag;
    }

    public boolean validatePassword() {
        String regex = "^(?=.*[a-zñ])(?=.*[A-ZÑ])(?=.*\\d)(?=.*[@$!%*?&])[A-ZÑa-zñ\\d@$!%*?&]{8,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        boolean flag = matcher.matches();

        if (!flag) {
            tPassword = findViewById(R.id.textInputPassword);
            colorText(tPassword, password);
        }
        return flag;
    }

    public boolean validatePasswordConfirm() {
        String regex = "^(?=.*[a-zñ])(?=.*[A-ZÑ])(?=.*\\d)(?=.*[@$!%*?&])[A-ZÑa-zñ\\d@$!%*?&]{8,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordConfirm);

        boolean flag = matcher.matches();

        if (!flag) {
            tPasswordConfirm = findViewById(R.id.textInputPasswordConf);
            colorText(tPasswordConfirm, passwordConfirm);
        }
        return flag;
    }

    public boolean validateCalle() {
        String regex = "^[0-9A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(calle);

        boolean flag = matcher.matches();

        if (!flag) {
            tCalle = findViewById(R.id.textInputCalle);
            colorText(tCalle, calle);
        }
        return flag;
    }

    public boolean validateColonia() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(colonia);

        boolean flag = matcher.matches();

        if (!flag) {
            tColonia = findViewById(R.id.textInputColonia);
            colorText(tColonia, colonia);
        }
        return flag;
    }

    public boolean validateMunicipio() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(municipio);

        boolean flag = matcher.matches();

        if (!flag) {
            tMunicipio = findViewById(R.id.textInputMunicipio);
            colorText(tMunicipio, municipio);
        }
        return flag;
    }

    public boolean validateEstado() {
        String regex = "^[A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{1,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(estado);

        boolean flag = matcher.matches();

        if (!flag) {
            tEstado = findViewById(R.id.textInputEstado);
            colorText(tEstado, estado);
        }
        return flag;
    }

    public boolean validateNumInterno() {
        String regex = "^[0-9A-Za-z ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]{0,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numInt);

        boolean flag = matcher.matches();

        if (!flag) {
            tNumInt = findViewById(R.id.textInputNumInt);
            colorText(tNumInt, numInt);
        }
        return flag;
    }

    public boolean validateCodigoPostal() {
        String regex = "\\d{5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cP);

        boolean flag = matcher.matches();

        if (!flag) {
            tCP = findViewById(R.id.textInputCP);
            colorText(tCP, cP);
        }
        return flag;
    }

    public boolean validateFields() {

        boolean name = false;
        boolean middle = false;
        boolean last = false;
        boolean email = false;
        boolean username = false;
        boolean pass = false;
        boolean confirmpass = false;

        TextView tname, tmiddle, tlast, temail, tusername, tpass, tconfirmpass;

        if (validateName()) {
            name = true;
            tname = findViewById(R.id.tVNameNotAccepted);
            tname.setVisibility(View.GONE);
        }
        else {
            tname = findViewById(R.id.tVNameNotAccepted);
            tname.setVisibility(View.VISIBLE);
            tname.setTextColor(Color.RED);
            String message = "El nombre no acepta caracteres especiales.";
            tname.setText(message);
        }
        if (validateMiddleName()) {
            middle = true;
            tmiddle = findViewById(R.id.tVAMNotAccepted);
            tmiddle.setVisibility(View.GONE);
        }
        else {
            tmiddle = findViewById(R.id.tVAMNotAccepted);
            tmiddle.setVisibility(View.VISIBLE);
            tmiddle.setTextColor(Color.RED);
            String message = "El apellido materno no acepta caracteres especiales.";
            tmiddle.setText(message);
        }
        if (validateLastName()) {
            last = true;
            tlast = findViewById(R.id.tVAPNotAccepted);
            tlast.setVisibility(View.GONE);
        }
        else {
            tlast = findViewById(R.id.tVAPNotAccepted);
            tlast.setVisibility(View.VISIBLE);
            tlast.setTextColor(Color.RED);
            String message = "El apellido paterno no acepta caracteres especiales.";
            tlast.setText(message);
        }
        if (validateEmail()) {
            email = true;
            temail = findViewById(R.id.tVEmailNotAccepted);
            temail.setVisibility(View.GONE);
        }
        else {
            temail = findViewById(R.id.tVEmailNotAccepted);
            temail.setVisibility(View.VISIBLE);
            temail.setTextColor(Color.RED);
            String message = "El correo electrónico no es válido.";
            temail.setText(message);

        }
        if (validateUsername()) {
            username = true;
            tusername = findViewById(R.id.tVUserNameNotAccepted);
            tusername.setVisibility(View.GONE);
        }
        else{
            tusername = findViewById(R.id.tVUserNameNotAccepted);
            tusername.setVisibility(View.VISIBLE);
            tusername.setTextColor(Color.RED);
            String message = "El usuario no acepta caracteres especiales.";
            tusername.setText(message);
        }
        if (validatePassword()) {
            pass = true;
            tpass = findViewById(R.id.tVPassNotAccepted);
            tpass.setVisibility(View.GONE);
        }
        else{
            tpass = findViewById(R.id.tVPassNotAccepted);
            tpass.setVisibility(View.VISIBLE);
            tpass.setTextColor(Color.RED);
            String message = "Mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula, un número y un caracter especial.";
            tpass.setText(message);
        }
        if (validatePasswordConfirm()) {
            confirmpass = true;
            tconfirmpass = findViewById(R.id.tVPassConfNotAccepted);
            tconfirmpass.setVisibility(View.GONE);
        }
        else{
            tconfirmpass = findViewById(R.id.tVPassConfNotAccepted);
            tconfirmpass.setVisibility(View.VISIBLE);
            tconfirmpass.setTextColor(Color.RED);
            String message = "Mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula, un número y un caracter especial.";
            tconfirmpass.setText(message);
        }

        return (name && middle && last && email && username && pass && confirmpass);
    }

    public boolean validateFields2() {

        boolean calle = false;
        boolean colonia = false;
        boolean municipio = false;
        boolean estado = false;
        boolean numint = false;
        boolean codpost = false;

        TextView tcalle, tcolonia, tmunicipio, testado, tnumint, tcodpost;

        if (validateCalle()) {
            calle = true;
            tcalle = findViewById(R.id.tVCalleNotAccepted);
            tcalle.setVisibility(View.GONE);
        }
        else {
            tcalle = findViewById(R.id.tVCalleNotAccepted);
            tcalle.setVisibility(View.VISIBLE);
            tcalle.setTextColor(Color.RED);
            String message = "La calle no acepta caracteres especiales.";
            tcalle.setText(message);
        }
        if (validateColonia()) {
            colonia = true;
            tcolonia = findViewById(R.id.tVColoniaNotAccepted);
            tcolonia.setVisibility(View.GONE);
        }
        else {
            tcolonia = findViewById(R.id.tVColoniaNotAccepted);
            tcolonia.setVisibility(View.VISIBLE);
            tcolonia.setTextColor(Color.RED);
            String message = "La colonia no acepta caracteres especiales.";
            tcolonia.setText(message);

        }
        if (validateMunicipio()) {
            municipio = true;
            tmunicipio = findViewById(R.id.tVMuniNotAccepted);
            tmunicipio.setVisibility(View.GONE);
        }
        else {
            tmunicipio = findViewById(R.id.tVMuniNotAccepted);
            tmunicipio.setVisibility(View.VISIBLE);
            tmunicipio.setTextColor(Color.RED);
            String message = "El municipio no acepta caracteres especiales.";
            tmunicipio.setText(message);

        }
        if (validateEstado()) {
            estado = true;
            testado = findViewById(R.id.tVEstadoNotAccepted);
            testado.setVisibility(View.GONE);
        }
        else {
            testado = findViewById(R.id.tVEstadoNotAccepted);
            testado.setVisibility(View.VISIBLE);
            testado.setTextColor(Color.RED);
            String message = "El estado no acepta caracteres especiales.";
            testado.setText(message);

        }
        if (validateNumInterno()) {
            numint = true;
            tnumint = findViewById(R.id.tVNINotAccepted);
            tnumint.setVisibility(View.GONE);
        }
        else {
            tnumint = findViewById(R.id.tVNINotAccepted);
            tnumint.setVisibility(View.VISIBLE);
            tnumint.setTextColor(Color.RED);
            String message = "El número interno no acepta caracteres especiales.";
            tnumint.setText(message);
        }
        if (validateCodigoPostal()) {
            codpost = true;
            tcodpost = findViewById(R.id.tVCodigoPostalNotAccepted);
            tcodpost.setVisibility(View.GONE);
        }
        else {
            tcodpost = findViewById(R.id.tVCodigoPostalNotAccepted);
            tcodpost.setVisibility(View.VISIBLE);
            tcodpost.setTextColor(Color.RED);
            String message = "El código postal solo puede tener una longitud de 5 dígitos.";
            tcodpost.setText(message);
        }

        return (calle && colonia && municipio && estado && numint && codpost);
    }

    public void verifyData(View view){
        // Find Id EditText
        eTNombre =findViewById(R.id.eTNombre);
        eTAP = findViewById(R.id.eTAP);
        eTAM = findViewById(R.id.eTAM);
        eTEmailR = findViewById(R.id.eTEmailR);
        eTUsername = findViewById(R.id.eTUsernameR);
        eTPassword = findViewById(R.id.eTPasswordR);
        eTPasswordConfirm = findViewById(R.id.eTPasswordConfirm);

        // Set strings UI
        nombre = eTNombre.getText().toString();
        apellidoPaterno = eTAP.getText().toString();
        apellidoMaterno = eTAM.getText().toString();
        email = eTEmailR.getText().toString();
        username = eTUsername.getText().toString();
        password = eTPassword.getText().toString();
        passwordConfirm = eTPasswordConfirm.getText().toString();

        if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellidoPaterno) || TextUtils.isEmpty(apellidoMaterno)  ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(passwordConfirm)){
            message = "Todos los campos son requeridos.";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else if (!password.equals(passwordConfirm)){
            message = "Las contraseñas no coinciden .";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(sImage)){
            message = "La foto de perfil es requerida";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        } else{
            if (validateFields()) {
                Resources resources = new Resources();
                passwordHash = resources.hash256(password);
                setContentView(R.layout.activity_register_second);
                if (saveUserData) {
                    userInfo2();
                }
            }
            else {
                message = "Algún campo es incorrecto.";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void verifyData2(View view){

        // Find Id EditText
        eTCalle = findViewById(R.id.eTCalle);
        eTNumInt = findViewById(R.id.eTNumInt);
        eTColonia = findViewById(R.id.eTColonia);
        eTMunicipio = findViewById(R.id.eTMunicipio);
        eTEstado = findViewById(R.id.eTEstado);
        eTCP = findViewById(R.id.eTCP);
        checkPrivacity = findViewById(R.id.cBPrivacity);

        // Set strings UI
        calle = eTCalle.getText().toString();
        numInt = eTNumInt.getText().toString();
        colonia = eTColonia.getText().toString();
        municipio = eTMunicipio.getText().toString();
        estado = eTEstado.getText().toString();
        cP = eTCP.getText().toString();

        if (TextUtils.isEmpty(calle)|| TextUtils.isEmpty(numInt) || TextUtils.isEmpty(colonia) ||
                TextUtils.isEmpty(municipio) || TextUtils.isEmpty(estado) || TextUtils.isEmpty(cP)){

        }else if(!checkPrivacity.isChecked()){
            message = "Se requiere aceptar las condiciones de uso y privacidad de la aplicación.";
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        }else{
            if (validateFields2()) {
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setNombre(nombre);
                registerRequest.setApellidoP(apellidoPaterno);
                registerRequest.setApellidoM(apellidoMaterno);
                registerRequest.setCalle(calle);
                registerRequest.setNumero(numInt);
                registerRequest.setColonia(colonia);
                registerRequest.setMunicipio(municipio);
                registerRequest.setEstado(estado);
                registerRequest.setCPP(cP);
                registerRequest.setCorreo(email);
                registerRequest.setIdUsuario(username);
                registerRequest.setContrasena(passwordHash);
                registerRequest.setImage(sImage);
                registerUser(registerRequest);
            }

            else {
                message = "Algún campo es incorrecto.";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void clickBtnProfileImage(View view){
        verifyStoragePermissions(RegisterActivity.this);
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(Intent.createChooser(i, "Select Picture"));
    }

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        uri = data.getData();
                        if (null != uri) {
                            Transformation transformation = new RoundedCornersTransformation(100,5);
                            Picasso.get().load(Uri.parse(uri.toString())).resize(120,120).centerCrop().transform(transformation).into(profileImage);
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                byte[] byteArray = outputStream.toByteArray();
                                //Encode Base 64 Image
                                sImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


    public void userInfo(Integer codigo){
        profileImage = findViewById(R.id.iVProfPic);
        tNombre = findViewById(R.id.textInputName);
        tAM = findViewById(R.id.textInputAM);
        tAP = findViewById(R.id.textInputAP);
        tUsername=findViewById(R.id.textInputUsername);
        tEmailR = findViewById(R.id.textInputEmail);
        tPassword = findViewById(R.id.textInputPassword);
        tPasswordConfirm = findViewById(R.id.textInputPasswordConf);
        Transformation transformation = new RoundedCornersTransformation(100,5);
        Picasso.get().load(Uri.parse(uri.toString())).resize(120,120).centerCrop().transform(transformation).into(profileImage);
        tNombre.getEditText().setText(nombre);
        tAM.getEditText().setText(apellidoMaterno);
        tAP.getEditText().setText(apellidoPaterno);
        if(codigo == 0){
            tUsername.getEditText().setTextColor(Color.parseColor("#ff0000"));
            tUsername.getEditText().setText(username);
            tUsername.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    tUsername.getEditText().setTextColor(Color.BLACK);
                }
            });
            tEmailR.getEditText().setText(email);
        }else if(codigo == 1){
            tUsername.getEditText().setText(username);
            tEmailR.getEditText().setTextColor(Color.parseColor("#ff0000"));
            tEmailR.getEditText().setText(email);
            tEmailR.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    tEmailR.getEditText().setTextColor(Color.BLACK);
                }
            });
        }else if(codigo == 2){
            tUsername.getEditText().setText(username);
            tEmailR.getEditText().setText(email);
        }
        tPassword.getEditText().setText(password);
        tPasswordConfirm.getEditText().setText(password);
    }

    public void userInfo2(){
        tCalle = findViewById(R.id.textInputCalle);
        tColonia = findViewById(R.id.textInputColonia);
        tMunicipio = findViewById(R.id.textInputMunicipio);
        tEstado = findViewById(R.id.textInputEstado);
        tNumInt = findViewById(R.id.textInputNumInt);
        checkPrivacity = findViewById(R.id.cBPrivacity);
        tCP = findViewById(R.id.textInputCP);
        tCalle.getEditText().setText(calle);
        tColonia.getEditText().setText(colonia);
        tMunicipio.getEditText().setText(municipio);
        tEstado.getEditText().setText(estado);
        tNumInt.getEditText().setText(numInt);
        tCP.getEditText().setText(cP);
        checkPrivacity.setChecked(true);
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    try {
                        if (registerResponse.getRegisterApproval() == 1) {
                            message = "Usuario Registrado Exitosamente";
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(login);
                            finish();
                        } else {
                            setContentView(R.layout.activity_register);
                            message = registerResponse.getError();
                            if(message.equals("Usuario duplicado")){
                                codigo = 0;
                            }else if(message.equals("Correo duplicado")){
                                codigo=1;
                            }
                            userInfo(codigo);
                            saveUserData = true;
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }catch (NullPointerException nullPointerException){
                        message = "Error al registar, favor de intentar más tarde";
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    message ="Ocurrió un error, favor de intentar más tarde.";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goBackLogin(View view){
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
        finish();

    }

    public void goBackRegister(View view){
        setContentView(R.layout.activity_register);
        codigo = 2;
        userInfo(codigo);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}