package com.example.enda.flickadvisor.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserTblRealmProxy;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by enda on 17/02/16.
 */
@Parcel(implementations = { UserTblRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = UserTbl.class)
public class UserTbl extends RealmObject {
    @PrimaryKey
    private Long id;
    private String name;
    @Required
    private String email;
    @Ignore
    private String password;

    public UserTbl() {
    }

    public UserTbl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserTbl(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
