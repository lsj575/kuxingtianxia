package com.example.codeplay.kuxing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeplay.kuxing.Activity.BeginActivity;
import com.example.codeplay.kuxing.Activity.LoginActivity;
import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.R;

import static android.content.Context.MODE_PRIVATE;

public class ThirdFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_third, container,false);
        Button button = view.findViewById(R.id.accessBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hello!首次登陆！", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), BeginActivity.class);
                startActivity(intent);
            }
    });
        return view;
    }
}
