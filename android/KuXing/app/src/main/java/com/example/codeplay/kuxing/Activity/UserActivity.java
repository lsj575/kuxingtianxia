package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.codeplay.kuxing.Adapter.UserComAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private UserComAdapter userComAdapter;
    private ArrayList<Event> gData = null;
    private Context mContext;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mContext = UserActivity.this;
        listView = (ListView) findViewById(R.id.user_community);
        gData = new ArrayList<Event>();
        gData.add(new Event());
        gData.add(new Event());
        gData.add(new Event());
        userComAdapter = new UserComAdapter(gData,mContext);
        listView.setAdapter(userComAdapter);
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
