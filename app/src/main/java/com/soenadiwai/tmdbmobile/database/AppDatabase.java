package com.soenadiwai.tmdbmobile.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.soenadiwai.tmdbmobile.database.dao.MovieDetailDao;
import com.soenadiwai.tmdbmobile.model.MovieDetail;

@Database(entities = {MovieDetail.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDetailDao movieDetailDao();

    private AppDatabase appDatabase;

    private static final String DB_NAME = "movie_database.db";

    private static AppDatabase db;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = buildDatabaseInstance(context);
        }
        return db;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class,
                DB_NAME).allowMainThreadQueries().build();
    }

}