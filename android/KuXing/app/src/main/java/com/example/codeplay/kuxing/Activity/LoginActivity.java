package com.example.codeplay.kuxing.Activity;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.codeplay.kuxing.DataOfServer;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.NormalPostRequest;
import com.example.codeplay.kuxing.VolleyCallBack;
import com.example.codeplay.kuxing.util.DatabaseHelper;
import com.example.codeplay.kuxing.util.SQLiteDAOImpl;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ImageView Imageback;
    private TextView Textback;


    EditText etName, etPwd;
    Button bnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_login);
        Imageback =  findViewById(R.id.fanhui);
        Imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        Textback = findViewById(R.id.fh);
        Textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

        etName = (EditText) findViewById(R.id.uNameEditText);
        etPwd = (EditText) findViewById(R.id.uPwdEditText);
        bnLogin = (Button)findViewById(R.id.loginButton);


        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行输入校验
                if(validate()){
                    //登录校验
                    String url = "http://120.79.159.186:8080/user/signin";
                    String uName = etName.getText().toString();
                    String uPwd = etPwd.getText().toString();
                    try{
                        uName = URLEncoder.encode(uName,"UTF-8"); // 中文数据需要经过URL编码
                        uPwd = URLEncoder.encode(uPwd,"UTF-8");

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username",uName);
                    params.put("password",uPwd);
                    DataOfServer dataOfServer = new DataOfServer(getApplicationContext(), url);

                    dataOfServer.setVolleyCallBack(new VolleyCallBack() {
                        @Override
                        public void getJsonFromVolley(JSONObject js) {
                            try{
                                String s = js.get("msg").toString();
                                System.out.println("测试了" + s);
                                if(s.equals("OK")){
                                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新输入！",Toast.LENGTH_SHORT).show();

                                }
                                /*
                                String token = js.get("Token").toString();  //然后存入数据库
                                String name = js.get("Username").toString();
                                */

                                //tv.setText(s + "");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    });
                    dataOfServer.JsonFromAndPutNet(params);

                }

            }
        });
    }

    // 对用户输入的用户名、密码进行校验
    private boolean validate()
    {
        String uName = etName.getText().toString().trim();
        if (uName.equals(""))
        {
            Toast.makeText(this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        String uPwd = etPwd.getText().toString().trim();
        if (uPwd.equals(""))
        {
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

}
