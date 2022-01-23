package com.soenadiwai.tmdbmobile.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soenadiwai.tmdbmobile.model.Genre;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public String listToJsonGenreObject(List<Genre> value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Genre>>() {
        }.getType();
        return gson.toJson(value, type);
    }

    @TypeConverter
    public List<Genre> jsonToListGenreObject(String value) {
        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Genre>>() {
        }.getType();
        return gson.fromJson(value, type);
    }
}
