package com.example.enda.flickadvisor.services;

import android.content.Context;

import com.example.enda.flickadvisor.models.User;

import io.realm.Realm;

/**
 * Created by enda on 18/02/16.
 */
public class UserService {

    private Realm realm;
    private Context context;

    public UserService(Context context) {
        this.context = context;
        this.realm = Realm.getInstance(context);
    }

    public void saveUser(User user) {
        if (getUserWithId(user.getId()) != null) {
            User old = getUserWithId(user.getId());
            removeUser(old);
        }
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    private void removeUser(User user) {
        realm.beginTransaction();
        user.removeFromRealm();
        realm.commitTransaction();
    }

    private User getUserWithId(Long id) {
        User user = realm.where(User.class)
                .equalTo("id", id)
                .findFirst();
        return user == null ? null : user;
    }

    public boolean isLoggedIn() {
        int count = realm.where(User.class)
                .findAll().size();
        return count >= 1;
    }

}
