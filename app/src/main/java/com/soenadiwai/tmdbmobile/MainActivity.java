package com.soenadiwai.tmdbmobile;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.FAVORITE;
import static com.soenadiwai.tmdbmobile.constant.AppConstant.POPULAR;
import static com.soenadiwai.tmdbmobile.constant.AppConstant.TOP_RATED;
import static com.soenadiwai.tmdbmobile.constant.AppConstant.UPCOMING;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.soenadiwai.tmdbmobile.adapter.MovieAdapter;
import com.soenadiwai.tmdbmobile.model.Genre;
import com.soenadiwai.tmdbmobile.model.Movie;
import com.soenadiwai.tmdbmobile.viewmodel.GenreViewModel;
import com.soenadiwai.tmdbmobile.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ChipGroup genreChips;
    private ProgressBar progressBar;
    private Button showFavListBtn;
    private LinearLayout topratedShowAllLayout;
    private LinearLayout popularShowAllLayout;
    private LinearLayout upcomingShowAllLayout;
    private MovieViewModel movieViewModel;
    private GenreViewModel genreViewModel;
    private final ArrayList<Genre> genreArrayList = new ArrayList<>();
    private final ArrayList<Movie> topratedMovieList = new ArrayList<>();
    private final ArrayList<Movie> popularMovieList = new ArrayList<>();
    private final ArrayList<Movie> upcomingMovieList = new ArrayList<>();
    private RecyclerView topRatedMovieRV;
    private RecyclerView popularMovieRV;
    private RecyclerView upcomingMovieRV;
    private MovieAdapter topratedMovieAdapter;
    private MovieAdapter popularMovieAdapter;
    private MovieAdapter upcomingMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getMovies();
        clickListeners();

    }

    public void getMovies() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                getMovieGenres();
                getTopRatedMovies();
                getPopularMovies();
                getUpComingMovies();
                progressBar.setVisibility(View.GONE);
            }
        }, 1500);
    }

    private void init() {
        topratedShowAllLayout = findViewById(R.id.toprated_movie_showall_layout);
        popularShowAllLayout = findViewById(R.id.popular_movie_showall_layout);
        upcomingShowAllLayout = findViewById(R.id.upcoming_movie_showall_layout);
        genreChips = findViewById(R.id.chipsGenres);
        progressBar = findViewById(R.id.progress_bar);
        topRatedMovieRV = findViewById(R.id.toprated_recyclerview);
        popularMovieRV = findViewById(R.id.popularmovie_recyclerview);
        upcomingMovieRV = findViewById(R.id.upcomingmovie_recyclerview);
        showFavListBtn = findViewById(R.id.favoriteListBtn);

        topRatedMovieRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        popularMovieRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        upcomingMovieRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        topRatedMovieRV.setHasFixedSize(true);
        popularMovieRV.setHasFixedSize(true);
        upcomingMovieRV.setHasFixedSize(true);

        topratedMovieAdapter = new MovieAdapter(MainActivity.this, topratedMovieList, "top-rated");
        popularMovieAdapter = new MovieAdapter(MainActivity.this, popularMovieList, "popular");
        upcomingMovieAdapter = new MovieAdapter(MainActivity.this, upcomingMovieList, "popular");

        topRatedMovieRV.setAdapter(topratedMovieAdapter);
        popularMovieRV.setAdapter(popularMovieAdapter);
        upcomingMovieRV.setAdapter(upcomingMovieAdapter);
        movieViewModel = ViewModelProviders.of(this, new MovieViewModel.MovieViewModelFactory(getApplication(), 3)).get(MovieViewModel.class);
        genreViewModel = ViewModelProviders.of(this).get(GenreViewModel.class);
    }

    private void getMovieGenres() {
        genreViewModel.getGenreResponseLiveData().observe(this, genreResponse -> {
            if (genreResponse != null && genreResponse.getGenres() != null && !genreResponse.getGenres().isEmpty()) {
                List<Genre> genreList = genreResponse.getGenres();
                genreArrayList.addAll(genreList);
                setGenreChips(genreArrayList);
            }
        });
    }

    private void getTopRatedMovies() {
        movieViewModel.getTopRatedMovies(1).observe(this, movieResponse -> {
            if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                List<Movie> movieList = movieResponse.getMovies();
                topratedMovieList.addAll(movieList);
                topratedMovieAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getPopularMovies() {
        movieViewModel.getPopularMovies(1).observe(this, movieResponse -> {
            if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                List<Movie> movieList = movieResponse.getMovies();
                popularMovieList.addAll(movieList);
                popularMovieAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getUpComingMovies() {
        movieViewModel.getUpcomingMovies(1).observe(this, movieResponse -> {
            if (movieResponse != null && movieResponse.getMovies() != null && !movieResponse.getMovies().isEmpty()) {
                progressBar.setVisibility(View.GONE);
                List<Movie> movieList = movieResponse.getMovies();
                upcomingMovieList.addAll(movieList);
                upcomingMovieAdapter.notifyDataSetChanged();
            }
        });
    }

    private void clickListeners() {
        showFavListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", FAVORITE);
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_LIST);
                startActivity(goToMovieListIntent);
            }
        });

        topratedShowAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", TOP_RATED);
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_LIST);
                startActivity(goToMovieListIntent);
            }
        });
        popularShowAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", POPULAR);
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_LIST);
                startActivity(goToMovieListIntent);
            }
        });
        upcomingShowAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", UPCOMING);
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_LIST);
                startActivity(goToMovieListIntent);
            }
        });
        topratedMovieAdapter.setmItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("movie_id", topratedMovieList.get(position).getId());
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_DETAIL);
                startActivity(goToMovieListIntent);
            }
        });

        popularMovieAdapter.setmItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("movie_id", popularMovieList.get(position).getId());
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_DETAIL);
                startActivity(goToMovieListIntent);
            }
        });

        upcomingMovieAdapter.setmItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("movie_id", upcomingMovieList.get(position).getId());
                Intent goToMovieListIntent = new Intent(MainActivity.this, DetailActivity.class);
                goToMovieListIntent.putExtras(bundle);
                goToMovieListIntent.putExtra(DetailActivity.TYPE_KEY, DetailActivity.DETAILS_TYPE.MOVIE_DETAIL);
                startActivity(goToMovieListIntent);
            }
        });
    }


    public void setGenreChips(ArrayList<Genre> genres) {
        for (Genre genre :
                genres) {
            Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_choice_layout, null, false);
            mChip.setText(genre.getName());
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            mChip.setPadding(paddingDp, 0, paddingDp, 0);
            mChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    Log.e("oncheckchanged",compoundButton.getText().toString());
                }
            });
            genreChips.addView(mChip);
        }
    }
}