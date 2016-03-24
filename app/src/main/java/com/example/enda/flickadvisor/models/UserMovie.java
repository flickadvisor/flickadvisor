package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.UserMovieRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { UserMovieRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = UserMovie.class)
public class UserMovie extends RealmObject implements Serializable {
    @PrimaryKey
    private long id;
    private long movieId;
    private UserTbl user;
    private boolean isOnWatchList;
    private boolean isOnWatchedList;
    private boolean isHidden;
    private boolean isFavourite;
    private Date dateAdded;

    public UserMovie() {
    }

    public UserMovie(long movieId, UserTbl user) {
        this.movieId = movieId;
        this.user = user;
        this.isOnWatchList = false;
        this.isOnWatchedList = false;
        this.isOnWatchList = false;
        this.isFavourite = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
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

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }
}
