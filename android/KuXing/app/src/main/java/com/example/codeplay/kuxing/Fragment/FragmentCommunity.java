package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Adapter.CommunityAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.NormalPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentCommunity extends Fragment {
    private ArrayList<String> gData = null;
    private ArrayList<ArrayList<Event>> iData = null;
    private ArrayList<Event> lData = null;
    private Context mContext;
    private ExpandableListView community;
    private CommunityAdapter myAdapter = null;
    private Map<String, String> data;
    private ArrayList<Map<String, String>> group = new ArrayList<Map<String, String>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_community,container,false);
        mContext = getActivity();
        community = view.findViewById(R.id.community);
        //读取数据库内容
        data = new HashMap<>();
        data.put("username", "codeplay");
        data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/fan/note",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("data");
                            for (int i = 0; i < res.length(); i++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("Id", res.getJSONObject(i).getString("Id"));
                                map.put("Username", res.getJSONObject(i).getString("Username"));
                                map.put("Title", res.getJSONObject(i).getString("Title"));
                                map.put("Content", res.getJSONObject(i).getString("Content"));
                                map.put("Img", res.getJSONObject(i).getString("Img"));
                                map.put("Latitude", res.getJSONObject(i).getString("Latitude"));
                                map.put("Longitude", res.getJSONObject(i).getString("Longitude"));
                                map.put("Location", res.getJSONObject(i).getString("Location"));
                                map.put("CreateTime", res.getJSONObject(i).getString("CreateTime"));
                                group.add(map);
                            }
                            gData = new ArrayList<String>();
                            iData = new ArrayList<ArrayList<Event>>();
                            lData = new ArrayList<Event>();
                            gData.add(new String("好友"));
                            gData.add(new String("推荐"));
                            for(int i=0;i<group.size();i++){
                                //String filePath = "c:/01.jpg"+group.get(i).get("Img");
                                //Bitmap bitmap=BitmapFactory.decodeFile(filePath);
                                Event event = new Event(group.get(i).get("Username"),
                                        group.get(i).get("Title"),
                                        group.get(i).get("Content"),
                                        Double.valueOf(group.get(i).get("Latitude")),
                                        Double.valueOf(group.get(i).get("Longitude")),
                                        group.get(i).get("Location"),
                                        new Date(new Long(group.get(i).get("CreateTime"))),
                                        null);
                                Log.i("bbb", group.get(i).get("Img"));
                                lData.add(event);
                            }
                            iData.add(lData);
                            lData = new ArrayList<Event>();
                            lData.add(new Event("Jake","记事标题","今天去了街道口", 127, 127, "街道口",new Date(),null));
                            iData.add(lData);

                            myAdapter = new CommunityAdapter(gData,iData,mContext);
                            community.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("bbb", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("bbb", error.getMessage(), error);
            }
        }, data);
        requestQueue.add(request);

        return view;
    }

}