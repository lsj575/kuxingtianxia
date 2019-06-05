package com.example.codeplay.kuxing.Fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codeplay.kuxing.Activity.BeginActivity;
import com.example.codeplay.kuxing.Activity.UserActivity;
import com.example.codeplay.kuxing.R;



public class FragmentMy extends Fragment  {
    private FragmentManager user;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_topbar, container, false);

        return view;
    }
    @Override
    public void onStart() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), UserActivity.class);
        startActivity(intent);
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}



