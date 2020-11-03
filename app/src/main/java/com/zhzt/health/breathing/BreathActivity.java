package com.zhzt.health.breathing;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.zhzt.health.MainActivity;
import com.zhzt.health.R;
import com.zhzt.health.services.MusicCommand;
import com.zhzt.health.services.MyBroadCastReceiver;
import com.zhzt.health.services.PlayMusicService;

public class BreathActivity extends FragmentActivity implements View.OnClickListener {
    private BreathBubbleView mDWView;
    private MyBroadCastReceiver receiver;
    private ImageView stopBtn;
    Intent musicIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_breath_activity);
        //初始化操作
        initView();
        initData();

        receiver=new MyBroadCastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.complete");
        registerReceiver(receiver,filter);

        controlMusic(MusicCommand.PLAY_MUSIC);
        stopBtn = findViewById(R.id.stop_breath_btn);
        stopBtn.setOnClickListener(this);
    }


    private void controlMusic(int type){
        musicIntent = new Intent(this, PlayMusicService.class);
        musicIntent.putExtra("type", type);
        startService(musicIntent);
    }

    /**
     * 初始化View
     */
    private void initView() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mDWView = findViewById(R.id.fbv_main);
    }

    /**
     * 初始化Data
     */
    private void initData() {
        BubbleDrawer bubbleDrawer = new BubbleDrawer(this);
        bubbleDrawer.setBackgroundGradient(new int[]{0xFF00C9FF, 0xFF92FE9D});
        mDWView.setDrawer(bubbleDrawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDWView.onDrawResume();
        controlMusic(MusicCommand.PLAY_MUSIC);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDWView.onDrawPause();
        controlMusic(MusicCommand.STOP_MUSIC);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDWView.onDrawDestroy();
        if(musicIntent != null){
            stopService(musicIntent);
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onClick(View view) {
        controlMusic(MusicCommand.STOP_MUSIC);
        if(musicIntent != null){
            stopService(musicIntent);
            unregisterReceiver(receiver);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}