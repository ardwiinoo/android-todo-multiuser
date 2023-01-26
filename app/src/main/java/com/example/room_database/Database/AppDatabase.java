package com.example.room_database.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.room_database.Database.Dao.AllDao;
import com.example.room_database.Database.Entity.TaskModel;
import com.example.room_database.Database.Entity.UserModel;

@Database(entities = {UserModel.class, TaskModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AllDao getMyDao();

    public static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "myDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}
