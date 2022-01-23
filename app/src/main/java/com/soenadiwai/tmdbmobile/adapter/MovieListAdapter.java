package com.soenadiwai.tmdbmobile.adapter;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soenadiwai.tmdbmobile.R;
import com.soenadiwai.tmdbmobile.model.Movie;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private ArrayList<Movie> movieArrayList;
    private LoaderViewHolder loaderViewHolder;
    protected boolean showLoader = false;
    private static final int VIEWTYPE_ITEM = 1;
    private static final int VIEWTYPE_LOADER = 2;
    private OnItemClickListener mItemClickListener;

    public MovieListAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_LOADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_item_layout, parent, false);
            return new LoaderViewHolder(view);
        } else {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielist_item_layout, parent, false);

            return new MovieViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LoaderViewHolder) {

            LoaderViewHolder loaderViewHolder = (LoaderViewHolder) holder;

            this.loaderViewHolder = loaderViewHolder;
            if (showLoader) {
                loaderViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.mProgressBar.setVisibility(View.GONE);
            }

            return;
        } else {
            final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            Movie movie = movieArrayList.get(position);
            movieViewHolder.titleTextView.setText(movie.getTitle());

            float a = movie.getPopularity().intValue();
            float d = (float) ((a * 5) / 100);
            movieViewHolder.ratingBar.setRating(d);
            movieViewHolder.genreTextView.setText(movie.getOverview());
            Glide.with(context)
                    .load(IMAGE_BASE_URL + movie.getPoster_path())
                    .into(movieViewHolder.imageView);

            movieViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mItemClickListener != null) {

                        mItemClickListener.onItemClick(v, position);

                    }
                }
            });
        }

    }


    @Override
    public int getItemViewType(int position) {

        if (position != 0 && position == getItemCount() - 1) {

            if (movieArrayList != null && movieArrayList.size() != 0) {
                return VIEWTYPE_LOADER;
            } else {
                return VIEWTYPE_ITEM;
            }
        }
        return VIEWTYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if (movieArrayList == null || movieArrayList.size() == 0) {
            return 0;
        } else {
            return movieArrayList.size() + 1;
        }
    }

    public void setShowLoader(boolean showLoader) {

        this.showLoader = showLoader;

        if (loaderViewHolder != null) {

            if (showLoader) {

                loaderViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {

                loaderViewHolder.mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        View view;
        private final ImageView imageView;
        private final TextView titleTextView;
        private final TextView genreTextView;
        private final RatingBar ratingBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = itemView.findViewById(R.id.movieImage_imageView);
            titleTextView = itemView.findViewById(R.id.movieTitle_textView);
            genreTextView = itemView.findViewById(R.id.genre_textView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    public class LoaderViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar mProgressBar;

        public View mView;

        public LoaderViewHolder(View itemView) {

            super(itemView);

            mView = itemView;

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }
    }

}
