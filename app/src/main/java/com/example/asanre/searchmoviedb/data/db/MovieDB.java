package com.example.asanre.searchmoviedb.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class MovieDB extends RoomDatabase {

    public static final String DATABASE_NAME = "movies";
    private static MovieDB instance;

    public static MovieDB getInstance(Context context) {

        if (instance == null) {
            synchronized (MovieDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), MovieDB.class,
                            MovieDB.DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

    public abstract MovieDao getMovieDao();
}
