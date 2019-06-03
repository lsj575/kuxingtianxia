package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.R;

import org.w3c.dom.Text;

public class FragmentEventList extends Fragment {

    private android.app.FragmentManager fm;
    private Context mContext;
    private ExpandableListView expandableListView;
    private ImageView btn_close;
    private RadioGroup buttom_bar;
    private DisplayMetrics displayMetrics;
    private String[] group_name = { "test" };
    private String[][] items = { { "寒冰射手", "冰晶凤凰", "符文法师", "诺克萨斯之手", "魔蛇之拥", "狂暴之心", "战争之王", "德邦总管", "诺克萨斯统领", "正义巨像", "德玛西亚皇子", "无双剑姬", "放逐之刃", "唤潮鲛姬" } };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_eventlist, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fm = getActivity().getFragmentManager();
        mContext = getActivity();
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        expandableListView = (ExpandableListView) getActivity().findViewById(R.id.eventlist);
        expandableListView.setAdapter(new EventlistAdapter(group_name, items, mContext, displayMetrics.widthPixels));
        btn_close = (ImageView) getActivity().findViewById(R.id.eventlist_close);
        buttom_bar = (RadioGroup) getActivity().findViewById(R.id.bottom_bar);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttom_bar.setVisibility(View.VISIBLE);
                fm.beginTransaction().replace(R.id.layout_content, new FragmentMap()).commit();
            }
        });
    }

    class EventlistAdapter extends BaseExpandableListAdapter {

        private String[] gData;
        private String[][] iData;
        private Context mContext;
        private int screenWidth;

        public EventlistAdapter(String[] gData, String[][] iData, Context mContext, int screenWidth) {
            this.gData = gData;
            this.iData = iData;
            this.mContext = mContext;
            this.screenWidth = screenWidth;
        }

        @Override
        public int getGroupCount() {
            return gData.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return iData[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return gData[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return iData[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
            ViewHolderGroup groupHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.eventlist_group, viewGroup, false);
                groupHolder = new ViewHolderGroup();
                groupHolder.group_name = (TextView) convertView.findViewById(R.id.eventlist_group_name);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (ViewHolderGroup) convertView.getTag();
            }
            groupHolder.group_name.setText(gData[i]);
            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
            ViewHolderItem itemHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.eventlist_item, viewGroup, false);
                itemHolder = new ViewHolderItem();
                itemHolder.hsv = (HorizontalScrollView) convertView.findViewById(R.id.eventlist_hsv);
                itemHolder.item = (TextView) convertView.findViewById(R.id.item_text);
                itemHolder.content = (LinearLayout) convertView.findViewById(R.id.eventlist_item_content);
                itemHolder.ops = (LinearLayout) convertView.findViewById(R.id.event_list_ops);
                itemHolder.mark = (Button) convertView.findViewById(R.id.eventlist_item_mark);
                itemHolder.delete = (ImageView) convertView.findViewById(R.id.eventlist_item_delete);
                ViewGroup.LayoutParams layoutParams = itemHolder.content.getLayoutParams();
                layoutParams.width = screenWidth;
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ViewHolderItem) convertView.getTag();
            }
            itemHolder.item.setText(iData[i][i1]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    static class ViewHolderGroup {
        TextView group_name;
    }

    static class ViewHolderItem {
        HorizontalScrollView hsv;
        View content;
        TextView item;
        View ops;
        Button mark;
        ImageView delete;
    }
}
