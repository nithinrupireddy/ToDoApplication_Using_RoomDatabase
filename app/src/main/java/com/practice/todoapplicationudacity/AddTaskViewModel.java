package com.practice.todoapplicationudacity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;
    public AddTaskViewModel(AppDatabase mDb, int mTaskID) {
       task = mDb.taskDao().loadTaskByID(mTaskID);
    }
    LiveData<TaskEntry> getTask(){
        return task;
    }
}
