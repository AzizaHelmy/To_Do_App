package com.example.todoapp.Async2;

import android.os.AsyncTask;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

public class InsertAsyncTask extends AsyncTask<NotesModel, Void, Void> {

    private final TaskesDao taskessDao;

    public InsertAsyncTask(TaskesDao taskesDao) {
        this.taskessDao = taskesDao;
    }

    @Override
    protected Void doInBackground(NotesModel... notes) {
        taskessDao.addnote(notes[0]);
        return null;
    }
}
