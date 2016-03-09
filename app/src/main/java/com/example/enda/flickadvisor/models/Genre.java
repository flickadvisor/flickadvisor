package com.example.enda.flickadvisor.models;

import com.google.gson.JsonObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by enda on 17/02/16.
 */
//@Parcel(value = Parcel.Serialization.BEAN, analyze = { Genre.class })
public class Genre extends RealmObject {
    @PrimaryKey
    private Long id;
    @Required
    private String name;
    private RealmList<Movie> movies;
    private RealmList<Series> series;

    public Genre() {
    }

    public Genre(JsonObject json) {
        this.id = json.get("id").getAsLong();
        this.name = json.get("name").getAsString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(RealmList<Movie> movies) {
        this.movies = movies;
    }

    public RealmList<Series> getSeries() {
        return series;
    }

    public void setSeries(RealmList<Series> series) {
        this.series = series;
    }

}
