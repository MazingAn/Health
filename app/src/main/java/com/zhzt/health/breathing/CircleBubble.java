package com.zhzt.health.breathing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CircleBubble {
    private final float cx, cy;                 //圆心坐标
    private final float radius;                 //半径
    private final int color;                    //画笔颜色
    private final float variationOfFrame;       //设置每帧变化量
    private boolean isGrowing = true;           //根据此标志位判断左右移动
    private float curVariationOfFrame = 0f;     //当前帧变化量
    private String text = "吸气";

    CircleBubble(float cx, float cy, float radius, float variationOfFrame, int color) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.variationOfFrame = variationOfFrame;
        this.color = color;
    }

    /**
     * 更新位置并重新绘制
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param alpha  透明值
     */
    void updateAndDraw(Canvas canvas, Paint paint, Paint textPaint, float alpha) {
        /**
         * 每次绘制时都根据标志位(isGrowing)和每帧变化量(variationOfFrame)进行更新
         * 说白了其实就是每帧都会变化一段距离  连在一起 这就产生了动画效果
         */
        if (isGrowing) {
            curVariationOfFrame += variationOfFrame;
            if (curVariationOfFrame > 2f) {
                curVariationOfFrame = 2f;
                isGrowing = false;
                text = "呼气";
            }
        } else {
            curVariationOfFrame -= variationOfFrame;
            if (curVariationOfFrame < 0f) {
                curVariationOfFrame = 0f;
                isGrowing = true;
                text = "吸气";
            }
        }


        //根据当前帧变化量计算圆心偏移后的位置
        float curRadius = radius + radius * curVariationOfFrame;

        //设置画笔颜色
        int curColor = convertAlphaColor(alpha * (Color.alpha(color) / 255f), color);
        //这里才真正的开始画圆形气泡
        paint.setColor(curColor);
        canvas.drawCircle(cx, cy, curRadius, paint);


        textPaint.setColor(0xffffffff);
        textPaint.setTextSize(50 + 50 * curVariationOfFrame);
        canvas.drawText(text, cx, cy,   textPaint);

    }

    /**
     * 转成透明颜色
     *
     * @param percent       百分比
     * @param originalColor 初始颜色
     * @return 带有透明效果的颜色
     */
    private static int convertAlphaColor(float percent, final int originalColor) {
        int newAlpha = (int) (percent * 255) & 0xFF;
        return (newAlpha << 24) | (originalColor & 0xFFFFFF);
    }
}
