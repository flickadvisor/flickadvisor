package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.Series;

import org.parceler.Parcels;

/**
 * Created by enda on 27/03/16.
 */
public class SeriesListConverter extends RealmListParcelConverter<Series> {

    @Override
    public void itemToParcel(Series input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Series itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Series.class.getClassLoader()));
    }
}
