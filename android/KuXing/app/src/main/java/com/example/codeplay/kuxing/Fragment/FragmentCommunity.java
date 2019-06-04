package com.example.codeplay.kuxing.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.example.codeplay.kuxing.Adapter.CommunityAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class FragmentCommunity extends Fragment {
    private ArrayList<String> gData = null;
    private ArrayList<ArrayList<Event>> iData = null;
    private ArrayList<Event> lData = null;
    private Context mContext;
    private ExpandableListView community;
    private CommunityAdapter myAdapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_community,container,false);
        mContext = getActivity();
        community = view.findViewById(R.id.community);
        gData = new ArrayList<String>();
        iData = new ArrayList<ArrayList<Event>>();
        gData.add(new String("好友"));
        gData.add(new String("推荐"));

        lData = new ArrayList<Event>();
        lData.add(new Event());
        lData.add(new Event());
        lData.add(new Event());
        iData.add(lData);
        lData = new ArrayList<Event>();
        lData.add(new Event());
        lData.add(new Event());
        lData.add(new Event());
        iData.add(lData);

        myAdapter = new CommunityAdapter(gData,iData,mContext);
        community.setAdapter(myAdapter);

        return view;
    }
}