package com.example.enda.flickadvisor.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by enda on 17/02/16.
 */
public class UserMovie extends RealmObject {
    private Movie movie;
    private User user;
    private boolean isOnWatchList;
    private boolean isOnWatchedList;
    private boolean isHidden;
    private boolean isFavourite;
    private Date dateAdded;

    public UserMovie() {
    }

    public UserMovie(Movie movie, User user) {
        this.movie = movie;
        this.user = user;
        this.isOnWatchList = false;
        this.isOnWatchedList = false;
        this.isOnWatchList = false;
        this.isFavourite = false;
        this.dateAdded = new Date(System.currentTimeMillis());
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOnWatchList() {
        return isOnWatchList;
    }

    public void setIsOnWatchList(boolean isOnWatchList) {
        this.isOnWatchList = isOnWatchList;
    }

    public boolean isOnWatchedList() {
        return isOnWatchedList;
    }

    public void setIsOnWatchedList(boolean isOnWatchedList) {
        this.isOnWatchedList = isOnWatchedList;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
