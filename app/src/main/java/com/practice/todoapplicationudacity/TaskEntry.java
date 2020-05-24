package com.practice.todoapplicationudacity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @OnConflictStrategy
    private int id;

    @ColumnInfo(name="Description")
    private String description;

    @ColumnInfo(name="Priority")
    private int priority;

    @ColumnInfo(name="UpdatedAt")
    private Date updatedAt;

    @Ignore
    public TaskEntry(String description,int priority,Date updatedAt){
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    public TaskEntry(int id,String description,int priority,Date updatedAt){
        this.id=id;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
