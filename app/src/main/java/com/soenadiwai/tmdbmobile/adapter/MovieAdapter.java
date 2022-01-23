package com.soenadiwai.tmdbmobile.adapter;

import static com.soenadiwai.tmdbmobile.constant.AppConstant.IMAGE_BASE_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soenadiwai.tmdbmobile.R;
import com.soenadiwai.tmdbmobile.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Movie> movieArrayList;
    private String type;
    private OnItemClickListener mItemClickListener;


    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList, String type) {
        this.context = context;
        this.movieArrayList = movieArrayList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type.equalsIgnoreCase("popular")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_item_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.releasedDateTextView.setText(movie.getRelease_date());
        Glide.with(context)
                .load(IMAGE_BASE_URL + movie.getPoster_path())
                .into(holder.imageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItemClickListener != null) {

                    mItemClickListener.onItemClick(v, position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        private final ImageView imageView;
        private final TextView titleTextView;
        private final TextView releasedDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = itemView.findViewById(R.id.movieImage_imageView);
            titleTextView = itemView.findViewById(R.id.movieTitle_textView);
            releasedDateTextView = itemView.findViewById(R.id.releasedDate_textView);
        }
    }

}
