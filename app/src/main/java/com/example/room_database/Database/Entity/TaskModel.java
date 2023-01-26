package com.example.room_database.Database.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int idTask;
    private int userId;
    private String taskName;

    public TaskModel(int id_task, int userId, String taskName) {
        this.idTask = id_task;
        this.userId = userId;
        this.taskName = taskName;
    }

    public TaskModel() {}

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
