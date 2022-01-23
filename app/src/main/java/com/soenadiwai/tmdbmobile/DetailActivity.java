package com.soenadiwai.tmdbmobile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.soenadiwai.tmdbmobile.fragment.MovieDetailFragment;
import com.soenadiwai.tmdbmobile.fragment.MovieListFragment;

public class DetailActivity extends AppCompatActivity {
    public static final String TYPE_KEY = "DETAIL_TYPE";
    public static DetailActivity instance;
    public static Fragment oldFragment;
    public static Fragment currentFragment;
    private Toolbar toolbar;
    private DETAILS_TYPE detailsType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_detail);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailsType = (DETAILS_TYPE) getIntent().getSerializableExtra(TYPE_KEY);
        Bundle bundle = getIntent().getExtras();

        if (detailsType == DETAILS_TYPE.MOVIE_LIST) {
            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setArguments(bundle);
            replaceFragment(movieListFragment);
        } else if (detailsType == DETAILS_TYPE.MOVIE_DETAIL) {
            MovieDetailFragment movieListFragment = new MovieDetailFragment();
            movieListFragment.setArguments(bundle);
            replaceFragment(movieListFragment);
        }
    }

    public void replaceFragment(Fragment fragment) {

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.replace(R.id.frame_replace, fragment);

        fragmentTransaction.commit();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        currentFragment = null;
        super.onBackPressed();
    }

    public enum DETAILS_TYPE {
        MOVIE_LIST, MOVIE_DETAIL
    }
}
