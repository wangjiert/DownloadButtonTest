package com.konka.customui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
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
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.konka.downloadbuttontest.R;

/**
 * TODO: document your custom view class.
 */
public class DownloadButon extends View {
    private int drawableBlank = 40;
    private Drawable startDrawable;
    private Drawable completeDrawable;
    private Drawable drawDrawable;
    private Paint paint;
    private Paint alphaPaint;
    private float paintWidth = 40;                  //painstrokewidth的初始值
    private final float startAngle = -90;
    private float totalPosition;
    private float currentPosition;
    private int paintColor = Color.BLUE;                   //paint的初始颜色
    private int drawableTop;
    private ValueAnimator drawableUpAnimator;
    private ValueAnimator drawableDownAnimator;
    private ValueAnimator completeAnimator;
    private ValueAnimator colorAnimator;
    private AnimatorSet animatorSet = new AnimatorSet();
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
        setClickable(true);
        setFocusable(true);
        int width = View.MeasureSpec.makeMeasureSpec( 0, MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec( 0, MeasureSpec.UNSPECIFIED);
        measure( width, height);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DownloadButon, defStyle, 0);

        paintWidth = a.getDimension(R.styleable.DownloadButon_paintWidth, paintWidth);
        paintColor = a.getColor(R.styleable.DownloadButon_paintColor, paintColor);
        if (a.hasValue(R.styleable.DownloadButon_startDrawable)) {
            startDrawable = a.getDrawable(
                    R.styleable.DownloadButon_startDrawable);
            startDrawable.setCallback(this);
            drawDrawable = startDrawable;
        }
        if (a.hasValue(R.styleable.DownloadButon_completeDrawable)) {
            completeDrawable = a.getDrawable(
                    R.styleable.DownloadButon_completeDrawable);
            completeDrawable.setCallback(this);
        }
        a.recycle();
        alphaPaint = new Paint();
        alphaPaint.setAntiAlias(true);
        alphaPaint.setStyle(Paint.Style.FILL);
        alphaPaint.setColor(0x88517ab3);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(paintColor);
        drawableTop = (int) (height - drawDrawable.getIntrinsicHeight()) / 2;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setDrawableTop((int)valueAnimator.getAnimatedValue());
            }
        };
        drawableUpAnimator = ValueAnimator.ofInt(drawableTop, (int) (getPaddingTop() + paintWidth - drawableBlank));
        drawableUpAnimator.setDuration(200);
        drawableUpAnimator.addUpdateListener(animatorUpdateListener);
        drawableUpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                drawDrawable = startDrawable;
            }
        });
        drawableDownAnimator = ValueAnimator.ofInt((int) (getPaddingTop() + paintWidth - drawableBlank), (int) (height - getPaddingBottom() - paintWidth - drawDrawable.getIntrinsicHeight() + drawableBlank));
        drawableDownAnimator.setDuration(400);
        drawableDownAnimator.addUpdateListener(animatorUpdateListener);
        drawableDownAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                drawDrawable = completeDrawable;
            }
        });
        completeAnimator = ValueAnimator.ofInt(0, (int) (height - drawDrawable.getIntrinsicHeight()) / 2);
        completeAnimator.setDuration(600);
        completeAnimator.addUpdateListener(animatorUpdateListener);
        completeAnimator.setInterpolator(new BounceInterpolator());
        colorAnimator = ValueAnimator.ofInt(0x00,0xFF);
        colorAnimator.setDuration(800);
        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setPaintAlpha((int)valueAnimator.getAnimatedValue());
            }
        });
        animatorSet.play(drawableUpAnimator).before(drawableDownAnimator);
        animatorSet.play(drawableDownAnimator).before(completeAnimator);
        animatorSet.play(drawableUpAnimator).with(colorAnimator);
    }

    private void setPaintAlpha(int value) {
        alphaPaint.setColor((value << 24) +paintColor);
    }

    public void setTotalPosition (float totalPosition) {
        this.totalPosition = totalPosition;
    }

    public void setCurrentPosition (float currentPosition) {
        this.currentPosition = currentPosition;
        if(currentPosition == totalPosition){
            animatorSet.start();
        }
    }

    private void setDrawableTop (int top){
        this.drawableTop = top;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(300,300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int width = drawDrawable.getIntrinsicWidth();
        int height = drawDrawable.getIntrinsicHeight();
        int left = (getWidth() - width) / 2;
        int top = (getHeight() - height) /2;
        RectF rectF = new RectF(paddingLeft + paintWidth / 2, paddingTop + paintWidth / 2, getWidth() - paddingRight - paintWidth / 2, getHeight() - paddingBottom - paintWidth / 2);
        if(currentPosition < totalPosition){
            canvas.drawArc(rectF, startAngle, currentPosition * 360 / totalPosition, false, paint);
            drawDrawable.setBounds(left, top, left + width,top + height);
            drawDrawable.draw(canvas);
        }
        else{
            RectF rectF1 = new RectF(paddingLeft + paintWidth / 2, paddingTop + paintWidth / 2, getWidth() - paddingRight - paintWidth / 2, getHeight() - paddingBottom - paintWidth / 2);
            canvas.drawOval(rectF1, alphaPaint);
            canvas.drawArc(rectF, startAngle, 360, false, paint);
            drawDrawable.setBounds(left, drawableTop, left + width, drawableTop + height);
            drawDrawable.draw(canvas);
        }


    }
}
