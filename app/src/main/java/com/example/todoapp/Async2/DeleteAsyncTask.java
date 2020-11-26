package com.example.todoapp.Async2;

import android.os.AsyncTask;

import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;


public class DeleteAsyncTask extends AsyncTask<TaskesModel, Void, Void> {

    TaskesDao taskesDao;

    public DeleteAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected Void doInBackground(TaskesModel... taskes) {
        taskesDao.deleteTask(taskes[0]);
        return null;
    }
}
