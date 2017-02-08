package com.example.greatbook.model;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindListBean implements Serializable{
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
