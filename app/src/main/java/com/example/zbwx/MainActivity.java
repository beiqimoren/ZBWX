package com.example.zbwx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private MyApplication myApplication;
    private TextView text_user;
    private Button _login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text_user=findViewById(R.id.text_user);
        sp = this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);

        if(sp.getBoolean("AUTO_ISCHECK", false))
        {
            myApplication= (MyApplication) getApplication();
            myApplication.setUsername(sp.getString("USER_NAME",""));
        }else
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //text_user.setText(myApplication.getUsername());

//        _login=findViewById(R.id.login);
//        _login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                sp.edit().putBoolean("AUTO_ISCHECK", false).apply();
//
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });


    }
}