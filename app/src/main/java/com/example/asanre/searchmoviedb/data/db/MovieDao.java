package com.example.asanre.searchmoviedb.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(MovieEntity... movie);

    @Query("SELECT * FROM movie WHERE page = :page")
    Single<List<MovieEntity>> getMoviesByPage(int page);

    @Query("delete from movie")
    void clear();
}
