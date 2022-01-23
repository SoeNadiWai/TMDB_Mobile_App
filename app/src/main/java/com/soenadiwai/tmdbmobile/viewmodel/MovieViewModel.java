package com.soenadiwai.tmdbmobile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.soenadiwai.tmdbmobile.model.MovieDetail;
import com.soenadiwai.tmdbmobile.repository.MovieRepository;
import com.soenadiwai.tmdbmobile.response.MovieResponse;

public class MovieViewModel extends AndroidViewModel {

    private final MovieRepository movieRepository;
    private LiveData<MovieResponse> topratedMovieResponseLiveData;
    private LiveData<MovieResponse> popularMovieResponseLiveData;
    private LiveData<MovieResponse> upcomingMovieResponseLiveData;
    private LiveData<MovieDetail> movieDetailLiveData;

    public MovieViewModel(@NonNull Application application, Integer pageNumber) {
        super(application);
        movieRepository = new MovieRepository();
//        topratedMovieResponseLiveData = movieRepository.getTopRatedMovies(pageNumber);
//        popularMovieResponseLiveData = movieRepository.getPopularMovies(pageNumber);
//        upcomingMovieResponseLiveData = movieRepository.getUpComingMovies(pageNumber);
    }

    public LiveData<MovieResponse> getTopRatedMovies(Integer pageNumber) {
        if (topratedMovieResponseLiveData == null) {
            topratedMovieResponseLiveData = new MutableLiveData<>();
        }
        topratedMovieResponseLiveData = movieRepository.getTopRatedMovies(pageNumber);
        return topratedMovieResponseLiveData;
    }

    public LiveData<MovieResponse> getPopularMovies(Integer pageNumber) {
        if (popularMovieResponseLiveData == null) {
            popularMovieResponseLiveData = new MutableLiveData<>();
        }
        popularMovieResponseLiveData = movieRepository.getPopularMovies(pageNumber);
        return popularMovieResponseLiveData;
    }

    public LiveData<MovieResponse> getUpcomingMovies(Integer pageNumber) {
        if (upcomingMovieResponseLiveData == null) {
            upcomingMovieResponseLiveData = new MutableLiveData<>();
        }
        upcomingMovieResponseLiveData = movieRepository.getUpComingMovies(pageNumber);
        return upcomingMovieResponseLiveData;
    }

    public LiveData<MovieDetail> getMovieDetailById(Integer movie_id) {
        if (movieDetailLiveData == null) {
            movieDetailLiveData = new MutableLiveData<>();
        }
        movieDetailLiveData = movieRepository.getMovieDetailById(movie_id);
        return movieDetailLiveData;
    }

    public static class MovieViewModelFactory implements ViewModelProvider.Factory {
        private final Application mApplication;
        private final Integer mParam;


        public MovieViewModelFactory(Application application, Integer param) {
            mApplication = application;
            mParam = param;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MovieViewModel(mApplication, mParam);
        }
    }
}
