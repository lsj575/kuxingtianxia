package com.example.codeplay.kuxing.Activity;


import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.codeplay.kuxing.R;

public class InsertDetailActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        //修改图标大小
        RadioButton add_picture = (RadioButton)findViewById(R.id.add_picture);
        Drawable fenxiang = getResources().getDrawable(R.mipmap.fenxiang);
        fenxiang.setBounds(0,0,50,50);
        add_picture.setCompoundDrawables(null,fenxiang,null,null);

        TextView textView = null;
        textView = (TextView) findViewById(R.id.add_update);
        textView.setText("新建记事");

        radioGroup = (RadioGroup) findViewById(R.id.bottom_insert);
        radioGroup.setOnCheckedChangeListener(this);

        button = findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成响应函数
            }
        });
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
            case R.id.add_picture:
                //添加图片响应函数
                break;
            default:
                break;
        }
    }
}