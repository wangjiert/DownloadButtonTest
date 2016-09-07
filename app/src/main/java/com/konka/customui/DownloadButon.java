package com.konka.customui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.konka.downloadbuttontest.R;

/**
 * TODO: document your custom view class.
 */
public class DownloadButon extends View {
    private Drawable startDrawable;
    private Paint paint;
    private float paintWidth = 40;                  //painstrokewidth的初始值
    private final float startAngle = -90;
    private float totalPosition;
    private float currentPosition;
    private int paintColor = Color.BLUE;                   //paint的初始颜色
    public DownloadButon(Context context) {
        super(context);
        init(null, 0);
    }

    public DownloadButon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DownloadButon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DownloadButon, defStyle, 0);

        paintWidth = a.getDimension(R.styleable.DownloadButon_paintWidth, paintWidth);
        paintColor = a.getColor(R.styleable.DownloadButon_paintColor, paintColor);
        if (a.hasValue(R.styleable.DownloadButon_startDrawable)) {
            startDrawable = a.getDrawable(
                    R.styleable.DownloadButon_startDrawable);
            startDrawable.setCallback(this);
        }
        a.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(paintColor);
    }

    public void setTotalPosition(float totalPosition) {
        this.totalPosition = totalPosition;
    }

    public void setCurrentPosition(float currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        canvas.drawArc(new RectF(paddingLeft + paintWidth / 2, paddingTop + paintWidth / 2, getWidth() - paddingRight - paintWidth / 2, getHeight() - paddingBottom - paintWidth / 2), startAngle, currentPosition * 360 / totalPosition, false, paint);
        int width = startDrawable.getIntrinsicWidth();
        int height = startDrawable.getIntrinsicHeight();
        int left = (getWidth() - width) / 2;
        int top = (getHeight() - height) /2;
        startDrawable.setBounds(left, top, left + width,top + height);
        startDrawable.draw(canvas);
        
    }
}
