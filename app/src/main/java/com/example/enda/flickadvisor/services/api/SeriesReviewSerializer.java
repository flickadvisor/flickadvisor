package com.example.enda.flickadvisor.services.api;

import com.example.enda.flickadvisor.models.SeriesReview;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class SeriesReviewSerializer implements JsonSerializer<SeriesReview> {
    @Override
    public JsonElement serialize(SeriesReview src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("movieId", src.getSeriesId());
        jsonObject.addProperty("user", String.valueOf(src.getUser()));
        jsonObject.addProperty("rating", src.getRating());
        jsonObject.addProperty("description", src.getDescription());
        jsonObject.addProperty("date", src.getDate());
        return null;
    }
}
