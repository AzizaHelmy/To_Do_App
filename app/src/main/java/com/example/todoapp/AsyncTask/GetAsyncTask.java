package com.example.todoapp.AsyncTask;

import android.os.AsyncTask;

import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

import java.util.List;

public class GetAsyncTask extends AsyncTask<Void, Void, List<TaskesModel>> {


    private final TaskesDao taskesDao;

    public GetAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected List<TaskesModel> doInBackground(Void... voids) {
        return taskesDao.getAllTaskes();
    }
}
