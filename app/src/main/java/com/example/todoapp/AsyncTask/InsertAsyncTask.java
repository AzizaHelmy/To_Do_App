package com.example.todoapp.AsyncTask;

import android.os.AsyncTask;

import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

public class InsertAsyncTask extends AsyncTask<TaskesModel, Void, Void> {

    private final TaskesDao taskessDao;

    public InsertAsyncTask(TaskesDao taskesDao) {
        this.taskessDao = taskesDao;
    }

    @Override
    protected Void doInBackground(TaskesModel... taskes) {
        taskessDao.addTask(taskes[0]);
        return null;
    }
}
