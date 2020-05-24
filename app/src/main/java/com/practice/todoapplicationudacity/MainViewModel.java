package com.practice.todoapplicationudacity;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;



public class MainViewModel extends AndroidViewModel {
      private static final String TAG = MainViewModel.class.getSimpleName();
      LiveData<List<TaskEntry>> tasks;
      private AppDatabase mDb;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG,"Actively retriving data from database");
        mDb = AppDatabase.getInstance(this.getApplication());
        tasks= mDb.taskDao().loadAllTasks();
    }
    public LiveData<List<TaskEntry>> getTasks(){
        return tasks;
    }
}
