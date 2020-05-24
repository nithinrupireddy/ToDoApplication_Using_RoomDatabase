package com.practice.todoapplicationudacity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private final Context context;
    private List<TaskEntry> mEntries;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private onListItemClickListener onListClick;



    public TaskAdapter(Context context,onListItemClickListener onListClick)
    {
        this.context = context;
        this.onListClick = onListClick;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_task_item,parent,false);
        return new TaskHolder(view,onListClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        TaskEntry taskEntry = mEntries.get(position);
        String updatedAT = dateFormat.format(taskEntry.getUpdatedAt());
        int priority = taskEntry.getPriority();
        String priorityString = ""+priority;
        holder.taskname.setText(taskEntry.getDescription());
        holder.date.setText(updatedAT);
        holder.priority.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priority.getBackground();
        int priorityColor = getPriority(priority);
        priorityCircle.setColor(priorityColor);

    }

    private int getPriority(int priority) {
        int priorityColor = 0;
        switch (priority){
            case 1:
                priorityColor = ContextCompat.getColor(context,R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(context,R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(context,R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if(mEntries == null)
            return 0;
        else
           return mEntries.size();
    }



    public void setTasks(List<TaskEntry> loadAllTasks) {
        mEntries = loadAllTasks;
        notifyDataSetChanged();
    }

    public List<TaskEntry> getAllTasks() {
        return mEntries;
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskname,date,priority;
        onListItemClickListener onListclick;


        public TaskHolder(@NonNull View itemView,onListItemClickListener onListClick) {
            super(itemView);
            taskname = itemView.findViewById(R.id.taskname);
            date = itemView.findViewById(R.id.date);
            priority= itemView.findViewById(R.id.priority);
            this.onListclick = onListClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId= mEntries.get(getAdapterPosition()).getId();
            onListclick.onListItemClick(elementId);
        }
    }

    public interface onListItemClickListener {
       void onListItemClick(int position);
    }
}
