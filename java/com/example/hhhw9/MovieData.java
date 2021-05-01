package com.example.hhhw9;


import com.example.hhhw9.ui.movies.SliderData;

import java.util.ArrayList;

public class MovieData {
    private String genres;
    private String overview;
    private String title;
    private String year;
//    private ArrayList<SliderData> cast;

    // Constructor method.
    public MovieData(String title,String overview,String genres, String year) {
        this.genres = genres;
        this.overview=overview;
        this.title=title;
        this.year=year;
//        this.cast = cast;
    }

    // Getter method
    public String getOverview() {
        return overview;
    }
    public String getGenres() {
        return genres;
    }
    public String getTitle() {
        return title;
    }
    public String getYear() {
        return year;
    }


    // Setter method
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setYear(String year) {
        this.year = year;
    }


}

