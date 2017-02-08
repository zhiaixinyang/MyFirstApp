package com.example.greatbook.model;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public class BookDetailBean {
    private String content=null;
    private String title=null;
    private String css=null;

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
