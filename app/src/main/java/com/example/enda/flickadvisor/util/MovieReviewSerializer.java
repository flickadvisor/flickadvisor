package com.example.enda.flickadvisor.util;

import com.example.enda.flickadvisor.models.MovieReview;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class MovieReviewSerializer implements JsonSerializer<MovieReview> {
    @Override
    public JsonElement serialize(MovieReview src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("movieId", src.getMovieId());
        jsonObject.addProperty("user", String.valueOf(src.getUser()));
        jsonObject.addProperty("rating", src.getRating());
        jsonObject.addProperty("description", src.getDescription());
        jsonObject.addProperty("date", src.getDate());
        return jsonObject;
    }
}
