package com.example.loginapp;

import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.DialogInterface;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

public class activity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_MSG = "msg";

    private TextView Name,Msg;
    private Button Exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Name = findViewById(R.id.tvName);
        Msg = findViewById(R.id.tvMsg);
        Exit = findViewById(R.id.btnExit);

        SharedPreferences shrd = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = shrd.getString(KEY_NAME,null);
        String msg = shrd.getString(KEY_MSG,null);

        if(name != null || msg != null){
            Name.setText("Name : "+name);
            Msg.setText(msg);
        }
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity.this);

                builder.setMessage("Do you want to exit the App?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
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
}
