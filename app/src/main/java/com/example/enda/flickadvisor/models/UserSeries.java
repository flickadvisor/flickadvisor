package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.UserSeriesRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { UserSeriesRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = UserSeries.class)
public class UserSeries extends RealmObject {
    @PrimaryKey
    private Long id;
    private long seriesId;
    private User user;
    private boolean isOnWatchList;
    private boolean isOnWatchedList;
    private boolean isHidden;
    private boolean isFavourite;
    private Date dateAdded;

    public UserSeries() {
    }

    public UserSeries(long seriesId, User user) {
        this.seriesId = seriesId;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
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
