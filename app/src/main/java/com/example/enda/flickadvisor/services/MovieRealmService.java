package com.example.enda.flickadvisor.services;

import com.example.enda.flickadvisor.models.UserMovie;

import io.realm.Realm;

/**
 * Created by enda on 09/03/16.
 */
public class MovieRealmService {

    private static final Realm realm = Realm.getDefaultInstance();

    public static void saveUserMovie(UserMovie userMovie) {
        realm.beginTransaction();
        realm.copyToRealm(userMovie);
        realm.commitTransaction();
    }
}
