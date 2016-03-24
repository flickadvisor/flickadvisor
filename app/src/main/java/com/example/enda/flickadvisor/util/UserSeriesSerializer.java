package com.example.enda.flickadvisor.util;

import com.example.enda.flickadvisor.models.UserSeries;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class UserSeriesSerializer implements JsonSerializer<UserSeries> {
    @Override
    public JsonElement serialize(UserSeries src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("seriesId", src.getSeriesId());
//        jsonObject.addProperty("user", new Gson().toJson(src.getUser()));
        jsonObject.addProperty("isOnWatchList", src.isOnWatchList());
        jsonObject.addProperty("isOnWatchedList", src.isOnWatchedList());
        jsonObject.addProperty("isHidden", src.isHidden());
        jsonObject.addProperty("isFavourite", src.isFavourite());
        jsonObject.addProperty("dateAdded", String.valueOf(src.getDateAdded()));
        return jsonObject;
    }
}