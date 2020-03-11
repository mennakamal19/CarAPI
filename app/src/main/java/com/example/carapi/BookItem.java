package com.example.carapi;

import android.telephony.mbms.StreamingServiceInfo;

public class BookItem
{
    String title,author,thumbnail;

    public BookItem(String title, String author, String thumbnail) {
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
    }

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
