package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SeriesRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { SeriesRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = Series.class)
public class Series extends RealmObject {
    @PrimaryKey
    private Long id;
    private String title;
    private String overview;
    private String language;
    private double episodeRuntime;
    private String backdropPath;
    private Date firstAirDate;
    private Date lastAirDate;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private double popularity;
    private String posterPath;
    private String status;
    private double voteAverage;
    private int voteCount;
    private RealmList<SeriesReview> reviews;
    private RealmList<Genre> genres;

    public Series() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(double episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Date getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Date getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(Date lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public RealmList<SeriesReview> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<SeriesReview> reviews) {
        this.reviews = reviews;
    }

    public RealmList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<Genre> genres) {
        this.genres = genres;
    }
}
