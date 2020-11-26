package com.example.todoapp.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;

@Database(entities = {TaskesModel.class,NotesModel.class}, version = 1, exportSchema = false)
public abstract class TaskesDb extends RoomDatabase {

    public abstract TaskesDao getTaskesDao();
}
