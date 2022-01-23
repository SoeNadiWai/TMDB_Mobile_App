package com.soenadiwai.tmdbmobile.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soenadiwai.tmdbmobile.model.Genre;

import java.util.List;

public class GenreResponse {

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genres=" + genres +
                '}';
    }
}
