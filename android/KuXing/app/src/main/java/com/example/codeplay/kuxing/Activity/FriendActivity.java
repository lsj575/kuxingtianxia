package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.codeplay.kuxing.Adapter.UserComAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {
    private UserComAdapter userComAdapter;
    private ArrayList<Event> gData = null;
    private Context mContext;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mContext = FriendActivity.this;
        listView = (ListView) findViewById(R.id.user_community);
        gData = new ArrayList<Event>();
        gData.add(new Event());
        gData.add(new Event());
        gData.add(new Event());
        userComAdapter = new UserComAdapter(gData,mContext);
        listView.setAdapter(userComAdapter);
        //设置列表不可见
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setVisibility(View.INVISIBLE);
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
