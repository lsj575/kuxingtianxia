package com.example.codeplay.kuxing.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.codeplay.kuxing.R;

public class PictureFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture, container,false);
        ImageView imageView = view.findViewById(R.id.photo);
        return view;
    }
}
