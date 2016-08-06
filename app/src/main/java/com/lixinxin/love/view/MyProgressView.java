package com.lixinxin.love.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lixinxin on 2016/8/6.
 */
public class MyProgressView extends View {


    private int width = 100;
    private int height = 100;

    int max = 100;    //最大进度
    int progress = 50;     //进度

    private Paint circlePaint;  //画圆；
    private Paint progressPaint; //画重叠的部分
    private Paint textPaint; //画重叠的部分

    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Path path = new Path();

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

        float y = (1 - (float) progress / max) * height;

        path.moveTo(width, y);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, y);

        for (int i = 0; i < 3; i++) {
            path.rQuadTo(10, -10, 20, 0);
            path.rQuadTo(10, 10, 20, 0);
        }
        path.close();
        bitmapCanvas.drawPath(path, progressPaint);

        String text = (int) (((float) progress / max * 100)) + "%";
        float textWidth = textPaint.measureText(text);
        float x1 = width / 2 - textWidth / 2;
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y1 = height / 2 + dy;
        bitmapCanvas.drawText(text, x1, y1, textPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);

    }
}
