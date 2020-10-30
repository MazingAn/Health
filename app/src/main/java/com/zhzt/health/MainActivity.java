package com.zhzt.health;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhzt.health.breathing.BreathActivity;
import com.zhzt.health.dashboard.CheckNodeAdapter;
import com.zhzt.health.dashboard.CheckNodeHelper;
import com.zhzt.health.dashboard.FeaturedAdapter;
import com.zhzt.health.dashboard.FeaturedHelper;
import com.zhzt.health.dashboard.WikiAdapter;
import com.zhzt.health.dashboard.WikiHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    RecyclerView featuredRecycler, wikiRecycler, checkSelfRecycler;
    RecyclerView.Adapter featuredAdapter, wikiAdapter, checkNodeAdapter;
    LinearLayout menu_breath;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
        getWindow().setAttributes(lp);
        featuredRecycler = findViewById(R.id.featured_recycler);
        wikiRecycler = findViewById(R.id.wiki_recycler);
        checkSelfRecycler = findViewById(R.id.check_self_recycler);
        configFeaturedRecycler();
        configWikiRecycler();
        configCheckSelfRecycler();
        menu_breath = findViewById(R.id.menu_breath);
        menu_breath.setOnClickListener(this);
    }

    private void configFeaturedRecycler() {
        //当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)
        //并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<FeaturedHelper> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelper(R.drawable.person, "毕业后如何找到满意的工作", "都说“金九银十”，金九已经过去一半，银十即将到来，在这么好的时间里如何找到满意的工作成了刚毕业的学生来说十分苦恼。"));
        featuredLocations.add(new FeaturedHelper(R.drawable.car, "假期“恶补式旅游”为哪般", "随着暑期结束，携娃出游的家庭已陆续回到家中，但暑期旅游遭遇的种种窘境却依然记忆犹新。。"));
        featuredLocations.add(new FeaturedHelper(R.drawable.hard, "不屈服于磨难", "遇到坎坷，遇到失败是一件再正常不过的情况，而失败后能够从头再来也是一件非常需要勇气的事情."));
        featuredLocations.add(new FeaturedHelper(R.drawable.child, "让孩子活出自己的姿态", "本文为邓剑英老师原创投稿，首发邓剑英老师的微信公众号“母亲的长征"));
        featuredLocations.add(new FeaturedHelper(R.drawable.smial_golf, "爱不爱是其次，相处不累最重要", "单身的时候，大家都会觉得爱情很重要，但结婚之后，面对柴米油盐的琐碎，爱情或许就不是最重要的事情了"));
        featuredAdapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(featuredAdapter);
    }

    private void configWikiRecycler(){
        wikiRecycler.setHasFixedSize(true);
        wikiRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<WikiHelper> wikiLocations = new ArrayList<>();
        wikiLocations.add(new WikiHelper(R.drawable.thumb1, "毕业后如何找到满意的工作", "都说“金九银十”，金九已经过去一半，银十即将到来，在这么好的时间里如何找到满意的工作成了刚毕业的学生来说十分苦恼。"));
        wikiLocations.add(new WikiHelper(R.drawable.thumb2, "假期“恶补式旅游”为哪般", "随着暑期结束，携娃出游的家庭已陆续回到家中，但暑期旅游遭遇的种种窘境却依然记忆犹新。。"));
        wikiLocations.add(new WikiHelper(R.drawable.thumb3, "不屈服于磨难", "遇到坎坷，遇到失败是一件再正常不过的情况，而失败后能够从头再来也是一件非常需要勇气的事情."));
        wikiLocations.add(new WikiHelper(R.drawable.thumb4, "让孩子活出自己的姿态", "本文为邓剑英老师原创投稿，首发邓剑英老师的微信公众号“母亲的长征"));
        wikiLocations.add(new WikiHelper(R.drawable.thumb5, "爱不爱是其次，相处不累最重要", "单身的时候，大家都会觉得爱情很重要，但结婚之后，面对柴米油盐的琐碎，爱情或许就不是最重要的事情了"));
        wikiAdapter = new WikiAdapter(wikiLocations);
        wikiRecycler.setAdapter(wikiAdapter);
    }

    private void configCheckSelfRecycler(){
        checkSelfRecycler.setHasFixedSize(true);
        checkSelfRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CheckNodeHelper> checkNodeLocations = new ArrayList<>();
        checkNodeLocations.add(new CheckNodeHelper("FPA性格色彩测试", R.drawable.checknode2));
        checkNodeLocations.add(new CheckNodeHelper("职业锚测试", R.drawable.checknode3));
        checkNodeLocations.add(new CheckNodeHelper("情绪识别能力测试", R.drawable.checknode4));
        checkNodeLocations.add(new CheckNodeHelper("脸盲症测试", R.drawable.checknode5));
        checkNodeLocations.add(new CheckNodeHelper("阿拉伯驯马师测试", R.drawable.checknode6));
        checkNodeLocations.add(new CheckNodeHelper("心理年龄测试", R.drawable.checknode1));
        checkNodeAdapter = new CheckNodeAdapter(checkNodeLocations);
        checkSelfRecycler.setAdapter(checkNodeAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == menu_breath.getId()){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, BreathActivity.class);
            startActivity(intent);
        }
    }
}