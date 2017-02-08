package com.example.greatbook.model;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/1/30.
 */

public class GrammarKindIndex implements Serializable{
    private String name;
    private String href;
    private String name_kind;
    private String time;

    public String getNameKind() {
        return name_kind;
    }

    public void setNameKind(String name_kind) {
        this.name_kind = name_kind;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
}
