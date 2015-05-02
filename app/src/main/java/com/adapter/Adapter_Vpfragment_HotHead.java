package com.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bean.Banner_ViewPager;
import com.puddingvideoproject.Fragment.Fragment_HotHeader;

import java.util.List;

/**
 * Created by probuing on 2015/5/3.
 */
public class Adapter_Vpfragment_HotHead extends FragmentPagerAdapter {
    private List<Banner_ViewPager> banners;
    private Context context;
    public Adapter_Vpfragment_HotHead(FragmentManager fm,List<Banner_ViewPager> list,Context context) {
        super(fm);
        this.banners = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment_HotHeader fragment = Fragment_HotHeader.getFragment(position, context, banners.get(position).getImageUrl());

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
