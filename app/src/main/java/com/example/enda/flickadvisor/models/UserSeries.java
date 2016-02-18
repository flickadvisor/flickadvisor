package com.example.enda.flickadvisor.models;

import java.util.Date;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by enda on 17/02/16.
 */
public class UserSeries extends RealmObject {
    private Series series;
    private User user;
    private boolean onWatchList;
    private boolean onWatchedList;
    private boolean hidden;
    private Date dateAdded;

    public UserSeries() {
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void setOnWatchList(boolean onWatchList) {
        this.onWatchList = onWatchList;
    }

    public boolean isOnWatchedList() {
        return onWatchedList;
    }

    public void setOnWatchedList(boolean onWatchedList) {
        this.onWatchedList = onWatchedList;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
