package com.example.codeplay.kuxing.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.example.codeplay.kuxing.Activity.InsertDetailActivity;
import com.example.codeplay.kuxing.Activity.LoginActivity;
import com.example.codeplay.kuxing.Activity.MainActivity;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.DatabaseHelper;
import com.example.codeplay.kuxing.util.NormalPostRequest;
import com.example.codeplay.kuxing.util.SQLiteDAOImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentMap extends Fragment implements View.OnClickListener {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;
    private boolean isFirstLocate = true;
    private double lat, lon;
    private Button btn_catalogue, btn_add = null;
    private TextView search_text = null;
    private SearchView searchView = null;
    private RadioGroup bottom_bar = null;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String city, place;
    private ArrayList<String> groups;
    private ArrayList<ArrayList<Map<String, String>>> items;
    private Map<String, String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_map, container, false);
        return view;
    }

    @Override
    public void onStart() {
        initMap();
        groups = new ArrayList<String>();
        items = new ArrayList<ArrayList<Map<String, String>>>();
        data = new HashMap<>();
        groups.add("我的事件");
        data.put("username", "codeplay");
        data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/note/get",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("data");
                            ArrayList<Map<String, String>> group = new ArrayList<Map<String, String>>();
                            for (int i = 0; i < res.length(); i++) {
                                setMaker(Double.valueOf(res.getJSONObject(i).getString("Latitude").toString()), Double.valueOf(res.getJSONObject(i).getString("Longitude").toString()));
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
                            items.add(0, group);
                            Log.d("httpresult", "response -> " + response.getJSONArray("data"));
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
        bottom_bar = (RadioGroup) getActivity().findViewById(R.id.bottom_bar);
        fm = getActivity().getFragmentManager();
        ft = fm.beginTransaction();
        btn_catalogue = (Button) this.getView().findViewById(R.id.btn_catalogue);
        btn_catalogue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.GONE);
                FragmentEventList fragmentEventList = new FragmentEventList();
                Bundle bundle = new Bundle();
                Log.i("xxxxxx", String.valueOf(items.size()));
                bundle.putSerializable("groups", (Serializable) groups);
                bundle.putSerializable("items", (Serializable) items);
                fragmentEventList.setArguments(bundle);
                ft.replace(R.id.layout_content, fragmentEventList);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        btn_add = (Button) this.getView().findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), InsertDetailActivity.class);
                intent.putExtra("location", place);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                startActivity(intent);
            }
        });
        search_text = (TextView) this.getView().findViewById(R.id.search_text);
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.GONE);
                FragmentSearch fragmentSearch = new FragmentSearch();
                fragmentSearch.setTargetFragment(FragmentMap.this, 100);
                Bundle bundle = new Bundle();
                bundle.putString("city", city);
                fragmentSearch.setArguments(bundle);
                ft.replace(R.id.layout_content, fragmentSearch);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        super.onStart();
    }
    @Override
    public void onResume() {
        mMapView.onResume();
        setMapCenter(15);
        super.onResume();
    }
    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public void initMap() {
        mMapView = (MapView) this.getView().findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.showZoomControls(false);
        mLocationClient = new LocationClient(getActivity());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    public void setMapCenter(int zoom) {
        LatLng latLng = new LatLng(lat, lon);
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(zoom)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != getActivity().RESULT_OK) {
            return;
        } else {
            ArrayList<String> s = data.getStringArrayListExtra("coordinate");
            lat = Double.valueOf(s.get(0).toString());
            lon = Double.valueOf(s.get(1).toString());
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLocate) {
                isFirstLocate = false;
                lat = location.getLatitude();
                lon = location.getLongitude();
                city = location.getCity();
                place = location.getAddrStr();
                setMapCenter(18);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void setMaker(Double lat, Double lon) {
        LatLng point = new LatLng(lat, lon);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.marker);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
    }

}
