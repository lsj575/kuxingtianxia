package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Adapter.CommunityAdapter;
import com.example.codeplay.kuxing.Adapter.UserComAdapter;
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

public class FriendActivity extends AppCompatActivity {
    private UserComAdapter userComAdapter;
    private ArrayList<Event> gData = null;
    private Context mContext;
    private ListView listView;
    private Map<String, String> data;
    private Map<String, String> data1;
    private ArrayList<Map<String, String>> group = new ArrayList<Map<String, String>>();
    private TextView signature;
    private ArrayList<Map<String, String>> group1 = new ArrayList<Map<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mContext = FriendActivity.this;
        listView = (ListView) findViewById(R.id.user_community);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        TextView username =findViewById(R.id.userName);
        username.setText(userName);
        signature = findViewById(R.id.signature);
        //设置列表不可见
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.INVISIBLE);
        //读取数据库内容
        data = new HashMap<>();
        data.put("username", "codeplay");
        data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
        data.put("author",userName);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/note/author",
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
                            gData = new ArrayList<Event>();
                            for(int i=0;i<group.size();i++){
                                ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
                                Event event = new Event(group.get(i).get("Username"),
                                        group.get(i).get("Title"),
                                        group.get(i).get("Content"),
                                        Double.valueOf(group.get(i).get("Latitude")),
                                        Double.valueOf(group.get(i).get("Longitude")),
                                        group.get(i).get("Location"),
                                        new Date(new Long(group.get(i).get("CreateTime"))),
                                        bitmaps);
                                gData.add(event);
                            }
                            userComAdapter = new UserComAdapter(gData,mContext);
                            listView.setAdapter(userComAdapter);

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

        data1 = new HashMap<>();
        data1.put("username",userName);
        data1.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
        Request<JSONObject> request1 = new NormalPostRequest("http://120.79.159.186:8080/user/info",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("data");
                            for (int i = 0; i < res.length(); i++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("Id", res.getJSONObject(i).getString("Id"));
                                map.put("Username", res.getJSONObject(i).getString("Username"));
                                map.put("Signature", res.getJSONObject(i).getString("Signature"));
                                map.put("Avatar", res.getJSONObject(i).getString("Avatar"));
                                group1.add(map);
                            }
                            signature.setText(group1.get(0).get("Signature"));

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
        }, data1);
        requestQueue.add(request);
        requestQueue.add(request1);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
