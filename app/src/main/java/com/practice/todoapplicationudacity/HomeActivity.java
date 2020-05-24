package com.practice.todoapplicationudacity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.practice.todoapplicationudacity.databinding.ActivityHomeBinding;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;


public class HomeActivity extends AppCompatActivity  implements TaskAdapter.onListItemClickListener{

    private ActivityHomeBinding homeBinding;
    private AppDatabase mDb;
    private TaskAdapter myadapter;
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        mDb =AppDatabase.getInstance(getApplicationContext());

        homeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        myadapter = new TaskAdapter(this,this);
        homeBinding.recyclerView.setAdapter(myadapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        homeBinding.recyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  final RecyclerView.ViewHolder viewHolder, int direction) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntry> tasks = myadapter.getAllTasks();
                        mDb.taskDao().Delete(tasks.get(position));
                    }
                });

            }
        }).attachToRecyclerView(homeBinding.recyclerView);


        homeBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddTask.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    settingUpViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void settingUpViewModel() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> taskEntries) {
                Log.d(TAG,"Updating list of tasks from LiveData in Viewmodel");
                myadapter.setTasks(taskEntries);
            }
        });
    }

    @Override
    public void onListItemClick(int itemID) {
        Intent intent = new Intent(HomeActivity.this,AddTask.class);
        intent.putExtra(AddTask.EXTRA_TASK_ID,itemID);
        startActivity(intent);
    }
}
