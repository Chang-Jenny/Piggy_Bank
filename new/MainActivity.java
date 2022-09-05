package com.example.piggybank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                implements View.OnClickListener{

    private EditText useraccount,userpassword;
    private Button register,signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        useraccount=(EditText)findViewById(R.id.useraccount);
        userpassword=(EditText)findViewById(R.id.userpassword);
        signin=(Button)findViewById(R.id.signin);
        register=(Button)findViewById(R.id.register);

        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        //新增SharedPreferences
        SharedPreferences sharedPreferences =getSharedPreferences("data",MODE_PRIVATE);
        //讓sharedPreferences處於編輯狀態
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //存入資料
        editor.putString("id","110816002");
        editor.putString("password","110816002");
        editor.apply();

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.signin){
            String userid=useraccount.getText().toString();
            String userpasswd=userpassword.getText().toString();
            //取得sharedpreference
            SharedPreferences preference=getSharedPreferences("data",MODE_PRIVATE);
            //判斷登入畫面輸入的帳號密碼是否跟註冊的帳號密碼一樣
            if (userid.equals(preference.getString("id",""))
                    &&userpasswd.equals(preference.getString("password","")) ){
                //是的話顯示成功登入
                Toast.makeText(MainActivity.this,"成功登入",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(this, Home.class);
                startActivity(it);
                finish();
            } else{
                //不是則顯示登入失敗
                Toast.makeText(MainActivity.this,"登入失敗",Toast.LENGTH_SHORT).show();
            }
        }
        if(view.getId()==R.id.register){
            Toast.makeText(MainActivity.this,"不支援此功能/請輸入110816002",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("請直接登入帳號密碼110816002\n" +
                    "註冊功能尚未開放使用!");
            builder.setTitle("Welcome~");
            builder.setIcon(R.drawable.money);
            builder.setCancelable(true);
            builder.show();
        }
    }
}