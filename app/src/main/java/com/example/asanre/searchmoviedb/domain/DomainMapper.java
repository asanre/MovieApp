package com.example.asanre.searchmoviedb.domain;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;
import com.example.asanre.searchmoviedb.domain.model.Movie;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.asanre.searchmoviedb.domain.Constant.DATE_PATTERN;
import static com.example.asanre.searchmoviedb.domain.Constant.YEAR_PATTERN;

public class DomainMapper {

    public static List<IMovie> map(List<MovieEntity> movieEntities) {

        ModelMapper mapper = new ModelMapper();
        List<IMovie> movies = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());

        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = mapper.map(movieEntity, Movie.class);
            setDate(dateFormat, movieEntity, movie);
            movies.add(movie);
        }

        return movies;
    }

    private static void setDate(SimpleDateFormat dateFormat, MovieEntity movieEntity, Movie movie) {

        try {
            Date d = dateFormat.parse(movieEntity.getDate());
            movie.setReleaseYear(new SimpleDateFormat(YEAR_PATTERN, Locale.getDefault()).format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
