package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MBENBEN on 2016/11/25.
 */
@AVClassName("BookTalkBean")
public class BookTalkBean extends AVObject{
    private String content;
    private String belongId;
    private String belongBookId;

    public String getBelongBookId() {
        return getString("belongBookId");
    }

    public void setBelongBookId(String belongBookId) {
        put("belongBookId",belongBookId);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }
}
