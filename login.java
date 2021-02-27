package com.example.loginapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    public EditText name, password;
    public Button login, cancel;
    public TextView info;
    public int counter = 3;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_MSG = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        cancel = findViewById(R.id.btnCancel);
        info = findViewById(R.id.tvInfo);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), password.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);

                builder.setMessage("Do you want to exit the App?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void validate(String userName, String userPassword){
        if(userName.equals("")){
            name.setError("Name is required");
        }else if(userPassword.length()<6){
            password.setError("Minimum 6 char required");
        }
        else if ((userName.equals("Harsh")) && (userPassword.equals("18IT111")) ){
            SharedPreferences shrd = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

            SharedPreferences.Editor editor = shrd.edit();
            editor.putString(KEY_NAME,name.getText().toString());
            editor.putString(KEY_MSG,"Login Successful...");
            editor.apply();
            Toast.makeText(login.this, "Login Successful...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(login.this, activity.class));
        }
        else{
            counter--;
            Toast.makeText(login.this, "Login Failed...", Toast.LENGTH_SHORT).show();

            info.setText("Number of attempts remaining: "+ String.valueOf(counter));

            if(counter == 0){
                login.setEnabled(false);
            }
        }
    }
}
