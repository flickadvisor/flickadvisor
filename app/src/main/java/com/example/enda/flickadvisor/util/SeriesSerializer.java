package com.example.enda.flickadvisor.util;

import com.example.enda.flickadvisor.models.Series;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class SeriesSerializer implements JsonSerializer<Series> {
    @Override
    public JsonElement serialize(Series src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("title", src.getTitle());
        jsonObject.addProperty("overview", src.getOverview());
        jsonObject.addProperty("language", src.getLanguage());
        jsonObject.addProperty("episodeRuntime", src.getEpisodeRuntime());
        jsonObject.addProperty("backdropPath", src.getBackdropPath());
        jsonObject.addProperty("firstAirDate", String.valueOf(src.getFirstAirDate()));
        jsonObject.addProperty("lastAirDate", String.valueOf(src.getLastAirDate()));
        jsonObject.addProperty("numberOfEpisodes", src.getNumberOfEpisodes());
        jsonObject.addProperty("numberOfSeasons", src.getNumberOfSeasons());
        jsonObject.addProperty("popularity", src.getPopularity());
        jsonObject.addProperty("posterPath", src.getPosterPath());
        jsonObject.addProperty("status", src.getStatus());
        jsonObject.addProperty("voteAverage", src.getVoteAverage());
        jsonObject.addProperty("voteCount", src.getVoteCount());
        return jsonObject;
    }
}
