package com.example.greatbook.beans.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MBENBEN on 2016/11/27.
 */
@AVClassName("LBookDesCatalogue")
public class LBookDesCatalogue extends AVObject{
    String title;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }
}
