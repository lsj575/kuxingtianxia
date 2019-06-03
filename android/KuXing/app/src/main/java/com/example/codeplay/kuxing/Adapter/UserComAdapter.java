package com.example.codeplay.kuxing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class UserComAdapter extends BaseAdapter {
    private ArrayList<Event> gData;
    private Context mContext;

    public UserComAdapter(ArrayList<Event> gData,Context mContext) {
        this.gData = gData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return gData.size();
    }

    @Override
    public Object getItem(int position) {
        return gData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.community_group_item,parent,false);
        GridView gridView;
        PictureAdapter pictureAdapter;
        ArrayList<Integer> mdata;
        gridView = convertView.findViewById(R.id.pictures2);
        mdata = new ArrayList<Integer>();
        /**
         * 需要显示的图片
         */
        mdata.add(1);
        mdata.add(1);
        mdata.add(1);
        pictureAdapter = new PictureAdapter(mdata,mContext);
        gridView.setAdapter(pictureAdapter);

        return convertView;
    }
}