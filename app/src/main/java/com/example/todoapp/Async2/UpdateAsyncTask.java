package com.example.todoapp.Async2;

import android.os.AsyncTask;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;

public class UpdateAsyncTask extends AsyncTask<NotesModel, Void, Void> {

    TaskesDao taskesDao;



    public UpdateAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected Void doInBackground(NotesModel... notes) {
        taskesDao.updateNote(notes[0]);
        return null;
    }
}
