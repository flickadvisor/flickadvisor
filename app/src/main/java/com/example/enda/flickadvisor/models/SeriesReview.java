package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import java.util.Date;

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
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
