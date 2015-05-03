package com.puddingvideoproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.puddingvideo.Activity.Activity_ShowHeader;
import com.puddingvideoproject.R;
import com.uiview.ImageViewWith;
import com.utils.BitMapUtilsHelper;

import java.lang.reflect.Field;

/**
 * Created by probuing on 2015/5/2.
 * 头部Fragment
 */
public class Fragment_HotHeader extends Fragment {
    //ViewPager的位置常量标识
    public static final String POSITION = "position";
    public static final String IMAGEURL = "ImageUrl";
    public static final String URL = "url";
    private ImageViewWith image;
    private String url;

    public static Fragment_HotHeader getFragment(int position,Context context,String imageUrl,String url) {
        Fragment_HotHeader fragment = new Fragment_HotHeader();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(IMAGEURL, imageUrl);
        bundle.putString(URL,url);
        BitMapUtilsHelper.init(context);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_hotheader,null);
        image = (ImageViewWith) view.findViewById(R.id.image);
        Bundle bundle = getArguments();
        int position = bundle.getInt(POSITION);
        String imageurl = bundle.getString(IMAGEURL);
        url = bundle.getString(URL);
        BitMapUtilsHelper.getUtils().display(image, imageurl);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        image = (ImageViewWith)view.findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_ShowHeader.class);
                intent.putExtra(URL,url);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
