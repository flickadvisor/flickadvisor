package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.UserSeriesRealmProxy;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { UserSeriesRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = UserSeries.class)
public class UserSeries extends RealmObject {
    private Series series;
    private UserTbl user;
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

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
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
