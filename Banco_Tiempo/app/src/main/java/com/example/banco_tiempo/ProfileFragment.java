package com.example.banco_tiempo;

import static java.lang.Thread.sleep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    Button btnUserData;
    Button btnUserDocuments;
    Button btnImg;
    Button btnCncl;


    TextView imgPath;
    ImageView image;
    Uri selectedImage;
    String part_image;
    String sImage;
    Context applicationContext = MainActivity.getContextOfApplication();

    TextView nameTextView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String username;

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String ARG_PARAM1 = "param1";
    private final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = root.findViewById(R.id.tVUserName);
        preferences = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //Set Header Nav
        String name = preferences.getString("name","Nombre del Usuario");
        String lastName = preferences.getString("lastName","");
        nameTextView.setText( name + " " + lastName);

        username = preferences.getString("username","username");

        // Set listener en btnUserData
        btnUserData = (Button)root.findViewById(R.id.btnUserData);
        clickBtnUserData(btnUserData);

        btnUserDocuments = (Button) root.findViewById(R.id.btnUserDocuments);
        clickBtnUserDocuments(btnUserDocuments);

        image = root.findViewById(R.id.iVUserProfile);
        String imageProfile = preferences.getString("foto",null);
        if (imageProfile.equals("NULL")){
            image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_account_circle_black_48,null));
        }else{
            Picasso.get().invalidate(imageProfile);
            Picasso.get().load(imageProfile).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(image);
        }

        pick(image, root);

        return root;
    }

    public void clickBtnUserData(Button btnUserData){
        btnUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userData = new Intent(getActivity().getApplicationContext(), UserDataActivity.class);
                getActivity().startActivity(userData);
            }
        });

    }

    public void clickBtnUserDocuments(Button btnUserDocuments){
        btnUserDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userData = new Intent(getActivity().getApplicationContext(), UserDocumentsActivity.class);
                getActivity().startActivity(userData);
            }
        });
    }

    public void pick (ImageView image, View root) {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyStoragePermissions();
                mGetContent.launch("image/*");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onButtonShowPopupWindowClick(view, root);
            }
        });

    }

    public void onButtonShowPopupWindowClick(View view, View root) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(20);
        }

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        btnImg = (Button)popupView.findViewById(R.id.btnImg);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageProfile();
                popupWindow.dismiss();
            }
        });

        btnCncl = (Button)popupView.findViewById(R.id.btnCncl);

        btnCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_account_circle_black_48,null));
                popupWindow.dismiss();
            }
        });

    }

    public String getImagePath(Uri uri){
        Cursor cursor = applicationContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = applicationContext.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @SuppressLint("Range")
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onActivityResult(Uri uri) {
                    image.setImageURI(uri);
                    selectedImage = MediaStore.Images.Media.getContentUri("external");// Get the image file URI
                    String[] imageProjection = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};

                    // Obtain path image & fileName image
                    String path = getImagePath(uri);
                    File file = new File(path);
                    String fileName = file.getName();
                    String selectionClause = MediaStore.Images.ImageColumns.DISPLAY_NAME + "=?";
                    String[] args = {fileName};

                    Cursor cursor = applicationContext.getContentResolver().query(selectedImage, imageProjection,selectionClause, args, null);
                    if (cursor.getCount()>0) {
                        cursor.moveToPosition(0);
                        part_image = cursor.getString(cursor.getColumnIndex(imageProjection[0]));
                        cursor.close();
                        try {
                            byte[] buffer = new byte[(int) file.length() + 100];
                            @SuppressWarnings("resource")
                            int length = new FileInputStream(file).read(buffer);
                            sImage = Base64.encodeToString(buffer, 0, length,
                                    Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Algo Salio mal", Toast.LENGTH_SHORT);
                    }
                }
            });

    // Upload the image to the remote database
    public void uploadImageProfile() {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setImage(sImage);
        imageRequest.setUsername(username);
        imageRequest.setType("ProfilePicture");
        uploadImageServer(imageRequest);
    }

    public void uploadImageServer(ImageRequest imageRequest){
        Call<ImageResponse> registerResponseCall = ApiClient.getService().uploadImageServer(imageRequest);
        registerResponseCall.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse>  call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    ImageResponse imageResponse = response.body();
                    startActivity(new Intent(getActivity(), MainActivity.class).putExtra("data", imageResponse));
                    String message = "Image Uploaded Successfully";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                } else {
                    String message = "An error occurred, please try again...";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void verifyStoragePermissions() {
        int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}