package com.example.codeplay.kuxing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class PictureAdapter extends BaseAdapter {
    private ArrayList<Bitmap> mdata;
    private Context mcontext;

    public PictureAdapter(){}
    public PictureAdapter(ArrayList<Bitmap> mdata, Context mcontext) {
        this.mdata = mdata;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.picture,parent,false);
        ImageView imageView = convertView.findViewById(R.id.photo);
        if(null != mdata.get(position)){
            //imageView.setImageResource(R.drawable.ic_dashboard_black_24dp);
            imageView.setImageBitmap(mdata.get(position));
        }
        return convertView;
    }
}
