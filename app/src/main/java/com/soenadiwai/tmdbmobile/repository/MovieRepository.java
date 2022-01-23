package com.soenadiwai.tmdbmobile.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soenadiwai.tmdbmobile.model.MovieDetail;
import com.soenadiwai.tmdbmobile.response.MovieResponse;
import com.soenadiwai.tmdbmobile.retrofit.ApiRequest;
import com.soenadiwai.tmdbmobile.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public MovieRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<MovieResponse> getTopRatedMovies(Integer pageNumber) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiRequest.getTopRatedMovies(pageNumber)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<MovieResponse> getPopularMovies(Integer pageNumber) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiRequest.getPopularMovies(pageNumber)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e("popular_fail",t.getMessage());
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<MovieResponse> getUpComingMovies(Integer pageNumber) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiRequest.getUpcomingMovies(pageNumber)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<MovieDetail> getMovieDetailById(Integer movie_id) {
        final MutableLiveData<MovieDetail> data = new MutableLiveData<>();
        apiRequest.getMovieDetailById(movie_id)
                .enqueue(new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {
                        Log.e("failure", t.getMessage());
                        data.setValue(null);
                    }
                });
        return data;
    }
}
