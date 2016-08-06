package com.lixinxin.love.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.lixinxin.love.view.FloatCircleView;

/**
 * Created by lixinxin on 2016/8/6.
 */
public class FloatViewManager {

    private WindowManager wm;
    private Context context;
    private static FloatViewManager instance;
    private FloatCircleView circleView;
    private WindowManager.LayoutParams params;


    private View.OnTouchListener circleViewTouchListener = new View.OnTouchListener() {

        private float startX;
        private float startY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getRawX();
                    startY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getRawX();
                    float y = event.getRawY();
                    float dx = x - startX;
                    float dy = y - startY;
                    params.x += dx;
                    params.y += dy;
                    wm.updateViewLayout(circleView, params);
                    startX = x;
                    startY = y;
                    break;

                case MotionEvent.ACTION_UP:

                    float x1 = event.getRawX();
                    float y1 = event.getRawY();

                    if (x1 > getScreenWidth() / 2) {
                        params.x = getScreenWidth();
                        params.y = (int) y1;
                        wm.updateViewLayout(circleView, params);
                    } else {
                        params.x = 0;
                        params.y = (int) y1;
                        wm.updateViewLayout(circleView, params);
                    }
                    break;
            }

            return false;
        }
    };


    public int getScreenWidth() {

        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }


    private FloatViewManager(Context context) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        circleView = new FloatCircleView(context);
        circleView.setOnTouchListener(circleViewTouchListener);
    }

    public static FloatViewManager getInstance(Context context) {
        if (instance == null) {
            synchronized (FloatViewManager.class) {
                if (instance == null) {
                    instance = new FloatViewManager(context);
                }
            }
        }
        return instance;
    }

    //展示浮动的小球
    public void showFloatView() {

        params = new WindowManager.LayoutParams();
        params.width = circleView.width;
        params.height = circleView.height;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.RGBA_8888;
        params.x = 0;
        params.y = 0;
        wm.addView(circleView, params);

    }

}
