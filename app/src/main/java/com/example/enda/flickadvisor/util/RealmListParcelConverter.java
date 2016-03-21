package com.example.enda.flickadvisor.util;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by enda on 21/03/16.
 */
// https://gist.github.com/cmelchior/72c35fcb55cec33a71e1
public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
    @Override
    public RealmList<T> createCollection() {
        return new RealmList<>();
    }
}

