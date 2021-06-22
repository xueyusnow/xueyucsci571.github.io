package com.example.hhhw9.ui.movies;

public class SliderData {
    private String imgUrl;
    private String id;
    private String title;
    // Constructor method.
    public SliderData(String id, String imgUrl,String title) {
        this.imgUrl = imgUrl;
        this.id=id;
        this.title=title;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}

