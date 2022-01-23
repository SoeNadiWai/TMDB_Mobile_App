package com.soenadiwai.tmdbmobile.fragment;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.POPULAR;
import static com.soenadiwai.tmdbmobile.constant.AppConstant.TOP_RATED;
import static com.soenadiwai.tmdbmobile.constant.AppConstant.UPCOMING;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soenadiwai.tmdbmobile.DetailActivity;
import com.soenadiwai.tmdbmobile.R;
import com.soenadiwai.tmdbmobile.adapter.MovieListAdapter;
import com.soenadiwai.tmdbmobile.database.AppDatabase;
import com.soenadiwai.tmdbmobile.database.dao.MovieDetailDao;
import com.soenadiwai.tmdbmobile.interfaces.RecyclerViewScrollListener;
import com.soenadiwai.tmdbmobile.model.Movie;
import com.soenadiwai.tmdbmobile.model.MovieDetail;
import com.soenadiwai.tmdbmobile.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {

    private final ArrayList<Movie> movieList = new ArrayList<>();
    private View mainLayout;
    private ProgressBar progressBar;
    private RecyclerView movieListRV;
    private MovieListAdapter movieListAdapter;
    private MovieViewModel movieViewModel;
    private TextView noMovieTextView;
    private String movieType;
    private boolean isLoading = false;
    private LinearLayoutManager layoutManager;
    private Integer pageNumber = 1;
    private AppDatabase database;
    private MovieDetailDao movieDetailDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainLayout = inflater.inflate(R.layout.movie_list_layout, null);
        movieType = getArguments().getString("type");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movieType);
        init();
        getMoviesByType();
        clickListeners();
        return mainLayout;
    }

    private void getMoviesByType() {
        if (movieType.equalsIgnoreCase(TOP_RATED)) {
            movieViewModel.getTopRatedMovies(pageNumber).observe(getViewLifecycleOwner(), movieResponse -> {
                if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                    List<Movie> movieresponseList = movieResponse.getMovies();
                    movieList.addAll(movieresponseList);
                    movieListAdapter.notifyDataSetChanged();
                    addScrollerListener();
                }
            });
        } else if (movieType.equalsIgnoreCase(POPULAR)) {
            movieViewModel.getPopularMovies(pageNumber).observe(getViewLifecycleOwner(), movieResponse -> {
                if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                    List<Movie> movieresponseList = movieResponse.getMovies();
                    movieList.addAll(movieresponseList);
                    movieListAdapter.notifyDataSetChanged();
                    addScrollerListener();
                }
            });
        } else if (movieType.equalsIgnoreCase(UPCOMING)) {
            movieViewModel.getUpcomingMovies(pageNumber).observe(getViewLifecycleOwner(), movieResponse -> {
                if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                    List<Movie> movieresponseList = movieResponse.getMovies();
                    movieList.addAll(movieresponseList);
                    movieListAdapter.notifyDataSetChanged();
                    addScrollerListener();
                }
            });
        } else {
            List<MovieDetail> movieDetailList = movieDetailDao.getAllFavMovies();
            List<Movie> tempMovieList = new ArrayList<>();
            for (MovieDetail movieDetail : movieDetailList) {
                Movie movie = new Movie();
                movie.setId(movieDetail.getId());
                movie.setTitle(movieDetail.getTitle());
                movie.setPopularity(movieDetail.getPopularity());
                movie.setOverview(movieDetail.getOverview());
                movie.setPoster_path(movieDetail.getPoster_path());
                tempMovieList.add(movie);
            }
            movieList.addAll(tempMovieList);
            progressBar.setVisibility(View.GONE);
            if (movieList.size() == 0) {
                noMovieTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addScrollerListener() {
        movieListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == movieList.size() - 1) {
                        pageNumber++;
                        getMoviesByType();
                        isLoading = true;
                    }
                }
            }
        });
        movieListRV.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {
            }

            @Override
            public void onScrollDown() {
            }

            @Override
            public void onLoadMore() {
                movieListAdapter.setShowLoader(true);
                loadmore();
            }

        });
    }

    public void loadmore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                pageNumber++;
                getMoviesByType();
                movieListAdapter.notifyDataSetChanged();
                movieListAdapter.setShowLoader(false);
            }
        }, 1500);
    }

    private void clickListeners() {
        movieListAdapter.setmItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("movie_id", movieList.get(position).getId());
                Intent goToMovieListIntent = new Intent(getContext(), DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_DETAIL);
                startActivity(goToMovieListIntent);
            }
        });
    }

    private void init() {
        noMovieTextView = mainLayout.findViewById(R.id.no_movie_textview);
        movieListRV = mainLayout.findViewById(R.id.movielist_recyclerview);
        progressBar = mainLayout.findViewById(R.id.progress_bar);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        movieListAdapter = new MovieListAdapter(this.getContext(), movieList);
        movieListRV.setLayoutManager(layoutManager);
        movieListRV.setHasFixedSize(true);
        movieListRV.setAdapter(movieListAdapter);
        movieViewModel = ViewModelProviders.of(this, new MovieViewModel.MovieViewModelFactory(this.getActivity().getApplication(), 1)).get(MovieViewModel.class);
        database = AppDatabase.getInstance(getContext());
        movieDetailDao = database.movieDetailDao();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
