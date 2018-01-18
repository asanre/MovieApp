package com.example.asanre.searchmoviedb.domain.useCase.params;

public class SearchMovieParams {

    private String movie;
    private int page;

    public SearchMovieParams(String movie, int page) {

        this.movie = movie;
        this.page = page;
    }

    public String getMovie() {

        return movie;
    }

    public void setMovie(String movie) {

        this.movie = movie;
    }

    public int getPage() {

        return page;
    }

    public void setPage(int page) {

        this.page = page;
    }
}
