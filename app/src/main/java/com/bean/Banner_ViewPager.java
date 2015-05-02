package com.bean;

/**
 * Created by probuing on 2015/5/2.
 * 首页的头部ViewPager的实体类
 */
public class Banner_ViewPager {
    private String imageUrl,url;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Banner_ViewPager{" +
                "imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
