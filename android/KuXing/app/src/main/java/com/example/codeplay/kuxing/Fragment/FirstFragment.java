package com.example.codeplay.kuxing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeplay.kuxing.Activity.BeginActivity;
import com.example.codeplay.kuxing.Activity.LoginActivity;
import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.R;

public class FirstFragment extends Fragment {
    private final String SHARE_APP_TAG = "firstOpen";
    private Boolean first;
    private SharedPreferences setting;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting = getActivity().getSharedPreferences(SHARE_APP_TAG, 0);
        first = setting.getBoolean("FIRST", true);
        if (first) {
            setting.edit().putBoolean("FIRST", false).commit();
            Toast.makeText(getActivity(), "Hello! Welcome!！", Toast.LENGTH_LONG).show();
            View view = inflater.inflate(R.layout.fragment_first, container, false);
            return view;
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share",
                    Context.MODE_PRIVATE);
            boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isFirstRun) {
                    Toast.makeText(getActivity(), "Hello!请登陆！", Toast.LENGTH_LONG).show();
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), BeginActivity.class);
                    startActivity(intent);
                    View view = inflater.inflate(R.layout.personal_main, container, false);
                    return view;
                } else {
                    Toast.makeText(getActivity(), "Welcome Again！", Toast.LENGTH_LONG).show();
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainActivity.class);
                    startActivity(intent);
                    View view = inflater.inflate(R.layout.frag_map, container, false);
                    return view;
                }
            }
    }
}


