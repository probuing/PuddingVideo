package com.utils;

import android.content.Context;
import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by probuing on 2015/5/2.
 * xUtils框架的BitMapUtils初始化类
 */
public class BitMapUtilsHelper {
    private static BitmapUtils utils;

    public static BitmapUtils getUtils() {
        return utils;
    }

    public static void init(Context context)
    {
        utils = new BitmapUtils(context, Environment.getExternalStorageDirectory()+"/puddingapp",1/8.0f,10*1024*1024);

    }
}
