package com.example.greatbook.beans;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindBean implements Serializable{
    private String urlPhoto;
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
