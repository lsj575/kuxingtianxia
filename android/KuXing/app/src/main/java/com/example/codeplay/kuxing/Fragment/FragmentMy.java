package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.codeplay.kuxing.Activity.ChangeInfoActivity;
import com.example.codeplay.kuxing.Activity.FanActivity;
import com.example.codeplay.kuxing.Activity.FrienActivity;
import com.example.codeplay.kuxing.Activity.LoginActivity;
import com.example.codeplay.kuxing.Activity.RegisterActivity;
import com.example.codeplay.kuxing.R;


public class FragmentMy extends Fragment  {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
        View view=inflater.inflate(R.layout.activity_user,container,false);
=======
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
        Button buttonC = view.findViewById(R.id.changinfo);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeInfoActivity.class);
                startActivity(intent);
            }
        });
        Button buttonFan = view.findViewById(R.id.fans);
        buttonFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FanActivity.class);
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
>>>>>>> 8050f8802595dc1a31c35f7c4367c39f8c73cc52
        return view;
    }
}

