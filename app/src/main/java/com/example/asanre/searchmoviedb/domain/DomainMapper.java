package com.example.asanre.searchmoviedb.domain;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;
import com.example.asanre.searchmoviedb.domain.model.Movie;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class DomainMapper {

    public static List<IMovie> map(List<MovieEntity> movieEntities) {

        ModelMapper mapper = new ModelMapper();
        List<IMovie> movies = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntities) {
            movies.add(mapper.map(movieEntity, Movie.class));
        }

        return movies;
    }
}
