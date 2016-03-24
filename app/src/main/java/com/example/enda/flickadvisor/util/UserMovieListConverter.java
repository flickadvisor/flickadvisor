package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.UserMovie;

import org.parceler.Parcels;

/**
 * Created by enda on 23/03/16.
 */
public class UserMovieListConverter extends RealmListParcelConverter<UserMovie> {
    @Override
    public void itemToParcel(UserMovie input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public UserMovie itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(UserMovie.class.getClassLoader()));
    }
}
