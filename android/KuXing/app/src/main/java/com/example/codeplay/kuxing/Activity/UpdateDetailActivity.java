package com.example.codeplay.kuxing.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Adapter.PictureAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.NormalPostRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateDetailActivity extends AppCompatActivity {

    private Button button;
    private EditText title;
    private EditText content;
    private Context mContext;
    private PictureAdapter pictureAdapter;
    private ArrayList<Bitmap> mdata = new ArrayList<Bitmap>();
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        id = intent.getStringExtra("id");

        title = (EditText) findViewById(R.id.biaoti);
        title.setText(event.getTitle());
        content = (EditText) findViewById(R.id.neirong);
        content.setText(event.getContent());
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(event.getDate().toLocaleString());
        TextView location = (TextView)findViewById(R.id.location);
        location.setText(event.getLocation());

        //修改图标大小
        TextView add_picture = (TextView) findViewById(R.id.add_picture);
        Drawable fenxiang = getResources().getDrawable(R.mipmap.fenxiang);
        fenxiang.setBounds(0,0,50,50);
        add_picture.setCompoundDrawables(null,fenxiang,null,null);

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入图片响应函数
            }
        });

        button =(Button) findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成响应函数
                Map<String, String> data = new HashMap<String, String>();
                data.put("username", "codeplay");
                data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
                data.put("title",title.getText().toString());
                data.put("content",content.getText().toString());
                data.put("img","");
                data.put("isOpen","1");
                data.put("id",id);
                RequestQueue requestQueue = Volley.newRequestQueue(UpdateDetailActivity.this);
                Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/note/edit",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("httpresult", "response -> " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("httpresult", error.getMessage(), error);
                    }
                }, data);
                requestQueue.add(request);
                UpdateDetailActivity.this.finish();
            }
        });
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDetailActivity.this.finish();
            }
        });
        mContext = UpdateDetailActivity.this;

        GridView icon_gridview = (GridView)findViewById(R.id.pictures);
        /**
         * 需要显示的图片
         */
        mdata = event.getBitmaps();
        pictureAdapter = new PictureAdapter(mdata,mContext);
        icon_gridview.setAdapter(pictureAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
