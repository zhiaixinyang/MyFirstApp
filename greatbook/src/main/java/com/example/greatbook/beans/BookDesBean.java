package com.example.greatbook.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookDesBean implements Serializable{
    private String urlPhoto;
    private String des;

    private List<Catalogue> catalogueList;

    public List<Catalogue> getCatalogueList() {
        return catalogueList;
    }

    public void setCatalogueList(List<Catalogue> catalogueList) {
        this.catalogueList = catalogueList;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }


    public class Catalogue implements Serializable{
        String title;
        String url;

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
}
