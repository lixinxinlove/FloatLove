package com.lixinxin.love.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lixinxin.love.engine.FloatViewManager;

public class MyService extends Service {
    FloatViewManager manager;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = FloatViewManager.getInstance(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra("key", 0) == 1) {
            manager.removeView();
        } else if(intent.getIntExtra("key", 0) == 0){
            manager.showFloatView();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
