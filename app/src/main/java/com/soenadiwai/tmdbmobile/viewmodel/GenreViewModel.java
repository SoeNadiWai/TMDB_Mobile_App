package com.soenadiwai.tmdbmobile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soenadiwai.tmdbmobile.repository.GenreRepository;
import com.soenadiwai.tmdbmobile.response.GenreResponse;

public class GenreViewModel extends AndroidViewModel {

    private GenreRepository genreRepository;
    private LiveData<GenreResponse> genreResponseLiveData;


    public GenreViewModel(@NonNull Application application) {
        super(application);

        genreRepository = new GenreRepository();
        genreResponseLiveData = genreRepository.getMovieGenres();
    }

    public LiveData<GenreResponse> getGenreResponseLiveData() {
        return genreResponseLiveData;
    }
}
