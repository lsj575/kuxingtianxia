package com.example.codeplay.kuxing.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.codeplay.kuxing.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //隐藏系统自带标题栏
        /*getSupportActionBar().hide();
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }*/
    }
}
