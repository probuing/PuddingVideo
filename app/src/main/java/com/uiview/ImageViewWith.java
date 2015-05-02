package com.uiview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2015/4/15.
 */
public class ImageViewWith extends ImageView {

    //在代码中使用
    public ImageViewWith(Context context) {
        super(context);
    }
    //在布局文件XML中使用
    public ImageViewWith(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ImageViewWith(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth()!=0) {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int wSize = MeasureSpec.getSize(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);
            int hSize = MeasureSpec.getSize(heightMeasureSpec);
            if(wMode==MeasureSpec.EXACTLY&& hMode!=MeasureSpec.EXACTLY){

                hSize=drawable.getIntrinsicHeight() * wSize / drawable.getIntrinsicWidth();

                heightMeasureSpec=MeasureSpec.makeMeasureSpec(hSize,MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
