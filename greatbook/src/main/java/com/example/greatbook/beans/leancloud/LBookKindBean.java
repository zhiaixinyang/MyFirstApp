package com.example.greatbook.beans.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2016/11/20.
 */
@AVClassName("LBookKindBean")
public class LBookKindBean extends AVObject{
    private AVFile photo;
    private String title;
    //此值使用对应的url作为所属id
    private String belongId;

    public AVFile getPhoto() {
        return getAVFile("photo");
    }

    public void setPhoto(AVFile photo) {
        put("photo",photo);
    }

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

}
