package com.example.asanre.searchmoviedb.data.model;

import com.google.gson.annotations.SerializedName;

public class MovieRepo {

    private int id;
    private String title;
    @SerializedName("poster_path")
    private String posterUrl;
    private String overview;

    public MovieRepo(int id, String title, String posterUrl, String overview) {

        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.overview = overview;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getPosterUrl() {

        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {

        this.posterUrl = posterUrl;
    }

    public String getOverview() {

        return overview;
    }

    public void setOverview(String overview) {

        this.overview = overview;
    }
}
