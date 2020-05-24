package com.practice.todoapplicationudacity;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntry.class},version = 3,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {


    private static final String LOG_TAG = AppDatabase.class.getName();
    private static final Object LOCK = new Object();
    private static  AppDatabase mInstance;
    private static final String Databasename="todolist";

    public static AppDatabase getInstance(Context context)
    {
        if(mInstance ==null)
        {
            synchronized (LOCK)
            {
                Log.d(LOG_TAG,"Creating Database Instance");
                mInstance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,
                        AppDatabase.Databasename)
                        //we will temporarily allow queries
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG,"getting database instance");
        return mInstance;
    }


    public abstract TaskDao taskDao();

}
