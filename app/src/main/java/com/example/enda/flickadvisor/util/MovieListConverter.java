package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.Movie;

import org.parceler.Parcels;

/**
 * Created by enda on 27/03/16.
 */
public class MovieListConverter extends RealmListParcelConverter<Movie> {

    @Override
    public void itemToParcel(Movie input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Movie itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Movie.class.getClassLoader()));
    }
}
