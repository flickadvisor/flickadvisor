package com.example.enda.flickadvisor.util;

import android.os.Parcel;

import com.example.enda.flickadvisor.models.MovieReview;

import org.parceler.Parcels;

public class MovieReviewListConverter extends RealmListParcelConverter<MovieReview> {

    @Override
    public void itemToParcel(MovieReview input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public MovieReview itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(MovieReview.class.getClassLoader()));
    }
}

