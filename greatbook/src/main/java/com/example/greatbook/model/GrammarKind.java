package com.example.greatbook.model;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/1/26.
 */

public class GrammarKind implements Serializable{
    private String href;
    private String name;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
