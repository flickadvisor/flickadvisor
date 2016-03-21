package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.Genre;

import org.parceler.Parcels;

/**
 * Created by enda on 21/03/16.
 */
public class GenreListConverter extends RealmListParcelConverter<Genre> {
    
    @Override
    public void itemToParcel(Genre input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Genre itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Genre.class.getClassLoader()));
    }
}