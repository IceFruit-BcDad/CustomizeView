package com.qqaqq.lib;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author houzongqi
 * @date 2018/12/19 11:01
 */
public class CircleProgressBar extends View {

    //圆环半径，默认:Math.min(getHeight()/2, getWidth()/2)
    private float mRingRadius = 0;

    //圆环宽度 默认半径1/5
    private float mRingWidth = 0;

    //圆环背景颜色
    private int mRingBackgroundColor;

    //圆环颜色
    private int mRingColor;

    //圆环背景画笔
    private Paint mRingBackgroundPaint;

    //圆环画笔
    private Paint mRingPaint;

    //是否显示进度
    private boolean mTextEnable = false;

    //进度(%)
    private int progress;

    //文本大小
    private float mTextSize;

    //文本颜色
    private int mTextColor = Color.BLACK;

    //文本
    private String mText;

    private ObjectAnimator mObjectAnimator;

    //文本画笔
    private Paint mTextPaint;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mRingWidth = typedArray.getDimension(R.styleable.CircleProgressBar_ring_width, 0);
        mRingRadius = typedArray.getDimension(R.styleable.CircleProgressBar_ring_radius, 0);
        mRingBackgroundColor = typedArray.getColor(
                R.styleable.CircleProgressBar_ring_background_color,
                Color.parseColor("#4489CA"));
        mRingColor = typedArray.getColor(
                R.styleable.CircleProgressBar_ring_color, Color.parseColor("#3F51B5"));
        mTextColor = typedArray.getColor(
                R.styleable.CircleProgressBar_text_color, Color.BLACK);
//        mText = typedArray.getString(
//                R.styleable.CircleProgressBar_text);
        mTextSize = typedArray.getDimension(R.styleable.CircleProgressBar_text_size,
                40);
        mTextEnable = typedArray.getBoolean(R.styleable.CircleProgressBar_text_enable,
                false);
        init();
    }

    private void init(){
        mRingBackgroundPaint = new Paint();
        mRingBackgroundPaint.setAntiAlias(true);
        mRingBackgroundPaint.setStyle(Paint.Style.STROKE);
        mRingBackgroundPaint.setColor(mRingBackgroundColor);
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);
        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        float radius = mRingRadius == 0 ? Math.min(x, y) : mRingRadius;
        float ringWidth = mRingWidth == 0 ? mRingRadius / 5 : mRingWidth;
        radius = radius - ringWidth;
        mRingBackgroundPaint.setStrokeWidth(ringWidth);
        mRingPaint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(x, y, radius, mRingBackgroundPaint);
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(x - radius,
                y - radius, x + radius,
                y + radius);
        canvas.drawArc(rectF, 0, (float) (progress * 3.6), false, mRingPaint);
        if (mTextEnable){
            canvas.drawText(progress + "%", x, y + mTextSize / 2, mTextPaint);
        }
    }

    /**
     * 修改进度
     * @param progress 进度(<=100)
     */
    public void setProgress(int progress) {
        if (progress <= 100){
            this.progress = progress;
            invalidate();
        }
    }


    /**
     * 修改是否显示进度条
     * @param textEnable 进度条状态
     */
    public void setTextEnable(boolean textEnable) {
        mTextEnable = textEnable;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void upDateProgress(int progress){
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", progress);
        mObjectAnimator.start();
    }
}
