package com.puddingvideoproject.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.puddingvideoproject.R;
import com.uiview.ImageViewWith;
import com.utils.BitMapUtilsHelper;

/**
 * Created by probuing on 2015/5/2.
 * 头部Fragment
 */
public class Fragment_HotHeader extends Fragment {
    //ViewPager的位置常量标识
    public static final String POSITION = "position";
    public static final String IMAGEURL = "ImageUrl";
    public static Fragment_HotHeader getFragment(int position,Context context,String imageUrl) {
        Fragment_HotHeader fragment = new Fragment_HotHeader();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(IMAGEURL,imageUrl);
        BitMapUtilsHelper.init(context);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_hotheader,null);
        ImageViewWith image = (ImageViewWith) view.findViewById(R.id.image);
        Bundle bundle = getArguments();
        int position = bundle.getInt(POSITION);
        String imageurl = bundle.getString(IMAGEURL);
        BitMapUtilsHelper.getUtils().display(image, imageurl);
        return view;
    }

}
