package com.example.asanre.searchmoviedb.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.asanre.searchmoviedb.ui.model.IMovie;

public class Movie implements IMovie, Parcelable {

    private int id;
    private String title;
    private String posterUrl;
    private String overview;
    private String year;

    public Movie(int id, String title, String posterUrl, String overview, String year) {

        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.year = year;
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

        return year;
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
        dest.writeString(this.year);
    }

    public Movie() {

    }

    protected Movie(Parcel in) {

        this.id = in.readInt();
        this.title = in.readString();
        this.posterUrl = in.readString();
        this.overview = in.readString();
        this.year = in.readString();
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
