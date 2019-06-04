package com.example.codeplay.kuxing;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class DataOfServer {
    private Context context;
    private String url;
    private RequestQueue mQueue;
    //接口回调步骤2：定义接口成员变量
    private VolleyCallBack volleyCallBack;
    //接口回调步骤3：实例化接口
    public void setVolleyCallBack(VolleyCallBack volleyCallBack) {
        this.volleyCallBack = volleyCallBack;
    }

    public DataOfServer(Context context, String url){
        this.context = context;
        this.url = url;
        mQueue = Volley.newRequestQueue(context);
    }
    public DataOfServer(View view,String url){
        this(view.getContext(),url);
    }
    public void JsonFromAndPutNet(final Map<String, String> map) {
        Request<JSONObject> request = new NormalPostRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject js) {
                if (volleyCallBack !=null){
                    volleyCallBack.getJsonFromVolley(js);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //打印错误信息
                Log.e("TAG", volleyError.getMessage(), volleyError);
            }
        },map);

        mQueue.add(request);
    }
}
