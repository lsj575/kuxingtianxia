package com.example.codeplay.kuxing.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.codeplay.kuxing.Adapter.PictureAdapter;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private EditText title;
    private EditText content;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    private PictureAdapter pictureAdapter;
    private ArrayList<Integer> mdata = new ArrayList<Integer>();           //图片存放，将Integer改为图片类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //设置文本不可编辑，并不可打开键盘
        title = (EditText) findViewById(R.id.biaoti);
        title.setFocusable(false);
        title.setFocusableInTouchMode(false);
        content = (EditText) findViewById(R.id.neirong);
        content.setFocusable(false);
        content.setFocusableInTouchMode(false);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,UpdateDetailActivity.class);
                startActivity(intent);
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,UpdateDetailActivity.class);
                startActivity(intent);
            }
        });


        //修改图标大小
        RadioButton share = (RadioButton)findViewById(R.id.share);
        Drawable fenxiang = getResources().getDrawable(R.mipmap.fenxiang);
        fenxiang.setBounds(0,0,50,50);
        share.setCompoundDrawables(null,fenxiang,null,null);

        RadioButton delete = (RadioButton)findViewById(R.id.delete);
        Drawable shanchu = getResources().getDrawable(R.mipmap.shanchu);
        shanchu.setBounds(0,0,50,50);
        delete.setCompoundDrawables(null,shanchu,null,null);

        radioGroup = (RadioGroup) findViewById(R.id.bottom_detail);
        radioGroup.setOnCheckedChangeListener(this);

        mContext = DetailActivity.this;

        GridView icon_gridview = (GridView)findViewById(R.id.pictures);
        /**
         * 需要显示的图片
         */
        mdata.add(1);
        mdata.add(1);
        mdata.add(1);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.share:
                //响应函数
                break;
            case R.id.delete:
                //响应函数
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setTitle("提示")
                        .setMessage("确定删除吗？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 *
                                 */
                                Toast.makeText(mContext, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 *
                                 */
                                Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alert.show();
                break;
            default:
                break;
        }
    }
}
