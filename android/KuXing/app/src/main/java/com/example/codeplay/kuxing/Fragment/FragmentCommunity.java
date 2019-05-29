package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.codeplay.kuxing.R;


public class FragmentCommunity extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_community,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {
    }
}