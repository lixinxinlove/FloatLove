package com.lixinxin.love.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lixinxin.love.engine.FloatViewManager;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FloatViewManager manager = FloatViewManager.getInstance(this);
        manager.showFloatView();
    }
}
