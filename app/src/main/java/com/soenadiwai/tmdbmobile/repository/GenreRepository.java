package com.soenadiwai.tmdbmobile.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soenadiwai.tmdbmobile.response.GenreResponse;
import com.soenadiwai.tmdbmobile.retrofit.ApiRequest;
import com.soenadiwai.tmdbmobile.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository {

    private static final String TAG = GenreRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public GenreRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<GenreResponse> getMovieGenres() {
        final MutableLiveData<GenreResponse> data = new MutableLiveData<>();
        apiRequest.getMovieGenres()
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
