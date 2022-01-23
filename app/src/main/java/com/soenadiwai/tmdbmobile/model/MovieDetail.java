package com.soenadiwai.tmdbmobile.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "favorite_movie_table")
public class MovieDetail {

    @ColumnInfo(name = "movie_id")
    @SerializedName("movie_id")
    @Expose
    private Integer movie_id;

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    private String backdrop_path;

    @SerializedName("budget")
    @Expose
    private Integer budget;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("imdb_id")
    @Expose
    private String imdb_id;

    @SerializedName("original_language")
    @Expose
    private String original_language;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    @Expose
    private String original_title;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Expose
    private String overview;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    @Expose
    private Float popularity;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    private String poster_path;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    private String release_date;

    @SerializedName("revenue")
    @Expose
    private Integer revenue;

    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private Float vote_average;

    @SerializedName("vote_count")
    @Expose
    private Integer vote_count;

    @ColumnInfo(name = "is_Favorite")
    @SerializedName("is_Favorite")
    @Expose
    private Boolean is_Favorite;

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getIs_Favorite() {
        return is_Favorite;
    }

    public void setIs_Favorite(Boolean is_Favorite) {
        this.is_Favorite = is_Favorite;
    }

    @Override
    public String toString() {
        return "MovieDetail{" +
                "movie_id=" + movie_id +
                ", adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", imdb_id='" + imdb_id + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", is_Favorite=" + is_Favorite +
                '}';
    }
}
