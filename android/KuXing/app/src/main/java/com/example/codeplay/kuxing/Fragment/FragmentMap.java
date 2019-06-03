package com.example.codeplay.kuxing.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.example.codeplay.kuxing.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_map, container, false);
        return view;
    }

    @Override
    public void onStart() {
        initMap();
        bottom_bar = (RadioGroup) getActivity().findViewById(R.id.bottom_bar);
        fm = getActivity().getFragmentManager();
        btn_catalogue = (Button) this.getView().findViewById(R.id.btn_catalogue);
        btn_catalogue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.layout_content, new FragmentEventList()).commit();
            }
        });
        btn_add = (Button) this.getView().findViewById(R.id.btn_add);
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
//                bottomSheetDialog.setContentView(R.layout.place);
//                bottomSheetDialog.show();
//            }
//        });
//        TextView search_text = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        search_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.map_place, new FragmentPlace()).commit();
            }
        });
        search_text = (TextView) this.getView().findViewById(R.id.search_text);
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.GONE);
                fm.beginTransaction().replace(R.id.layout_content, new FragmentSearch()).commit();
            }
        });
        super.onStart();
    }
    @Override
    public void onResume() {
        mMapView.onResume();
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
        mLocationClient.setLocOption(option);
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    public void setMapCenter() {
        LatLng latLng = new LatLng(lat, lon);
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
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
                setMapCenter();
            }
        }
    }

    @Override
    public void onClick(View view) {

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
            FragmentEventList.ViewHolderGroup groupHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.eventlist_group, viewGroup, false);
                groupHolder = new FragmentEventList.ViewHolderGroup();
                groupHolder.group_name = (TextView) convertView.findViewById(R.id.eventlist_group_name);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (FragmentEventList.ViewHolderGroup) convertView.getTag();
            }
            groupHolder.group_name.setText(gData[i]);
            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
            FragmentEventList.ViewHolderItem itemHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.eventlist_item, viewGroup, false);
                itemHolder = new FragmentEventList.ViewHolderItem();
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
                itemHolder = (FragmentEventList.ViewHolderItem) convertView.getTag();
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
