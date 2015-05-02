package com.puddingvideoproject.Fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AppFinal.AppURLFinal;
import com.adapter.Adapter_Vpfragment_HotHead;
import com.bean.Banner_ViewPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.puddingvideoproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2015/5/2.
 * 热门的fragment,最外层的viewpager的fragment
 */
public class Fragment_organize extends Fragment {
    public static List<Banner_ViewPager> banners;

    private static final String KEY = "position";
    private ViewPager vp_fragment_hothead;

    public static Fragment_organize getFragment(int position) {
        Fragment_organize fragment_organize = new Fragment_organize();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        fragment_organize.setArguments(bundle);

        return fragment_organize;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organize, null);
        //热门的Fragment的Viewpager
        vp_fragment_hothead = ((ViewPager) view.findViewById(R.id.vp_fragment_hothead));
        getHeadData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    public void getHeadData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppURLFinal.HeadURL, new RequestCallBack<String>() {


            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    JSONArray arr = object.getJSONArray("featured_banner");
                    banners = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject object1 = arr.getJSONObject(i);
                        Banner_ViewPager banner = new Banner_ViewPager();
                        banner.setImageUrl(object1.getString("imageUrl"));
                        banner.setUrl(object1.getString("url"));
                        banners.add(banner);
                    }
                    vp_fragment_hothead.setAdapter(new Adapter_Vpfragment_HotHead(getChildFragmentManager(),banners,getActivity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
}
}
