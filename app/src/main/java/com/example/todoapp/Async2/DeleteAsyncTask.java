package com.example.todoapp.Async2;

import android.os.AsyncTask;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.RoomDB.TaskesDao;


public class DeleteAsyncTask extends AsyncTask<NotesModel, Void, Void> {

    TaskesDao taskesDao;

    public DeleteAsyncTask(TaskesDao taskesDao) {
        this.taskesDao = taskesDao;
    }

    @Override
    protected Void doInBackground(NotesModel... notes) {
        taskesDao.deleteNote(notes[0]);
        return null;
    }
}
