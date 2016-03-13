package com.example.enda.flickadvisor.services;

import com.example.enda.flickadvisor.models.UserTbl;

import io.realm.Realm;

/**
 * Created by enda on 18/02/16.
 */
public class UserRealmService {

    private static final Realm realm = Realm.getDefaultInstance();

    public static void saveUser(UserTbl user) {
        if (getUserWithId(user.getId()) != null) {
            UserTbl old = getUserWithId(user.getId());
            removeUser(old);
        }
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public static UserTbl getCurrentUser() {
        UserTbl user = realm.where(UserTbl.class)
                .findFirst();
        return user == null ? null : user;
    }

    private static void removeUser(UserTbl user) {
        realm.beginTransaction();
        user.removeFromRealm();
        realm.commitTransaction();
    }

    private static UserTbl getUserWithId(Long id) {
        UserTbl user = realm.where(UserTbl.class)
                .equalTo("id", id)
                .findFirst();
        return user == null ? null : user;
    }

    public static boolean isLoggedIn() {
        UserTbl user = realm.where(UserTbl.class)
        .findFirst();
        return user != null;
    }

    public static void logout() {
        realm.beginTransaction();
        UserTbl user = realm.where(UserTbl.class)
                .findFirst();
        user.removeFromRealm();
        realm.commitTransaction();
    }
}
