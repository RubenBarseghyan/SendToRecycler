package com.example.ruben.sendtorecycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    public static final int REQUEST_CODE_DELECT_IMAGE=1002;

    final String LOG_TAG = "mylogs";
    final String FILENAME = "file";


    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Uri imageUri;

    private Button addImageBtn;
    private Button saveBtn;
    private CheckBox imageCheckBox;

    private Gson gson;
    private String infoGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        gson= new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        nameEditText = findViewById(R.id.nameEDitText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordId);


        addImageBtn = findViewById(R.id.addPhotoBtn);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_DELECT_IMAGE);
            }
        });



        saveBtn = findViewById(R.id.saveBtn);
        imageCheckBox = findViewById(R.id.checkBoxId);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                User user = new User(
                        nameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        date,
                        imageUri.toString()
                );
                //hima userin piti intent ov het tand
                String userJson = gson.toJson(user);
                Log.i("USERJSON", "jsonOfUser:" + userJson);

                infoGson = userJson;

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_USER, userJson);
                setResult(RESULT_OK, resultIntent);
                finish();//activitin pakum e

            }
        });

    }
//photo add anelu hamar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_DELECT_IMAGE){
            if(resultCode==RESULT_OK){
                imageUri = data.getData();
                imageCheckBox.setChecked(true);
                saveBtn.setEnabled(true);
            }
        }
    }

}
