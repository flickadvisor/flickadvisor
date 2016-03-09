package com.example.enda.flickadvisor.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
//@Parcel(value = Parcel.Serialization.BEAN, analyze = { MovieReview.class })
public class MovieReview extends RealmObject {
    @PrimaryKey
    private Long id;
    private float rating;
    private String description;
    private long date; // Realm is limiting me here, cannot parse long to date, using long and will format for display
//    private Date date;

    public MovieReview() {
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
}
