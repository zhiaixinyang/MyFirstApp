package com.example.greatbook.model;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/1/31.
 */

public class GrammarContent implements Serializable{
    private String href;
    private String title;
    private String author;
    private String origin;
    private String time;
    private String content;
    private String recommendation_1_desc;
    private String recommendation_1_href;
    private String recommendation_2_desc;
    private String recommendation_2_href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecommendation_1_desc() {
        return recommendation_1_desc;
    }

    public void setRecommendation_1_desc(String recommendation_1_desc) {
        this.recommendation_1_desc = recommendation_1_desc;
    }

    public String getRecommendation_1_href() {
        return recommendation_1_href;
    }

    public void setRecommendation_1_href(String recommendation_1_href) {
        this.recommendation_1_href = recommendation_1_href;
    }

    public String getRecommendation_2_desc() {
        return recommendation_2_desc;
    }

    public void setRecommendation_2_desc(String recommendation_2_desc) {
        this.recommendation_2_desc = recommendation_2_desc;
    }

    public String getRecommendation_2_href() {
        return recommendation_2_href;
    }

    public void setRecommendation_2_href(String recommendation_2_href) {
        this.recommendation_2_href = recommendation_2_href;
    }
}
