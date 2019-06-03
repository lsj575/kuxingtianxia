package com.example.codeplay.kuxing.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.codeplay.kuxing.Fragment.FirstFragment;
import com.example.codeplay.kuxing.Fragment.SecondFragment;
import com.example.codeplay.kuxing.Fragment.ThirdFragment;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private final int SDK_PERMISSION_REQUEST = 127;
    private ListView FunctionList;
    private String permissionInfo;

    private ViewPager viewPager = null;
    private LinearLayout indicator = null;
    private PagerAdapter adapter = null;
    private Button button = null;
    private ImageView indicator1, indicator2, indicator3 = null;
    private LauncherActivity.myFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (LinearLayout) findViewById(R.id.indicator);
        indicator1 = (ImageView) findViewById(R.id.indicator1);
        indicator2 = (ImageView) findViewById(R.id.indicator2);
        indicator3 = (ImageView) findViewById(R.id.indicator3);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.launcher);
        relativeLayout.setSystemUiVisibility(View.GONE);

        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        myFragmentPagerAdapter = new myFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(this);

        //初始化显示第一个页面
        viewPager.setCurrentItem(0);
        getPersimmions();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0: {
                indicator1.setImageResource(R.mipmap.circle_select);
                indicator2.setImageResource(R.mipmap.circle_unselect);
                indicator3.setImageResource(R.mipmap.circle_unselect);
                break;
            }
            case 1: {
                indicator1.setImageResource(R.mipmap.circle_unselect);
                indicator2.setImageResource(R.mipmap.circle_select);
                indicator3.setImageResource(R.mipmap.circle_unselect);
                break;
            }
            case 2: {
                indicator1.setImageResource(R.mipmap.circle_unselect);
                indicator2.setImageResource(R.mipmap.circle_unselect);
                indicator3.setImageResource(R.mipmap.circle_select);
                break;
            }
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (SDK_PERMISSION_REQUEST == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            } else {

            }
        }
    }

    AlertDialog mPermissionDialog;
    String mPackName = "com.example.codeplay.kuxing";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class myFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public myFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
