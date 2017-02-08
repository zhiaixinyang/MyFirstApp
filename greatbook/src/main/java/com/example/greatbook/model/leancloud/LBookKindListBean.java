package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MBENBEN on 2016/11/27.
 */
@AVClassName("LBookKindListBean")
public class LBookKindListBean extends AVObject{
    private String title;
    //此值使用对应的url作为所属id
    private String belongId;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }
}
