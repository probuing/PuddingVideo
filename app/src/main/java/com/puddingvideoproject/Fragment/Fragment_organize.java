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
import com.bean.Content_New;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.puddingvideoproject.R;
import com.uiview.MyViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2015/5/2.
 * 热门的fragment,最外层的viewpager的fragment
 */
public class Fragment_organize extends Fragment {
    public static List<Banner_ViewPager> banners;
    public static List<Content_New> content_newList;
    private static final String KEY = "position";
    private ViewPager vp_fragment_hothead;
    private MyViewPager vp_fragment_new;

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
        vp_fragment_hothead = ((MyViewPager) view.findViewById(R.id.vp_fragment_hothead));
        vp_fragment_new = ((MyViewPager) view.findViewById(R.id.vp_fragment_new));
        getNewData();
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
                    vp_fragment_hothead.setAdapter(new Adapter_Vpfragment_HotHead(getChildFragmentManager(), banners, getActivity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    //下载本月最新的数据的方法
    public void getNewData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppURLFinal.NewURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                try {
                    JSONArray jsonArray = new JSONArray(objectResponseInfo.result);
                    content_newList = new ArrayList<Content_New>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Content_New content_new = new Content_New();
                        JSONObject object = jsonArray.getJSONObject(i);
                        content_new.set_id(object.getString("_id"));
                        JSONObject anime = object.getJSONObject("anime");
                        content_new.setAnime_id(anime.getString("_id"));
                        JSONArray categoryNames = anime.getJSONArray("categoryNames");
                            String[] strings = new String[3];
                        for (int k = 0; k < categoryNames.length(); k++) {
                            if (k < 3) {
                                strings[k] = categoryNames.getString(k);
                            }
                            content_new.setCategoryNames(strings);
                        }
                            content_new.setDirectorNames(anime.getString("directorNames"));
                            JSONObject image = anime.getJSONObject("image");
                            content_new.setImage(image.getString("url"));
                            content_new.setIntro(anime.getString("intro"));
                            content_new.setScore(anime.getInt("score"));
                            content_new.setName(anime.getString("name"));
                        content_newList.add(content_new);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

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
