package com.practice.todoapplicationudacity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.practice.todoapplicationudacity.databinding.ActivityAddBinding;

import java.util.Date;

public class AddTask extends AppCompatActivity {

    public static final String EXTRA_TASK_ID ="extraTaskID";
    private static final int DEFAULT_TASK_ID = -1;
    public static final String INSTANCE_TASK_ID ="instanceTaskID";
    private ActivityAddBinding addTaskBinding;
    public static final int PRIORITY_HIGH=1;
    public static final int PRIORITY_MEDIUM=2;
    public static final int PRIORITY_LOW=3;
    private static final String TAG = AddTask.class.getSimpleName();
    private AppDatabase mDb;
    private int mTaskID = DEFAULT_TASK_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTaskBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(addTaskBinding.getRoot());

        mDb = AppDatabase.getInstance(getApplicationContext());

        if(savedInstanceState!=null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTaskID = savedInstanceState.getInt(INSTANCE_TASK_ID,DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(EXTRA_TASK_ID)){
            addTaskBinding.save.setText("Update");
            if(mTaskID == DEFAULT_TASK_ID){
                mTaskID = intent.getIntExtra(EXTRA_TASK_ID,DEFAULT_TASK_ID);
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb,mTaskID);
                final AddTaskViewModel viewModel = ViewModelProviders.of(this,factory).get(AddTaskViewModel.class);

                viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(TaskEntry taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        Log.d(TAG,"Receiving database update from LiveData of AddTaskViewModel");
                        populateUI(taskEntry);
                    }
                });
            }
        }


        addTaskBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validate()){
                    onSaveButtonClicked();
                }else{
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateUI(TaskEntry task) {
        if(task==null)
        {Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            addTaskBinding.addDesc.setText(task.getDescription());
            setPriorityInViews(task.getPriority());
        }

    }

    private boolean Validate() {
        int checkedid=((RadioGroup) addTaskBinding.radiogroup).getCheckedRadioButtonId();
        if(addTaskBinding.addDesc.getText().toString()==null || TextUtils.isEmpty(addTaskBinding.addDesc.getText().toString())){
            addTaskBinding.addDesc.setError("Please add task");
            addTaskBinding.addDesc.requestFocus();
            return false;
        }else if(checkedid==-1)
        {
            Toast.makeText(getApplicationContext(),"Please select priority",Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

    private void onSaveButtonClicked() {
        String description = addTaskBinding.addDesc.getText().toString().trim();
        int priority = getPriority();
        Date date = new Date();
        final TaskEntry taskEntry = new TaskEntry(description,priority,date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mTaskID==DEFAULT_TASK_ID){
                    mDb.taskDao().InsertTask(taskEntry);
                }else
                {
                    taskEntry.setId(mTaskID);
                    mDb.taskDao().Update(taskEntry);
                }
                finish();
            }
        });
    }

    private int getPriority(){
        int priority =1;
        int checkedId = ((RadioGroup) addTaskBinding.radiogroup).getCheckedRadioButtonId();
        switch(checkedId)
        {
            case R.id.highRadioButton:
                priority = PRIORITY_HIGH;
                break;
            case R.id.mediumRadioButton:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.lowRadioButton:
                priority = PRIORITY_LOW;
                break;
        }
        return priority;
    }

    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) addTaskBinding.radiogroup).check(R.id.highRadioButton);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) addTaskBinding.radiogroup).check(R.id.mediumRadioButton);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) addTaskBinding.radiogroup).check(R.id.lowRadioButton);
                break;
        }
    }
}
