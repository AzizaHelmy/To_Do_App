package com.example.todoapp.AsyncTask;

import android.os.AsyncTask;

import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

public class UpdateAsyncTask extends AsyncTask<TaskesModel, Void, Void> {

    TaskesDao taskesDao;



    public UpdateAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected Void doInBackground(TaskesModel... taskes) {
        taskesDao.updateTask(taskes[0]);
        return null;
    }
}
