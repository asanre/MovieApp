package com.example.asanre.searchmoviedb.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.asanre.searchmoviedb.ui.model.IMovie;

public class Movie implements IMovie, Parcelable {

    private int id;
    private String title;
    private String posterUrl;
    private String overview;
    private String releaseYear;

    public Movie() {

    }

    public Movie(int id, String title, String posterUrl, String overview, String releaseYear) {

        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.releaseYear = releaseYear;
    }

    @Override
    public int getId() {

        return id;
    }

    @Override
    public String getTitle() {

        return title;
    }

    @Override
    public String getOverView() {

        return overview;
    }

    @Override
    public String getImageUrl() {

        return posterUrl;
    }

    @Override
    public String getReleaseYear() {

        return releaseYear;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setPosterUrl(String posterUrl) {

        this.posterUrl = posterUrl;
    }

    public void setOverview(String overview) {

        this.overview = overview;
    }

    public void setReleaseYear(String releaseYear) {

        this.releaseYear = releaseYear;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterUrl);
        dest.writeString(this.overview);
        dest.writeString(this.releaseYear);
    }

    protected Movie(Parcel in) {

        this.id = in.readInt();
        this.title = in.readString();
        this.posterUrl = in.readString();
        this.overview = in.readString();
        this.releaseYear = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {

            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {

            return new Movie[size];
        }
    };
}
