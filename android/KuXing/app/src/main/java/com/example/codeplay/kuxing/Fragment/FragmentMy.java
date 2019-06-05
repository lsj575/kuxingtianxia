package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.codeplay.kuxing.Activity.BeginActivity;
import com.example.codeplay.kuxing.Activity.ChangeInfoActivity;
import com.example.codeplay.kuxing.Activity.FanActivity;
import com.example.codeplay.kuxing.Activity.FrienActivity;
import com.example.codeplay.kuxing.Activity.UserActivity;
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

public class FragmentMy extends Fragment  {
    private UserComAdapter userComAdapter;
    private ArrayList<Event> gData = null;
    private Context mContext;
    private ListView listView;
    private ImageView Imageback;
    private TextView userName;
    private Map<String, String> data;
    private ArrayList<Map<String, String>> group = new ArrayList<Map<String, String>>();
    private Spinner spinner;
    private SharedPreferences setting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user, container, false);
        Imageback = view.findViewById(R.id.huitui);
        Imageback.setVisibility(View.INVISIBLE);
        listView = (ListView) view.findViewById(R.id.user_community);
        Log.i("ccc","111");
        userName = view.findViewById(R.id.userName);
        userName.setText("codeplay");

        mContext = getActivity();

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(mContext, R.array.spingarr,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  Intent intent;
                                                  switch (i) {
                                                      case 1:
                                                          intent = new Intent(mContext, ChangeInfoActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 2:
                                                          intent = new Intent(mContext, FrienActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 3:
                                                          intent = new Intent(mContext, FanActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 4:
                                                          intent = new Intent(mContext, BeginActivity.class);
                                                          startActivity(intent);
                                                          setting.edit().putBoolean("isFirstRun", true).commit();
                                                          break;
                                                  }
                                              }
                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {
                                              }
                                          }
        );



        //读取数据库内容
        data = new HashMap<>();
        data.put("username", "codeplay");
        data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
        data.put("author","codeplay");
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
                        Log.d("ccc", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ccc", error.getMessage(), error);
            }
        }, data);
        requestQueue.add(request);

        Log.i("ccc","111");
        //设置两个图片不可见
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton5);
        imageButton.setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) view.findViewById(R.id.addfriend);
        imageView.setVisibility(View.INVISIBLE);






        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}




