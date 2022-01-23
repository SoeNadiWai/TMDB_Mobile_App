package com.soenadiwai.tmdbmobile.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soenadiwai.tmdbmobile.model.Movie;

import java.util.List;

public class MovieResponse {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    @SerializedName("total_pages")
    @Expose
    private Integer total_pages;

    @SerializedName("total_results")
    @Expose
    private Integer total_results;


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "Popular Movies{" +
                "page=" + page +
                ", movies=" + movies +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
