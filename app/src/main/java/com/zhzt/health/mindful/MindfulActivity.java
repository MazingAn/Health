package com.zhzt.health.mindful;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.zhzt.health.MainActivity;
import com.zhzt.health.R;

public class MindfulActivity extends AppCompatActivity implements View.OnClickListener {

    AnimationDrawable animationDrawable;
    Handler handler;
    Runnable runnable;
    RelativeLayout gameBoard;
    ImageView mindCircle, stopMindfulBtn;
    MindfulTouch mindfulTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        gameBoard = findViewById(R.id.mindful_background);
        stopMindfulBtn = findViewById(R.id.stop_mindful_btn);

        // 初始化背景渐变动画
        setupBackgroundAnimation();

        // 设置触摸事件
        MindfulTouch mindfulTouch = new MindfulTouch(this);
        gameBoard.setOnTouchListener(mindfulTouch);

        stopMindfulBtn.setOnClickListener(this);

    }

    /**
     * 设置背景图片绘制  循环渐变效果
     * */
    private void setupBackgroundAnimation(){
        animationDrawable = (AnimationDrawable)(gameBoard.getBackground());
        animationDrawable.start();
        ((TransitionDrawable)animationDrawable.getCurrent()).startTransition(8000);
        handler = new Handler();
        runnable = new Runnable() {
            TransitionDrawable oldDrawable;
            @Override
            public void run() {
                TransitionDrawable currentDrawable = (TransitionDrawable)animationDrawable.getCurrent();
                if(oldDrawable == null){
                    oldDrawable = currentDrawable;
                }
                if(oldDrawable != currentDrawable){
                    currentDrawable.resetTransition();
                    currentDrawable.startTransition(8000);
                    oldDrawable = currentDrawable;
                }
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 10000);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}