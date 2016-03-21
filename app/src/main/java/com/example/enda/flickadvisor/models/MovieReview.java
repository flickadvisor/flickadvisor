package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import io.realm.MovieReviewRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { MovieReviewRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = MovieReview.class)
public class MovieReview extends RealmObject {
    @PrimaryKey
    private Long id;
    private UserTbl user;
    private long movieId;
    private float rating;
    private String description;
    private long date; // Realm is limiting me here, cannot parse long to date, using long and will format for display
//    private Date date;

    public MovieReview() {
    }

    public MovieReview(UserTbl user, long movieId, float rating, String description) {
        this.user = user;
        this.movieId = movieId;
        this.rating = rating;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
