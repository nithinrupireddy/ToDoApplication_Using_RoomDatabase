package com.practice.todoapplicationudacity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private static AppDatabase mDb;
    private static int mTaskID;

    public AddTaskViewModelFactory(AppDatabase mDb,int mTaskID){
        this.mDb = mDb;
        this.mTaskID= mTaskID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDb,mTaskID);
    }
}
