package com.tenakatauniversity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tenakatauniversity.studentapplication.StudentApplication;
import com.tenakatauniversity.studentapplication.StudentApplicationDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {StudentApplication.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public StudentApplicationDao studentApplicationDao;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                    .build();
        }
        return INSTANCE;
    }

}
