package com.zhzt.health.breathing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;

import com.zhzt.health.utils.RandomRanged;

import java.util.ArrayList;
import java.util.List;

public class BubbleDrawer {
    /*===== 图形相关 =====*/
    private GradientDrawable mGradientBg;       //渐变背景
    private int[] mGradientColors;              //渐变颜色数组
    private Paint mPaint; //抗锯齿画笔
    private Paint mFloatingPaint;
    private Paint mTextPaint; //文字画笔
    private int mWidth, mHeight;                //上下文对象
    private CircleBubble bubble;
    private List<FloatingBubble> floatingBubbles;

    /**
     * 构造函数
     *
     * @param context 上下文对象 可能会用到
     */
    public BubbleDrawer(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(8);
        mTextPaint.setTextSize(100);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mFloatingPaint = new Paint();
    }

    /**
     * 设置显示悬浮气泡的范围
     * @param width 宽度
     * @param height 高度
     */
    void setViewSize(int width, int height) {
        if (this.mWidth != width && this.mHeight != height) {
            this.mWidth = width;
            this.mHeight = height;
            if (this.mGradientBg != null) {
                mGradientBg.setBounds(0, 0, width, height);
            }
        }
        //设置一些默认的气泡
        initDefaultBubble(width, height);
    }

    /**
     * 初始化默认的气泡
     *
     * @param width 宽度
     */
    private void initDefaultBubble(int width, int height) {

        if(floatingBubbles == null){
            floatingBubbles = new ArrayList<>();
            for(int i = 0; i < 100; i++){
                floatingBubbles.add(new FloatingBubble(RandomRanged.nextFloat(0, 1.0f) * width, height - RandomRanged.nextFloat(0f, 1.0f) * height,
                        RandomRanged.nextFloat(0.01f, 0.03f) * width,
                        RandomRanged.nextFloat(1.0f, 3.0f), 0xcc78ffd6));
            }
        }

        if(bubble == null){
            bubble = new CircleBubble(0.5f * width, 0.5f * height,  0.1f * width,
                    0.004f, 0xcc78ffd6);
        }
    }

    /**
     * 用画笔在画布上画气泡
     *
     * @param canvas 画布
     * @param alpha  透明值
     */
    private void drawCircleBubble(Canvas canvas, float alpha) {
        bubble.updateAndDraw(canvas, mPaint, mTextPaint, alpha);
    }

    private void drawFloatingBubble(Canvas canvas, float alpha) {
        for(FloatingBubble fb : floatingBubbles ){
            fb.updateAndDraw(canvas, mFloatingPaint, alpha);
        }
    }

    /**
     * 画背景 画所有的气泡
     *
     * @param canvas 画布
     * @param alpha  透明值
     */
    void drawBgAndBubble(Canvas canvas, float alpha) {
        drawGradientBackground(canvas, alpha);
        drawCircleBubble(canvas, alpha);
        drawFloatingBubble(canvas, alpha);
    }


    /**
     * 设置渐变背景色
     *
     * @param gradientColors 渐变色数组 必须 >= 2 不然没法渐变
     */
    public void setBackgroundGradient(int[] gradientColors) {
        this.mGradientColors = gradientColors;
    }

    /**
     * 获取渐变色数组
     *
     * @return 渐变色数组
     */
    private int[] getBackgroundGradient() {
        return mGradientColors;
    }

    /**
     * 绘制渐变背景色
     *
     * @param canvas 画布
     * @param alpha  透明值
     */
    private void drawGradientBackground(Canvas canvas, float alpha) {
        if (mGradientBg == null) {
            //设置渐变模式和颜色
            mGradientBg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getBackgroundGradient());
            //规定背景宽高 一般都为整屏
            mGradientBg.setBounds(0, 0, mWidth, mHeight);
        }
        //然后开始画
        mGradientBg.setAlpha(Math.round(alpha * 255f));
        mGradientBg.draw(canvas);
    }

}
