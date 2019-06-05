package com.example.codeplay.kuxing.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.codeplay.kuxing.Fragment.FragmentCommunity;
import com.example.codeplay.kuxing.Fragment.FragmentMap;
import com.example.codeplay.kuxing.Fragment.FragmentMy;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.SQLiteDAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup bottom_bar;
    private FragmentManager fManager;
    private RadioButton rb_map;

    private Fragment fg1;
    private Fragment fg2;
    private Fragment fg3;

    private RadioButton radio0, radio1, radio2;
    private Drawable map, trend, my;

    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();

        //修改图标大小
        radio0 = (RadioButton) findViewById(R.id.radio0);
        map = getResources().getDrawable(R.mipmap.map_select);
        map.setBounds(0,0,50,50);
        radio0.setCompoundDrawables(null,map,null,null);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        trend = getResources().getDrawable(R.mipmap.trend_unselect);
        trend.setBounds(0,0,50,50);
        radio1.setCompoundDrawables(null,trend,null,null);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        my = getResources().getDrawable(R.mipmap.my_unselect);
        my.setBounds(0,0,50,50);
        radio2.setCompoundDrawables(null,my,null,null);

        bottom_bar = (RadioGroup) findViewById(R.id.bottom_bar);
        bottom_bar.setOnCheckedChangeListener(this);
        rb_map = (RadioButton)findViewById(R.id.radio0);
        rb_map.setChecked(true);
        SQLiteDAOImpl sqLiteDAO = new SQLiteDAOImpl();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId) {
            case R.id.radio0:
                if(fg1 == null){
                    fg1 = new FragmentMap();
                    fTransaction.add(R.id.layout_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                radio0.setTextColor(Color.parseColor("#FF53BFA2"));
                map = getResources().getDrawable(R.mipmap.map_select);
                map.setBounds(0,0,50,50);
                radio0.setCompoundDrawables(null,map,null,null);
                radio1.setTextColor(Color.parseColor("#FF000000"));
                trend = getResources().getDrawable(R.mipmap.trend_unselect);
                trend.setBounds(0,0,50,50);
                radio1.setCompoundDrawables(null,trend,null,null);
                radio2.setTextColor(Color.parseColor("#FF000000"));
                my = getResources().getDrawable(R.mipmap.my_unselect);
                my.setBounds(0,0,50,50);
                radio2.setCompoundDrawables(null,my,null,null);
                break;
            case R.id.radio1:
                if(fg2 == null){
                    fg2 = new FragmentCommunity();
                    fTransaction.add(R.id.layout_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                radio0.setTextColor(Color.parseColor("#FF000000"));
                map = getResources().getDrawable(R.mipmap.map_unselect);
                map.setBounds(0,0,50,50);
                radio0.setCompoundDrawables(null,map,null,null);
                radio1.setTextColor(Color.parseColor("#FF54BFA2"));
                trend = getResources().getDrawable(R.mipmap.trend_select);
                trend.setBounds(0,0,50,50);
                radio1.setCompoundDrawables(null,trend,null,null);
                radio2.setTextColor(Color.parseColor("#FF000000"));
                my = getResources().getDrawable(R.mipmap.my_unselect);
                my.setBounds(0,0,50,50);
                radio2.setCompoundDrawables(null,my,null,null);
                break;
            case R.id.radio2:
                if(fg3 == null){
                    fg3 = new FragmentMy();
                    fTransaction.add(R.id.layout_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                radio0.setTextColor(Color.parseColor("#FF000000"));
                map = getResources().getDrawable(R.mipmap.map_unselect);
                map.setBounds(0,0,50,50);
                radio0.setCompoundDrawables(null,map,null,null);
                radio1.setTextColor(Color.parseColor("#FF000000"));
                trend = getResources().getDrawable(R.mipmap.trend_unselect);
                trend.setBounds(0,0,50,50);
                radio1.setCompoundDrawables(null,trend,null,null);
                radio2.setTextColor(Color.parseColor("#FF53BFA2"));
                my = getResources().getDrawable(R.mipmap.my_select);
                my.setBounds(0,0,50,50);
                radio2.setCompoundDrawables(null,my,null,null);
                break;
            default:
                break;
        }
        fTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //转发返回的结果给fragment
        fg1.onActivityResult(requestCode, resultCode, data);
    }

}
