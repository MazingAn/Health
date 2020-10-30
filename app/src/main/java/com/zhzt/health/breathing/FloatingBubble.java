package com.zhzt.health.breathing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.zhzt.health.utils.RandomRanged;

public class FloatingBubble{

    private float cx, cy;                 //圆心坐标
    private float dx, dy;                 //圆心偏移量
    private float radius;                 //半径
    private int color;                    //画笔颜色
    private float variationOfFrame;       //设置每帧变化量
    private boolean isGrowing = true;           //根据此标志位判断左右移动
    private float curVariationOfFrame = 0f;     //当前帧变化量

    FloatingBubble(float cx, float cy, float radius, float variationOfFrame, int color) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.variationOfFrame = variationOfFrame;
        this.color = color;
    }

    public FloatingBubble() {
    }

    /**
     * 更新位置并重新绘制
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param alpha  透明值
     */
    void updateAndDraw(Canvas canvas, Paint paint, float alpha) {
        /**
         * 每次绘制时都根据标志位(isGrowing)和每帧变化量(variationOfFrame)进行更新
         * 说白了其实就是每帧都会变化一段距离  连在一起 这就产生了动画效果
         */
        if(this.cy <= 0){
            cy = canvas.getHeight();
        }


        //根据当前帧变化量计算圆心偏移后的位置
        cx = cx + RandomRanged.nextFloat(-1 * variationOfFrame * 0.5f, variationOfFrame * 0.5f);
        cy -= RandomRanged.nextFloat(0, variationOfFrame);

        //设置画笔颜色
        int curColor = convertAlphaColor(alpha * (Color.alpha(color) / 255f), color);
        //这里才真正的开始画圆形气泡
        paint.setColor(curColor);
        canvas.drawCircle(cx, cy, radius, paint);
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
