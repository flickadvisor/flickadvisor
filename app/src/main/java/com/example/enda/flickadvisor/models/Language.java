package com.example.enda.flickadvisor.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by enda on 31/03/16.
 */
public class Language {

    @SerializedName("iso_639_1")
    private String iso6391;
    private String name;

    public Language() {
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
