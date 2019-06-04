package com.example.codeplay.kuxing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeplay.kuxing.Activity.FriendActivity;
import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.Fragment.PictureFragment;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends BaseExpandableListAdapter implements ViewPager.OnPageChangeListener{
    private ArrayList<String> gData;
    private ArrayList<ArrayList<Event>> iData;
    private Context mContext;
    private TextView friendName;

    public CommunityAdapter(ArrayList<String> gData, ArrayList<ArrayList<Event>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.community_group, parent, false);
            textView = convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(textView);
        }else{
            textView = (TextView) convertView.getTag();
        }
        textView.setText(gData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GridView gridView;
        PictureAdapter pictureAdapter;
        ArrayList<Bitmap> mdata;
        ImageButton imageButton;
        TextView location;
        TextView content;
        TextView date;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.community_group_item, parent, false);
        friendName = (TextView)convertView.findViewById(R.id.friendname);
        friendName.setText(iData.get(groupPosition).get(childPosition).getUsername());
        location = (TextView)convertView.findViewById(R.id.location1);
        location.setText(iData.get(groupPosition).get(childPosition).getLocation());
        content = (TextView)convertView.findViewById(R.id.neirong2) ;
        content.setText(iData.get(groupPosition).get(childPosition).getContent());
        date = (TextView)convertView.findViewById(R.id.date2) ;
        date.setText(iData.get(groupPosition).get(childPosition).getDate().toLocaleString());
        if(groupPosition == 2){
            gridView = convertView.findViewById(R.id.pictures2);

            mdata = new ArrayList<Bitmap>();
            /**
             * 需要显示的图片
             */
            mdata = iData.get(groupPosition).get(childPosition).getBitmaps();
            pictureAdapter = new PictureAdapter(mdata,mContext);
            gridView.setAdapter(pictureAdapter);
        }else{
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
        }

        imageButton = convertView.findViewById(R.id.touxiang);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 头像点击响应函数
                 */
                Intent intent = new Intent();
                intent.setClass(mContext, FriendActivity.class);
                intent.putExtra("userName",friendName.getText());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
