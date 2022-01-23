package com.soenadiwai.tmdbmobile.fragment;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.IMAGE_BASE_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.soenadiwai.tmdbmobile.R;
import com.soenadiwai.tmdbmobile.database.AppDatabase;
import com.soenadiwai.tmdbmobile.database.dao.MovieDetailDao;
import com.soenadiwai.tmdbmobile.model.Genre;
import com.soenadiwai.tmdbmobile.model.MovieDetail;
import com.soenadiwai.tmdbmobile.viewmodel.MovieViewModel;

public class MovieDetailFragment extends Fragment {
    private View mainLayout;
    private TextView movieTitleTextView;
    private TextView releasedDateTextView;
    private TextView overViewTextView;
    private ImageView backdropImageView;
    private ImageView posterImageView;
    private TextView movieLengthTextView;
    private ProgressBar progressBar;
    private RatingBar ratingBar;
    private ToggleButton favBtn;
    private LinearLayout llGenreLayout;
    private int movieID;
    private AppDatabase database;
    private MovieViewModel movieViewModel;
    private MovieDetail movieDetailObject;
    private MovieDetailDao movieDetailDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainLayout = inflater.inflate(R.layout.movie_detail_layout, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Movie Detail");
        movieID = getArguments().getInt("movie_id");
        init();
        getMovieById();
        favBtn.setChecked(checkIsFavorite(movieID));
        clickListeners();
        return mainLayout;
    }

    private void getMovieById() {
        movieViewModel.getMovieDetailById(movieID).observe(getViewLifecycleOwner(), movieDetail -> {
            if (movieDetail != null) {
                movieDetailObject = movieDetail;
                updateUI(movieDetail);
            }
        });
    }

    private void updateUI(MovieDetail movieDetail) {
        progressBar.setVisibility(View.GONE);
        movieTitleTextView.setText(movieDetail.getTitle());
        overViewTextView.setText(movieDetail.getOverview());
        releasedDateTextView.setText(movieDetail.getRelease_date());
        movieLengthTextView.setText(movieDetail.getRuntime().toString() + " minutes");
        Glide.with(this)
                .load(IMAGE_BASE_URL + movieDetail.getBackdrop_path())
                .into(backdropImageView);

        Glide.with(this)
                .load(IMAGE_BASE_URL + movieDetail.getPoster_path())
                .into(posterImageView);

        float a = movieDetail.getPopularity().intValue();
        float d = (float) ((a * 5) / 100);
        ratingBar.setRating(d);

        for (Genre genre : movieDetail.getGenres()) {
            TextView tv_dynamic = new TextView(this.getContext());
            tv_dynamic.setTextSize(13f);
            tv_dynamic.setTextColor(this.getContext().getResources().getColor(R.color.black));
            tv_dynamic.setPadding(8, 8, 8, 8);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 10, 0, 10);
            tv_dynamic.setLayoutParams(params);

            tv_dynamic.setBackgroundResource(R.drawable.dashboard_drawable);
            tv_dynamic.setText(genre.getName());
            llGenreLayout.addView(tv_dynamic);
        }
    }

    private boolean checkIsFavorite(Integer movieID) {
        MovieDetail movieDetail = movieDetailDao.getFavMovieById(movieID);
        return movieDetail != null;
    }

    private void clickListeners() {
        favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    Long result = movieDetailDao.insertFavMovie(movieDetailObject);
                    if (result > 0) {
                        Toast.makeText(getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int result = movieDetailDao.deleteFavMovie(movieDetailObject.getId());
                    if (result > 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void init() {
        movieTitleTextView = mainLayout.findViewById(R.id.movie_NameTxt);
        backdropImageView = mainLayout.findViewById(R.id.backdrop_imageView);
        posterImageView = mainLayout.findViewById(R.id.poster_imageView);
        overViewTextView = mainLayout.findViewById(R.id.movie_OverviewBodyTxt);
        movieLengthTextView = mainLayout.findViewById(R.id.movie_TimeTxt);
        releasedDateTextView = mainLayout.findViewById(R.id.movie_ReleaseDateTxt);
        favBtn = mainLayout.findViewById(R.id.add_favBtn);
        ratingBar = mainLayout.findViewById(R.id.ratingBar);
        llGenreLayout = mainLayout.findViewById(R.id.ll_genre_layout);
        progressBar = mainLayout.findViewById(R.id.progressbar);
        movieViewModel = ViewModelProviders.of(this, new MovieViewModel.MovieViewModelFactory(this.getActivity().getApplication(), 1)).get(MovieViewModel.class);
        database = AppDatabase.getInstance(getContext());
        movieDetailDao = database.movieDetailDao();
    }
}
