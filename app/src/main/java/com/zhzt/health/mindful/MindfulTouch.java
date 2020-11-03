package com.zhzt.health.mindful;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhzt.health.R;
import com.zhzt.health.services.MusicCommand;
import com.zhzt.health.services.MyBroadCastReceiver;
import com.zhzt.health.services.PlayMusicService;

public class MindfulTouch implements View.OnTouchListener {

    float[] tempXy = new float[] {0, 0};
    int stopCount = 0;
    long tempTime = 0;
    long startTime = 0;
    boolean startLock = false;

    Context parent;
    TextView notifyTextView;
    ImageView mindCircle, stopBtn;
    Intent musicIntent;
    View root;
    float mindCircleScale = 1.0f;
    float mindCircleScaleOffset = 0.0f;
    MyBroadCastReceiver receiver;


    public MindfulTouch(Context parent) {
        this.parent = parent;
        receiver=new MyBroadCastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.complete");
        parent.registerReceiver(receiver, filter);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        notifyTextView = view.findViewById(R.id.notify_text);
        mindCircle = view.findViewById(R.id.mind_circle);
        stopBtn = view.findViewById(R.id.stop_mindful_btn);
        root = view;

        if(!startLock){
            startTime = System.currentTimeMillis();
            startLock = true;
        }
        tempTime = System.currentTimeMillis();

        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                if(stopCount == 0){
                    notifyTextView.setText("很好！按住了！\n跟随音乐的节奏！缓缓移动手指！");
                    controlMusic(MusicCommand.PLAY_MUSIC);
                    tempXy[0] = motionEvent.getRawX();
                    tempXy[1] = motionEvent.getRawY();
                    stopBtn.setVisibility(View.INVISIBLE);
                }
                break;

            case MotionEvent.ACTION_UP:
                notifyTextView.setText("结束了！\n正念时间：" + (tempTime - startTime) / 1000 + "秒");
                startLock = false;
                controlMusic(MusicCommand.STOP_MUSIC);
                resetMindCircle();
                stopBtn.setVisibility(View.VISIBLE);
                break;

            case MotionEvent.ACTION_MOVE:
                    float moveX = Math.abs(motionEvent.getRawX() - tempXy[0]);
                    float moveY = Math.abs(motionEvent.getRawY() - tempXy[1]);
                    if(moveX  == 0 && moveY == 0 ){
                        if(tempTime - startTime > 3000) {
                            notifyTextView.setText("停住了！");
                            controlMusic(MusicCommand.PAUSE_MUSIC);
                        }
                        scaleMindCircle(-0.1f);
                    }
                    else {
                        double distance = Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2));
                        if(distance > view.getWidth() / 100){
                            if(tempTime - startTime > 3000) {
                                notifyTextView.setText("太快了！");
                            }
                            controlMusic(MusicCommand.PAUSE_MUSIC);
                        }else{
                            long deltaTime = tempTime - startTime;
                            if(deltaTime > 3000 && deltaTime < 10000) {
                                notifyTextView.setText("做的不错，继续保持专注！");
                            }
                            else if(deltaTime > 10000 && deltaTime < 20000){
                                notifyTextView.setText("进入状态了！");
                            }
                            else if(deltaTime > 20000 && deltaTime < 25000) {
                                notifyTextView.setText("闭上眼睛！");
                            }else{
                                notifyTextView.setText("");
                            }
                            controlMusic(MusicCommand.PLAY_MUSIC);
                            scaleMindCircle(distance);
                        }
                    }
                    tempXy[0] = motionEvent.getRawX();
                    tempXy[1] = motionEvent.getRawY();
                    mindCircle.setX(tempXy[0] - mindCircle.getWidth() / 2);
                    mindCircle.setY(tempXy[1] - mindCircle.getHeight() / 2);
                break;
        }
        return true;
    }

    private void scaleMindCircle(double distance){
        int maxEdge = Math.max(root.getWidth(),root.getHeight());
        mindCircleScaleOffset +=  distance / maxEdge;
        mindCircle.setScaleX(mindCircleScale + mindCircleScaleOffset);
        mindCircle.setScaleY(mindCircleScale + mindCircleScaleOffset);
    }

    private void resetMindCircle(){
        mindCircle.setScaleX(1.0f);
        mindCircle.setScaleY(1.0f);
        mindCircle.setX(root.getWidth() / 2 - mindCircle.getWidth() / 2);
        mindCircle.setY(root.getHeight() / 2 - mindCircle.getHeight() / 2);
    }

    private void controlMusic(int type){
        musicIntent = new Intent(this.parent, PlayMusicService.class);
        musicIntent.putExtra("type", type);
        parent.startService(musicIntent);
    }

    private void destroy(){
        if(musicIntent != null){
            parent.stopService(musicIntent);
            parent.unregisterReceiver(receiver);
        }
    }
}
