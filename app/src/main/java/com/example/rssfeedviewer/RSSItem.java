package com.example.rssfeedviewer;

import java.util.Date;


public class RSSItem {
    private String title;
    private String description;
    private String link;
    private String strDate;
    private Date date;



    RSSItem(String title, String description, String link){
        this.title = title;
        this.description = description;
        this.link = link;
        this.strDate = "04 May 2020";
    }

    RSSItem(String title, String description, String link, Date date){
        this.title = title;
        this.description = description;
        this.link = link;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setStrDate(String strDate) { this.strDate = strDate;}

    public String getStrDate() {
        return strDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
