package com.example.enda.flickadvisor.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by enda on 21/03/16.
 */
public class Credentials {
    @Getter @Setter private String email;
    @Getter @Setter private String password;

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
