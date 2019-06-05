package com.example.codeplay.kuxing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeplay.kuxing.DataOfServer;
import com.example.codeplay.kuxing.R;
import com.example.codeplay.kuxing.VolleyCallBack;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ImageView Imageback;
    private TextView Textback;

    EditText etName, etPwd1,etPwd2;
    Button bnLogon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_register);
        Imageback =  findViewById(R.id.fanhui);
        Imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        Textback = findViewById(R.id.fh);
        Textback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });


        etName = (EditText) findViewById(R.id.nameEditText);
        etPwd1 = (EditText) findViewById(R.id.pwd1EditText);
        etPwd2 = (EditText) findViewById(R.id.pwd2EditText);
        bnLogon = (Button)findViewById(R.id.logonButton);
        bnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行输入校验
                if(validate()){
                    //登录校验
                    String url = "http://120.79.159.186:8080/user/signup";
                    String uName = etName.getText().toString();
                    String uPwd = etPwd1.getText().toString();
                    //String uPwd2 = etPwd2.getText().toString();
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
                                if(s.equals("SUCCESS")){
                                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();

                                }

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

    private boolean validate()
    {
        String name = etName.getText().toString().trim();
        if (name.equals(""))
        {
            Toast.makeText(this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        String pwd1 = etPwd1.getText().toString().trim();
        if (pwd1.equals(""))
        {
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        String pwd2 = etPwd2.getText().toString().trim();
        if (pwd2.equals(""))
        {
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();

            return false;
        }
        if (!pwd2.equals(pwd1))
        {
            Toast.makeText(this,"两次密码不同！",Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }
}
