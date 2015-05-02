package com.puddingvideo.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.puddingvideoproject.Fragment.Fragment_organize;
import com.puddingvideoproject.R;


public class MainActivity extends FragmentActivity {
    private boolean isExit = false;//判断退出的标志位

    private ViewPager vp_head_main;
    private View[] views = new View[2];
    private RadioGroup rg_head_main;
    private RelativeLayout re_main;
    private ImageView image_search_main;
    private int[] rbIds = {R.id.rb1_head_main, R.id.rb2_head_main};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp_head_main = (ViewPager) findViewById(R.id.vp_head_main);
        rg_head_main = (RadioGroup) findViewById(R.id.rg_head_main);
        //设置总的ViewPager的页面适配器c
        vp_head_main.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //选中改变选项的位置
        rg_head_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rbIds.length; i++) {
                    if (rbIds[i] == checkedId) {
                        vp_head_main.setCurrentItem(i);
                    }
                }
            }
        });
        //滚动条的改变
        vp_head_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < views.length; i++) {
                    if (position == i) {
                        views[i].setVisibility(View.VISIBLE);
                    } else {
                        views[i].setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //初始化滚动条id
        init();

    }

    private void init() {
        views[0] = findViewById(R.id.view1_head_main);
        views[1] = findViewById(R.id.view2_head_main);
        image_search_main = (ImageView) findViewById(R.id.image_search_main);

        image_search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置搜索页面
                Intent intent = new Intent(MainActivity.this, ActivitySearch.class);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return Fragment_organize.getFragment(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    // private ImageView imagebtn1_bottom_main, imagebtn2_bottom_main, imagebtn3_bottom_main, imagebtn4_bottom_main;
    //设置图片按钮的点击事件
    public void imagebtnOnClick(View view) {
        switch (view.getId()) {
            case R.id.imagebtn2_bottom_main:
                Intent intentTime = new Intent(MainActivity.this, ActivityTime.class);
                startActivity(intentTime);
                break;
            case R.id.imagebtn3_bottom_main:
                Intent intentCyber = new Intent(MainActivity.this, ActivityCyber.class);
                startActivity(intentCyber);
                break;
            case R.id.imagebtn4_bottom_main:
                Intent intentMine = new Intent(MainActivity.this, ActivityMine.class);
                startActivity(intentMine);
                break;
        }
    }

    //双击退出

    @Override
    public void onBackPressed() {
        if (!isExit) {//false不退出--不能去执行系统的销毁窗体
            Toast.makeText(MainActivity.this, "再按一次就要离开喽，亲", Toast.LENGTH_SHORT).show();
            //修改标识位
            isExit = true;

            //两秒之后吧标识位改为false

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {//两秒钟之后执行的操作
                    isExit = false;
                }
            }, 2000);

            return;//方法结束
        }
        super.onBackPressed();
    }
}

