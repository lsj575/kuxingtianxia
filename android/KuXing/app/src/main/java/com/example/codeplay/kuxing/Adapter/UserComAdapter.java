package com.example.codeplay.kuxing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

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
        ArrayList<Bitmap> mdata;
        gridView = convertView.findViewById(R.id.pictures2);
        mdata = new ArrayList<Bitmap>();
        /**
         * 需要显示的图片
         */
        mdata.add(null);
        mdata.add(null);
        mdata.add(null);
        pictureAdapter = new PictureAdapter(mdata,mContext);
        gridView.setAdapter(pictureAdapter);
        TextView location;
        TextView content;
        TextView date;
        TextView friendName;
        friendName = (TextView)convertView.findViewById(R.id.friendname);
        friendName.setText(gData.get(position).getUsername());
        location = (TextView)convertView.findViewById(R.id.location1);
        location.setText(gData.get(position).getLocation());
        content = (TextView)convertView.findViewById(R.id.neirong2) ;
        content.setText(gData.get(position).getContent());
        date = (TextView)convertView.findViewById(R.id.date2) ;
        date.setText(gData.get(position).getDate().toLocaleString());

        return convertView;
    }
}
