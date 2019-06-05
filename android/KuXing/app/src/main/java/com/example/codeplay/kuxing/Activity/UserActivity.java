package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.codeplay.kuxing.Adapter.UserComAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private UserComAdapter userComAdapter;
    private ArrayList<Event> gData = null;
    private Context mContext;
    private ListView listView;
    private Spinner spinner;
    private ImageView Imageback;
    private SharedPreferences setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Imageback = findViewById(R.id.huitui);
        Imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.this.finish();
                Intent intent;
                intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mContext = UserActivity.this;
        listView = (ListView) findViewById(R.id.user_community);
        gData = new ArrayList<Event>();
        gData.add(new Event());
        gData.add(new Event());
        gData.add(new Event());
        userComAdapter = new UserComAdapter(gData, mContext);
        listView.setAdapter(userComAdapter);
        //设置两个图片不可见
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton5);
        imageButton.setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.addfriend);
        imageView.setVisibility(View.INVISIBLE);
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
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.spingarr,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  Intent intent;
                                                  switch (i) {
                                                      case 1:
                                                          intent = new Intent(UserActivity.this, ChangeInfoActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 2:
                                                          intent = new Intent(UserActivity.this, FrienActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 3:
                                                          intent = new Intent(UserActivity.this, FanActivity.class);
                                                          startActivity(intent);
                                                          break;
                                                      case 4:
                                                          intent = new Intent(UserActivity.this, BeginActivity.class);
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
    }
}
