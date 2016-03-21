package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.SeriesReview;

import org.parceler.Parcels;

public class SeriesReviewListConverter extends RealmListParcelConverter<SeriesReview> {

    @Override
    public void itemToParcel(SeriesReview input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public SeriesReview itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(SeriesReview.class.getClassLoader()));
    }
}
