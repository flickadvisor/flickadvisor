package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.SeriesReviewRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { SeriesReviewRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = SeriesReview.class)
public class SeriesReview extends RealmObject {
    @PrimaryKey
    private Long id;
    private float rating;
    private String description;
    private long date;
    private UserTbl user;
    private Long seriesId;

    public SeriesReview() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
