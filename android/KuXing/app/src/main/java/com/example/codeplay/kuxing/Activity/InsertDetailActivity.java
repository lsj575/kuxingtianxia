package com.example.codeplay.kuxing.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.Adapter.PictureAdapter;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.util.NormalPostRequest;

import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InsertDetailActivity extends AppCompatActivity {

    private Button button;
    private GridView gridView;
    private PictureAdapter pictureAdapter;
    private Context mContext;
    private ArrayList<Bitmap> mdata = new ArrayList<Bitmap>();
    private Bitmap bitmap;
    private ProgressDialog mDialog ;
    private EditText title;
    private EditText content;
    private TextView place;
    private TextView time;
    private double latitude;
    private double longitude;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        mContext = InsertDetailActivity.this;
        gridView = findViewById(R.id.pictures);
        title = findViewById(R.id.biaoti);
        content = findViewById(R.id.neirong);
        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        latitude = intent.getDoubleExtra("latitude",0.00);
        longitude = intent.getDoubleExtra("longitude",0.00);

        Log.i("result2",location);
        Log.i("result2",String.valueOf(latitude));

        place = findViewById(R.id.location);
        place.setText(location);
        time = findViewById(R.id.date);
        time.setText(new Date().toLocaleString());

        Log.i("result2",location);

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
                Map<String, String> data = new HashMap<String, String>();
                data.put("username", "codeplay");
                data.put("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
                data.put("title",title.getText().toString());
                data.put("content",content.getText().toString());
                data.put("location",location);
                data.put("latitude",String.valueOf(latitude));
                data.put("longitude",String.valueOf(longitude));
                data.put("img","");
                data.put("isOpen","1");
                RequestQueue requestQueue = Volley.newRequestQueue(InsertDetailActivity.this);
                Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/note/upload",
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
                InsertDetailActivity.this.finish();
            }
        });
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDetailActivity.this.finish();
            }
        });

        Log.i("result2",location);

        mDialog = new ProgressDialog(this) ;
        mDialog.setCanceledOnTouchOutside(false);

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
            Log.i("zzz",uri.toString());
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

                //图片上传到线上服务器

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 上传图片接口
     */
    /*public void uploadImg(Bitmap bitmap,Uri uri){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String,String> map = new HashMap<>();
        map.put("username","codeplay");
        map.put("token","44c42b0bc9a88d630c0574367dc56d525cf5d161");
        map.put("file",bitmap.toString());
        Request<JSONObject> request = new NormalPostRequest("http://120.79.159.186:8080/img/upload",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("zzz", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zzz", error.getMessage(), error);
            }
        }, map);
        Log.e("zzz", request.toString());
        requestQueue.add(request);
    }*/


    /*public void upload(final Uri uri) {
        new Thread(){
            private String getImagePath(Uri uri, String selection) {
                String path = null;
                Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();
                }
                return path;
            }
            @Override
            public void run(){
                String path="";
                String end = "\r\n";
                String twoHyphens = "--";
                String boundary = "******";
                URL url;
                try {
                    //根据URI获取文件路径
                    String imagePath = null;
                    if (DocumentsContract.isDocumentUri(InsertDetailActivity.this, uri)) {
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            //Log.d(TAG, uri.toString());
                            String id = docId.split(":")[1];
                            String selection = MediaStore.Images.Media._ID + "=" + id;
                            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            //Log.d(TAG, uri.toString());
                            Uri contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"),
                                    Long.valueOf(docId));
                            imagePath = getImagePath(contentUri, null);
                        }
                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                        //Log.d(TAG, "content: " + uri.toString());
                        imagePath = getImagePath(uri, null);
                    }

                    url = new URL("http://120.79.159.186:8080/img/upload");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
                    // 允许输入输出流
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                    // 使用POST方法
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    //将id（用户id格式     user:+id  社团id格式     org:+id）传到服务器
                    httpURLConnection.setRequestProperty("username", "codeplay");
                    httpURLConnection.setRequestProperty("token", "44c42b0bc9a88d630c0574367dc56d525cf5d161");
                    DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + end);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\"" +
                            imagePath.substring(imagePath.lastIndexOf("/") + 1) + "\"" + end);
                    dos.writeBytes(end);
                    FileInputStream fis = new FileInputStream(imagePath);
                    Log.i("aaa",imagePath);
                    byte[] buffer = new byte[8192]; // 8
                    int count = 0;
                    // 读取文件
                    while ((count = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, count);
                    }
                    fis.close();
                    dos.writeBytes(end);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
                    dos.flush();
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String result = br.readLine();
                    Log.i("tag", result);
                    dos.close();
                    is.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }.start();

    }*/
    /*private void sendImage(Bitmap bm)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("file", img);
        params.add("username","codeplay");
        params.add("token","44c42b0bc9a88d630c0574367dc56d525cf5d161");
        client.post("http://120.79.159.186:8080/img/upload", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(InsertDetailActivity.this, "Upload Success!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(InsertDetailActivity.this, "Upload Fail!", Toast.LENGTH_LONG).show();
            }
        });
    }*/

}
