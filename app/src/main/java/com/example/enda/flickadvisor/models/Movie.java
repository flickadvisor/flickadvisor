package com.example.enda.flickadvisor.models;

import com.google.gson.JsonObject;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 12/03/16.
 */
@Parcel(implementations = { Movie.class },
        value = Parcel.Serialization.BEAN,
        analyze = Movie.class)
public class Movie extends RealmObject {
    @PrimaryKey
    private Long id;
    private String title;
    private String imdbId;
    private String language;
    private boolean adult;
    private String overview;
    private String backdropPath;
    private String posterPath;
    private Date releaseDate;
    private double popularity;
    private int runtime;
    private String status;
    private float voteAverage;
    private long voteCount;
    private RealmList<MovieReview> reviews;
    private RealmList<Genre> genres;

    public Movie() {
    }

    public Movie(JsonObject json) {
        this.id = json.get("id").getAsLong();
        this.title = json.get("original_title").getAsString();
        this.imdbId = json.get("imdb_id") == null ? null : json.get("imdb_id").getAsString();
//        this.language = json.get("original_language").getAsString();
        this.language = json.get("spoken_languages").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        this.adult = json.get("adult").getAsBoolean();
        this.overview = json.get("overview").getAsString();
        this.backdropPath = "http://image.tmdb.org/t/p/w185" + json.get("backdrop_path").getAsString();
        this.posterPath = "http://image.tmdb.org/t/p/w185" + json.get("poster_path").getAsString();
        //region this.releaseDate = json.get("release_date").getDate();
        String dateString = json.get("release_date").getAsString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            this.releaseDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //endregion
        this.popularity = json.get("popularity").getAsDouble();
        this.runtime = json.get("runtime").getAsInt();
        this.status = json.get("status").getAsString();
        this.voteAverage = json.get("vote_average").getAsFloat();
        this.voteCount = json.get("vote_count").getAsLong();
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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public RealmList<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public RealmList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<Genre> genres) {
        this.genres = genres;
    }
}