package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Activity.DetailActivity;
import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.NormalPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FragmentEventList extends Fragment {

    private android.app.FragmentManager fm;
    private Context mContext;
    private ExpandableListView expandableListView;
    private ImageView btn_close;
    private TextView noevent;
    private RadioGroup bottom_bar;
    private DisplayMetrics displayMetrics;
    private Map<String, String> data;
    private ArrayList<String> groups = new ArrayList<String>();
    private ArrayList<ArrayList<Map<String, String>>> items = new ArrayList<ArrayList<Map<String, String>>>();
//    private String[] group_name = { "test" };
//    private String[][] items = { { "寒冰射手", "冰晶凤凰", "符文法师", "诺克萨斯之手", "魔蛇之拥", "狂暴之心", "战争之王", "德邦总管", "诺克萨斯统领", "正义巨像", "德玛西亚皇子", "无双剑姬", "放逐之刃", "唤潮鲛姬" } };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_eventlist, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        groups = (ArrayList<String>) getArguments().getSerializable("groups");
        items = (ArrayList<ArrayList<Map<String, String>>>) getArguments().getSerializable("items");
        fm = getActivity().getFragmentManager();
        mContext = getActivity();
        noevent = (TextView) getActivity().findViewById(R.id.noevent);
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        expandableListView = (ExpandableListView) getActivity().findViewById(R.id.eventlist);
        if (items.size() == 0) {
            noevent.setVisibility(View.VISIBLE);
        } else {
            noevent.setVisibility(View.GONE);
            expandableListView.setAdapter(new EventlistAdapter(groups, items, mContext, displayMetrics.widthPixels));
        }
        btn_close = (ImageView) getActivity().findViewById(R.id.eventlist_close);
        bottom_bar = (RadioGroup) getActivity().findViewById(R.id.bottom_bar);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.VISIBLE);
                fm.popBackStack();
            }
        });
    }

    class EventlistAdapter extends BaseExpandableListAdapter {

        private ArrayList<String> gData;
        private ArrayList<ArrayList<Map<String, String>>> iData;
        private Context mContext;
        private int screenWidth;

        public EventlistAdapter(ArrayList<String> gData, ArrayList<ArrayList<Map<String, String>>> iData, Context mContext, int screenWidth) {
            this.gData = gData;
            this.iData = iData;
            this.mContext = mContext;
            this.screenWidth = screenWidth;
        }

        @Override
        public int getGroupCount() {
            return gData.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return iData.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return gData.size();
        }

        @Override
        public Object getChild(int i, int i1) {
            return iData.get(i).get(i1);
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
            groupHolder.group_name.setText(gData.get(i));
            return convertView;
        }

        @Override
        public View getChildView(int i, final int i1, boolean b, View convertView, ViewGroup viewGroup) {
            ViewHolderItem itemHolder;
            final int group_index = i;
            final int item_index = i1;
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
                itemHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data = new HashMap<>();
                        data.put("username", "codeplay");
                        data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
                        data.put("id", items.get(group_index).get(item_index).get("Id"));
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/note/delete",
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (response.getString("code") == "0") {
                                                Log.i("delete", "OK");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("httperror", error.getMessage(), error);
                            }
                        }, data);
                        requestQueue.add(request);
                        items.get(group_index).remove(item_index);
                        EventlistAdapter.this.notifyDataSetChanged();
                        Log.i("group index", String.valueOf(group_index));
                        Log.i("item index", String.valueOf(item_index));
                    }
                });
                itemHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("timastamp", items.get(group_index).get(item_index).get("CreateTime"));
                        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
                        Event event = new Event(
                                items.get(group_index).get(item_index).get("Username"),
                                items.get(group_index).get(item_index).get("Title"),
                                items.get(group_index).get(item_index).get("Content"),
                                Double.valueOf(items.get(group_index).get(item_index).get("Latitude")),
                                Double.valueOf(items.get(group_index).get(item_index).get("Longitude")),
                                items.get(group_index).get(item_index).get("Location"),
                                (new Date(new Long(items.get(group_index).get(item_index).get("CreateTime")))),
                                bitmaps);
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("event", event);
                        intent.putExtra("id", items.get(group_index).get(item_index).get("Id"));
                        startActivity(intent);
                    }
                });
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ViewHolderItem) convertView.getTag();
            }
            itemHolder.item.setText(iData.get(i).get(i1).get("Title"));
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
