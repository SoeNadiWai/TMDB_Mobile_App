package com.soenadiwai.tmdbmobile.retrofit;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.API_KEY;

import com.soenadiwai.tmdbmobile.model.MovieDetail;
import com.soenadiwai.tmdbmobile.response.GenreResponse;
import com.soenadiwai.tmdbmobile.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("genre/movie/list?api_key="+API_KEY+"&language=en-US")
    Call<GenreResponse> getMovieGenres();

    @GET("movie/top_rated?api_key="+API_KEY+"&language=en-US")
    Call<MovieResponse> getTopRatedMovies(@Query("page") Integer pageNo);

    @GET("movie/popular?api_key="+API_KEY+"&language=en-US")
    Call<MovieResponse> getPopularMovies(@Query("page") Integer pageNo);

    @GET("movie/upcoming?api_key="+API_KEY+"&language=en-US")
    Call<MovieResponse> getUpcomingMovies(@Query("page") Integer pageNo);

    @GET("movie/{movie_id}?api_key="+API_KEY+"&language=en-US")
    Call<MovieDetail> getMovieDetailById(@Path("movie_id") Integer movieid);

    @GET("/search/movie?api_key="+API_KEY+"&language=en-US")
    Call<MovieResponse> getMoviesByGenre();
}