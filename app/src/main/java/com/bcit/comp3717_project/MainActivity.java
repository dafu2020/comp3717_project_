package com.bcit.comp3717_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.main_tv_register);
        register.setOnClickListener(registerOnClick);
    }

    private final View.OnClickListener registerOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent registerIntent  = new Intent(MainActivity.this,
                    RegisterUser.class);
            startActivity(registerIntent);
        }
    };
}