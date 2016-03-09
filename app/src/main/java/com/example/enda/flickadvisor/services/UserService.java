package com.example.enda.flickadvisor.services;

import com.example.enda.flickadvisor.models.User;

import io.realm.Realm;

/**
 * Created by enda on 18/02/16.
 */
public class UserService {

    private static final Realm realm = Realm.getDefaultInstance();

    public static void saveUser(User user) {
        if (getUserWithId(user.getId()) != null) {
            User old = getUserWithId(user.getId());
            removeUser(old);
        }
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public static User getCurrentUser() {
        User user = realm.where(User.class)
                .findFirst();
        return user == null ? null : user;
    }

    private static void removeUser(User user) {
        realm.beginTransaction();
        user.removeFromRealm();
        realm.commitTransaction();
    }

    private static User getUserWithId(Long id) {
        User user = realm.where(User.class)
                .equalTo("id", id)
                .findFirst();
        return user == null ? null : user;
    }

    public static boolean isLoggedIn() {
        User user = realm.where(User.class)
        .findFirst();
        return user != null;
    }

    public static void logout() {
        realm.beginTransaction();
        User user = realm.where(User.class)
                .findFirst();
        user.removeFromRealm();
        realm.commitTransaction();
    }
}
