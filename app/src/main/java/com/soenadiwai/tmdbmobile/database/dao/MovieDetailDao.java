package com.soenadiwai.tmdbmobile.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.soenadiwai.tmdbmobile.model.MovieDetail;

import java.util.List;

@Dao
public interface MovieDetailDao {

    @Query("SELECT * FROM favorite_movie_table")
    List<MovieDetail> getAllFavMovies();

    @Query("SELECT * FROM favorite_movie_table WHERE id IN (:movieid)")
    MovieDetail getFavMovieById(int movieid);

    @Insert
    Long insertFavMovie(MovieDetail movieDetail);

    @Query("DELETE FROM favorite_movie_table WHERE id = :movieid")
    int deleteFavMovie(int movieid);
}