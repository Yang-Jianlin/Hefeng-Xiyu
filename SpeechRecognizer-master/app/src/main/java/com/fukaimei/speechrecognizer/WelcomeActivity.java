package com.fukaimei.speechrecognizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class WelcomeActivity extends AppCompatActivity {

    private final int time = 3000;
    private boolean lag = true;

    public boolean isFirstRun;
    SharedPreferences.Editor editor;
    //public static int welcomeiterface=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否第一次使用APP
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        editor = sharedPreferences.edit();

        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
                    finish();
                    Toast.makeText(WelcomeActivity.this , "刷新成功" , Toast.LENGTH_LONG).show();
                    if(isFirstRun) {
                        System.out.println("111111111");
//                        editor.putBoolean("isFirstRun", false);
//                        editor.commit();
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        System.out.println("22222222");
                        Intent intent = new Intent(WelcomeActivity.this, WeatherActivity.class);
                        startActivity(intent);
                    }
                }

            }

        } , time);

        //给按钮添加监听事件，当点击时，直接进入主页面
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(isFirstRun) {
                    System.out.println("111111111");
//                    editor.putBoolean("isFirstRun", false);
//                    editor.commit();
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    System.out.println("22222222");
                    Intent intent = new Intent(WelcomeActivity.this, WeatherActivity.class);
                    startActivity(intent);
                }
                lag = false;
            }
        });
    }
}
