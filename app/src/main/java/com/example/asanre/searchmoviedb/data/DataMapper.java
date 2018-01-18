package com.example.asanre.searchmoviedb.data;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;
import com.example.asanre.searchmoviedb.data.model.MovieRepo;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    public static List<MovieEntity> mapToEntity(List<MovieRepo> serviceResponse, int page) {

        ModelMapper mapper = new ModelMapper();
        List<MovieEntity> movieEntities = new ArrayList<>();
        for (MovieRepo movieRepo : serviceResponse) {
            MovieEntity movieEntity = mapper.map(movieRepo, MovieEntity.class);
            movieEntity.setPage(page);
            movieEntities.add(movieEntity);
        }

        return movieEntities;
    }
}
