package com.example.codeplay.kuxing.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codeplay.kuxing.R;

public class RegisterActivity extends AppCompatActivity {
    private ImageView Imageback;
    private TextView Textback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_register);
        Imageback =  findViewById(R.id.fanhui);
        Imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        Textback = findViewById(R.id.fh);
        Textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
    }
}
