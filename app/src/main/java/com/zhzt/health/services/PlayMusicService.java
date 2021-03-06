package com.zhzt.health.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zhzt.health.R;

public class PlayMusicService extends Service {
    //用于播放音乐等媒体资源
    private MediaPlayer mediaPlayer;
    //标志判断播放歌曲是否是停止之后重新播放，还是继续播放
    private boolean isStop=true;
    private int pausePosition = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
            //为播放器添加播放完成时的监听器
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //发送广播到MainActivity
                    Intent intent=new Intent();
                    intent.setAction("com.complete");
                    sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra("type",-1)){
            case MusicCommand.PLAY_MUSIC:
                if (isStop){
                    //重置mediaplayer
                    mediaPlayer.reset();
                    //将需要播放的资源与之绑定
                    mediaPlayer=MediaPlayer.create(this, R.raw.breath);
                    //开始播放
                    mediaPlayer.start();
                    //是否循环播放
                    mediaPlayer.setLooping(true);
                    isStop=false;
                }else{
                    if(!mediaPlayer.isPlaying()){
                        mediaPlayer.reset();
                        //将需要播放的资源与之绑定
                        mediaPlayer=MediaPlayer.create(this, R.raw.breath);
                        //开始播放
                        mediaPlayer.start();
                        mediaPlayer.seekTo(pausePosition);
                        //是否循环播放
                        mediaPlayer.setLooping(true);
                        isStop=false;
                    }
                }
                break;
            case MusicCommand.PAUSE_MUSIC:
                //播放器不为空，并且正在播放
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    pausePosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
                break;
            case MusicCommand.STOP_MUSIC:
                if (mediaPlayer!=null){
                    //停止之后要开始播放音乐
                    mediaPlayer.stop();
                    isStop=true;
                }
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
