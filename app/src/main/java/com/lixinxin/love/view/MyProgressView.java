package com.lixinxin.love.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lixinxin on 2016/8/6.
 */
public class MyProgressView extends View {


    private int width = 200;
    private int height = 200;
    private int max = 100;    //最大进度
    private int progress = 50;     //进度
    private int currentProgress = 0;     //变化的进度
    private int count = 50;
    private boolean isSingleTap = false;

    private Paint circlePaint;  //画圆；
    private Paint progressPaint; //画重叠的部分
    private Paint textPaint; //画重叠的部分

    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Path path = new Path();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c));

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.argb(0xff, 0x4e, 0xc9, 0x63));
        progressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);

        final GestureDetector detector = new GestureDetector(new MyGestureDetectorListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        setClickable(true);

    }

    class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isSingleTap = true;
            currentProgress = progress;
            startSingleTapAnimation();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isSingleTap = false;
            startDoubleTapAnimation();

            return super.onDoubleTap(e);
        }
    }

    //单击的动画
    private void startSingleTapAnimation() {
        handler.postDelayed(singleTapRunnable, 200);
    }


    //双击后的动画
    public void startDoubleTapAnimation() {
        handler.postDelayed(doubleTapRunnable, 50);
    }


    private DoubleTapRunnable doubleTapRunnable = new DoubleTapRunnable();
    private SingleTapRunnable singleTapRunnable = new SingleTapRunnable();

    class SingleTapRunnable implements Runnable {
        @Override
        public void run() {
            count--;
            if (count >= 0) {
                invalidate();

                postDelayed(singleTapRunnable, 200);
               // handler.postDelayed(singleTapRunnable, 200);
            } else {

               // handler.removeCallbacks(singleTapRunnable);
                count = 50;
            }
        }
    }

    class DoubleTapRunnable implements Runnable {
        @Override
        public void run() {
            currentProgress++;
            if (currentProgress <= progress) {
                invalidate();
                postDelayed(doubleTapRunnable, 50);
               // handler.postDelayed(doubleTapRunnable, 50);
            } else {
               // handler.removeCallbacks(doubleTapRunnable);
                currentProgress = 0;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bitmapCanvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);
        path.reset();

        float y = (1 - (float) currentProgress / max) * height;

        path.moveTo(width, y);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, y);

        if (isSingleTap) {
            float d = (float) count / 50 * 10;
            if (count % 2 == 0) {
                for (int i = 0; i < 5; i++) {
                    path.rQuadTo(20, -d, 40, 0);
                    path.rQuadTo(20, d, 40, 0);
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    path.rQuadTo(20, d, 40, 0);
                    path.rQuadTo(20, -d, 40, 0);
                }
            }
        } else {
            float d = (1 - ((float) currentProgress / progress)) * 10;
            for (int i = 0; i < 4; i++) {
                path.rQuadTo(10, -d, 20, 0);
                path.rQuadTo(10, d, 20, 0);
            }
        }


        path.close();
        bitmapCanvas.drawPath(path, progressPaint);

        String text = (int) (((float) currentProgress / max * 100)) + "%";
        float textWidth = textPaint.measureText(text);
        float x1 = width / 2 - textWidth / 2;
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y1 = height / 2 + dy;
        bitmapCanvas.drawText(text, x1, y1, textPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }


}
