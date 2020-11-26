package com.example.todoapp.Async2;

import android.os.AsyncTask;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

import java.util.List;

public class GetAsyncTask extends AsyncTask<Void, Void, List<NotesModel>> {


    private final TaskesDao taskesDao;

    public GetAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected List<NotesModel> doInBackground(Void... voids) {
        return taskesDao.getAllNotes();
    }
}
