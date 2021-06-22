package com.example.hhhw9;

import android.text.SpannableString;

public class SearchData {
    private String type;
    private String id;
    private String title;
    private String rate;
    private String year;
    private String image;
    private String poster;
//    private ArrayList<SliderData> cast;

    // Constructor method.
    public SearchData(String image, String title,String type,String id, String year,String rate,String purl) {
        this.type = type;
        this.id=id;
        this.title=title;
        this.year=year;
        this.rate = rate;
        this.image=image;
        this.poster=purl;
    }

    // Getter method
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getYear() {
        return year;
    }
    public String getRate() {
        return rate;
    }
    public String getImage() {
        return image;
    }
    public String getPoster() {
        return poster;
    }
}

