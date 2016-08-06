package com.lixinxin.love;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lixinxin.love.service.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showView(View v) {
        startService(new Intent(this, MyService.class));
        finish();
    }

    public void removeView(View v) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("key", 1);
        startService(intent);
        finish();
    }


}
