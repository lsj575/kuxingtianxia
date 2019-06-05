package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.codeplay.kuxing.R;

public class BeginActivity extends AppCompatActivity {
    SharedPreferences setting;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.personal_main);
        Button buttonL = findViewById(R.id.login);
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Button buttonR = findViewById(R.id.register);
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
