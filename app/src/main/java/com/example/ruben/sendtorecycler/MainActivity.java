package com.example.ruben.sendtorecycler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.ruben.sendtorecycler.FormActivity.EXTRA_USER;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;

    private Button addBtn;
    private List<User> users;
    private String infoUser;
    final String LOG_TAG = "mylogs";
    final String FILENAME = "file";

    public static final  int REQUEST_CODE_CREATE_USER=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerId);

        GridLayoutManager manager = new GridLayoutManager(this, 3);

        users = new ArrayList<>();
        readInternal();

        recyclerView.setLayoutManager(manager);

         adapter = new UsersAdapter(users);

         recyclerView.setAdapter(adapter);


            addBtn = findViewById(R.id.addUserBtn);
             addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_USER);//qani vor result enq spasym

            }

        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CREATE_USER){
            if(resultCode==RESULT_OK){
                if(data!=null){
                    User user = new Gson().fromJson(data.getStringExtra(EXTRA_USER), User.class);
                    addUser(user);


                }
            }

        }
    }


    private void addUser(User user){
        Log.i("USER", "addUser:" +user.toString());
        users.add(0, user);
        infoUser=user.toString();

        Toast.makeText(this, infoUser, Toast.LENGTH_LONG).show();
        adapter.notifyItemInserted(0);

    }


    public void readInternal(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while((str = br.readLine())!=null){
                Log.d(LOG_TAG, str);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
