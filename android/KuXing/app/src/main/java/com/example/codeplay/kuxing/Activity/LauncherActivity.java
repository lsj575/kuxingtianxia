package com.example.codeplay.kuxing.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.codeplay.kuxing.Fragment.FirstFragment;
import com.example.codeplay.kuxing.Fragment.SecondFragment;
import com.example.codeplay.kuxing.Fragment.ThirdFragment;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

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

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

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
