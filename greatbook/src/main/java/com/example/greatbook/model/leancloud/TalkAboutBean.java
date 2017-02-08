package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by MBENBEN on 2016/11/24.
 */
@AVClassName("TalkAboutBean")
public class TalkAboutBean extends AVObject{
    private String belongId;
    private String content;
    private AVFile contentPhoto;
    private AVFile avatar;

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public AVFile getContentPhoto() {
        return getAVFile("contentPhoto");
    }

    public void setContentPhoto(AVFile contentPhoto) {
        put("contentPhoto",contentPhoto);
    }

    public AVFile getAvatar() {
        return getAVFile("avatar");
    }

    public void setAvatar(AVFile avatar) {
        put("avatar",avatar);
    }
}
