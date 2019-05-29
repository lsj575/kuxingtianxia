package com.example.codeplay.kuxing.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.map.MapView;
import com.example.codeplay.kuxing.Fragment.FragmentCommunity;
import com.example.codeplay.kuxing.Fragment.FragmentMap;
import com.example.codeplay.kuxing.Fragment.FragmentMy;
import com.example.codeplay.kuxing.R;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup bottom_bar;
    private FragmentManager fManager;
    private RadioButton rb_map;

    private Fragment fg1;
    private Fragment fg2;
    private Fragment fg3;
    private MapView mMapView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();

        //修改图标大小
        RadioButton radio0 = (RadioButton)findViewById(R.id.radio0);
        Drawable ditu = getResources().getDrawable(R.mipmap.ditu);
        ditu.setBounds(0,0,50,50);
        radio0.setCompoundDrawables(null,ditu,null,null);

        RadioButton radio1 = (RadioButton)findViewById(R.id.radio1);
        Drawable community = getResources().getDrawable(R.mipmap.community);
        community.setBounds(0,0,50,50);
        radio1.setCompoundDrawables(null,community,null,null);

        RadioButton radio2 = (RadioButton)findViewById(R.id.radio2);
        Drawable aixin = getResources().getDrawable(R.mipmap.aixin);
        aixin.setBounds(0,0,50,50);
        radio2.setCompoundDrawables(null,aixin,null,null);

        bottom_bar = (RadioGroup) findViewById(R.id.bottom_bar);
        bottom_bar.setOnCheckedChangeListener(this);
        rb_map = (RadioButton)findViewById(R.id.radio0);
        rb_map.setChecked(true);
        //获取地图控件引用
        mMapView = (MapView)findViewById(R.id.bmapView);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                break;
            case R.id.radio1:
                if(fg2 == null){
                    fg2 = new FragmentCommunity();
                    fTransaction.add(R.id.layout_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.radio2:
                if(fg3 == null){
                    fg3 = new FragmentMy();
                    fTransaction.add(R.id.layout_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            default:
                break;
        }
        fTransaction.commit();
    }
}
