package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentSearch extends Fragment {

    private FragmentManager fm;
    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;
    private Button btn_search;
    private SearchView searchView;
    private ImageView search_back;
    private RadioGroup bottom_bar;
    private ListView listView;
    private DisplayMetrics displayMetrics;
    private Context mContext;
    private ArrayList<Map<String, String>> data;
    private Map map;
    private String city;
    private List<SuggestionResult.SuggestionInfo> suglist;
    private TextView search_result;
    private PlacelistAdapter placelistAdapter;
    private ArrayList<String> coordinate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);
        return view;
    }

    @Override
    public void onStart() {
        suggestionSearch = SuggestionSearch.newInstance();
        poiSearch = PoiSearch.newInstance();
        searchView = (SearchView) getActivity().findViewById(R.id.map_search);
        btn_search = (Button) getActivity().findViewById(R.id.btn_search);
        search_back = (ImageView) getActivity().findViewById(R.id.search_back);
        bottom_bar = (RadioGroup) getActivity().findViewById(R.id.bottom_bar);
        listView = (ListView) getActivity().findViewById(R.id.placelist);
        search_result = (TextView) getActivity().findViewById(R.id.search_result);
        mContext = getActivity();
        fm = getActivity().getFragmentManager();
        city = getArguments().getString("city");

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final EditText textView = (EditText) searchView.findViewById(id);
        textView.setTextSize(16);

        data = new ArrayList<Map<String, String>>();
        coordinate = new ArrayList<String>();
        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        placelistAdapter = new PlacelistAdapter(data, mContext, displayMetrics.widthPixels);
        listView.setAdapter(placelistAdapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(city)
                        .keyword(String.valueOf(textView.getText())));
//                poiSearch.searchInCity(new PoiCitySearchOption()
//                        .city("武汉") //必填
//                        .keyword(String.valueOf(textView.getText())) //必填
//                        .pageNum(0)
//                        .scope(2)
//                        .tag("美食"));
//                Log.i("searchtext", String.valueOf(textView.getText()));
            }
        });

        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_bar.setVisibility(View.VISIBLE);
                fm.popBackStack();
            }
        });

        OnGetSuggestionResultListener sugSearchlistener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                suglist = suggestionResult.getAllSuggestions();
                int length = suglist.size();
                int i = 0;
                if (length == 0) {
                    search_result.setVisibility(View.VISIBLE);
                } else {
                    search_result.setVisibility(View.GONE);
                    do {
                        map = new HashMap();
                        map.put("name", suglist.get(i).key);
                        map.put("city", suglist.get(i).city);
                        map.put("lat", suglist.get(i).pt.latitude);
                        map.put("lon", suglist.get(i).pt.longitude);
                        map.put("uid", suglist.get(i).uid);
                        data.add(map);
                        i = i + 1;
                    } while (i < suglist.size());
                    placelistAdapter.notifyDataSetChanged();
                }
//                poi.setText(String.valueOf(suggestionInfo));
            }
        };

        OnGetPoiSearchResultListener poiSearchlistener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.getAllPoi().size() == 0) {
                    Log.i("poiresult", "no result");
                } else {
//                    PoiInfo poiInfo = poiResult.getAllPoi().get(0);
//                    Log.i("result", String.valueOf(poiInfo));
//                    poi.setText(String.valueOf((poiInfo)));
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        suggestionSearch.setOnGetSuggestionResultListener(sugSearchlistener);
        poiSearch.setOnGetPoiSearchResultListener(poiSearchlistener);

        super.onStart();
    }

    @Override
    public void onDestroy() {
        suggestionSearch.destroy();
        poiSearch.destroy();
        super.onDestroy();
    }

    class PlacelistAdapter extends BaseAdapter {

        private Context mContext;
        private int screenWidth;
        private ArrayList<Map<String, String>> data;
        private LayoutInflater mInflater;

        public PlacelistAdapter(ArrayList<Map<String, String>> data, Context mContext, int screenWidth){
            this.data = data;
            this.mContext = mContext;
            this.screenWidth = screenWidth;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.placelist_item, null);
                holder.place_text = (TextView) convertView.findViewById(R.id.place_text);
                holder.arrow = (ImageView) convertView.findViewById(R.id.place_go);
                holder.placelist_item_mark = (Button) convertView.findViewById(R.id.placelist_item_mark);
                holder.content = (LinearLayout) convertView.findViewById(R.id.placelist_item_content);
                ViewGroup.LayoutParams layoutParams = holder.content.getLayoutParams();
                layoutParams.width = screenWidth;
                holder.arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        coordinate.add(String.valueOf(data.get(position).get("lat")));
                        coordinate.add(String.valueOf(data.get(position).get("lon")));
                        sendResult(getActivity().RESULT_OK, coordinate);
                        fm.popBackStack();
                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.place_text.setText(data.get(position).get("name"));
            return convertView;
        }

        final class ViewHolder{
            TextView place_text;
            ImageView arrow;
            Button placelist_item_mark;
            LinearLayout content;
        }
    }

    private void sendResult(int resultOk, ArrayList<String> coordinate) {
        if (getTargetFragment() == null) {
            return;
        } else {
            Intent i = new Intent();
            i.putStringArrayListExtra("coordinate", coordinate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, i);
        }
    }

}
