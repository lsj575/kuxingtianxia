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

import com.example.codeplay.kuxing.Adapter.PictureAdapter;
import com.example.codeplay.kuxing.Entity.Event;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class UpdateDetailActivity extends AppCompatActivity {

    private Button button;
    private EditText title;
    private EditText content;
    private Context mContext;
    private PictureAdapter pictureAdapter;
    private ArrayList<Bitmap> mdata = new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        //设置文本不可编辑，并不可打开键盘
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
                Intent intent = new Intent(UpdateDetailActivity.this,InsertDetailActivity.class);
                startActivity(intent);
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
