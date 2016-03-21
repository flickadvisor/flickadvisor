package com.example.enda.flickadvisor.util;

import com.example.enda.flickadvisor.models.UserTbl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by enda on 21/03/16.
 */
public class UserSerializer implements JsonSerializer<UserTbl> {
    @Override
    public JsonElement serialize(UserTbl src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("email", src.getEmail());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("password", src.getPassword());
        return jsonObject;
    }
}
