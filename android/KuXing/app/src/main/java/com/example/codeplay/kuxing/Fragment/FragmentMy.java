package com.example.codeplay.kuxing.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.codeplay.kuxing.Activity.FrienActivity;
import com.example.codeplay.kuxing.Activity.LoginActivity;
import com.example.codeplay.kuxing.Activity.RegisterActivity;
import com.example.codeplay.kuxing.Activity.UserActivity;
import com.example.codeplay.kuxing.R;



public class FragmentMy extends Fragment  {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.personal_main,container,false);
        Button buttonL = view.findViewById(R.id.login);
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        Button buttonR = view.findViewById(R.id.register);
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button buttonP = view.findViewById(R.id.zhuye);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
            }
        });

        Button buttonFrien = view.findViewById(R.id.friend);
        buttonFrien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrienActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}


