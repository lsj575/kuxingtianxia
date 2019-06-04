package com.example.codeplay.kuxing.Activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.codeplay.kuxing.Adapter.PictureAdapter;
import com.example.codeplay.kuxing.R;

import java.util.ArrayList;

public class InsertDetailActivity extends AppCompatActivity {

    private Button button;
    private GridView gridView;
    private PictureAdapter pictureAdapter;
    private Context mContext;
    private ArrayList<Bitmap> mdata = new ArrayList<Bitmap>();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        mContext = InsertDetailActivity.this;
        gridView = findViewById(R.id.pictures);

        //修改图标大小
        TextView add_picture = (TextView) findViewById(R.id.add_picture);
        Drawable fenxiang = getResources().getDrawable(R.mipmap.fenxiang);
        fenxiang.setBounds(0,0,50,50);
        add_picture.setCompoundDrawables(null,fenxiang,null,null);

        TextView textView = null;
        textView = (TextView) findViewById(R.id.add_update);
        textView.setText("新建记事");

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入图片响应函数
                goXiangChe();
            }
        });

        button = findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成响应函数

            }
        });
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDetailActivity.this.finish();
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

    /*调用相册*/
    protected void goXiangChe() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 111);
    }
    //得到结果
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = getContentResolver();
            try {
                //从相册获得图片
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                Log.i("xxx",bitmap.toString());
                if (uri ==null){
                    uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                }
                //图片太大时，进行缩放
                Matrix matrix = new Matrix();
                matrix.setScale(0.7f, 0.7f);
                bitmap = Bitmap.createBitmap( bitmap, 0, 0,  bitmap.getWidth(), bitmap.getHeight(), matrix, false);

                mdata.add(bitmap);
                pictureAdapter = new PictureAdapter(mdata,mContext);
                gridView.setAdapter(pictureAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
