package com.example.enda.flickadvisor.util;

import com.example.enda.flickadvisor.models.Movie;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class MovieSerializer implements JsonSerializer<Movie> {
    @Override
    public JsonElement serialize(Movie src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("title", src.getTitle());
        jsonObject.addProperty("imdbId", src.getImdbId());
        jsonObject.addProperty("language", src.getLanguage());
        jsonObject.addProperty("adult", src.isAdult());
        jsonObject.addProperty("overview", src.getOverview());
        jsonObject.addProperty("backdropPath", src.getBackdropPath());
        jsonObject.addProperty("posterPath", src.getPosterPath());
        jsonObject.addProperty("releaseDate", Long.valueOf(String.valueOf(src.getReleaseDate())));
        jsonObject.addProperty("popularity", src.getPopularity());
        jsonObject.addProperty("runtime", src.getRuntime());
        jsonObject.addProperty("status", src.getStatus());
        jsonObject.addProperty("voteAverage", src.getVoteAverage());
        jsonObject.addProperty("voteCount", src.getVoteCount());
        return jsonObject;
    }
}
