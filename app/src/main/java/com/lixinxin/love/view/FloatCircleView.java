package com.lixinxin.love.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lixinxin.love.R;

/**
 * Created by lixinxin on 2016/8/6.
 */
public class FloatCircleView extends View {

    public int width = 150;
    public int height = 150;
    private Paint circlePaint;
    private Paint textPaint;
    private String text = "LOVE";
    private Bitmap bitmap;
    private boolean drag = false;

    public FloatCircleView(Context context) {
        this(context, null);
    }

    public FloatCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaints();

    }

    private void initPaints() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(25);
        textPaint.setFakeBoldText(true);


        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.lmg);
        bitmap = Bitmap.createScaledBitmap(src, width, height, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (drag) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        } else {


            canvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);
            float textWidth = textPaint.measureText(text);
            float x = width / 2 - textWidth / 2;
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float dy = -(metrics.descent + metrics.ascent) / 2;
            float y = height / 2 + dy;
            canvas.drawText(text, x, y, textPaint);
        }
    }

    public void setDragState(boolean b) {
        drag = b;
        invalidate();
    }
}
