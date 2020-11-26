package com.example.todoapp.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;

import java.util.List;

@Dao
public interface TaskesDao {

    @Insert
    void addTask(TaskesModel task);
    @Insert
    void addnote(NotesModel note);

    @Query("SELECT * FROM Taskes")
    List<TaskesModel> getAllTaskes();
    @Query("SELECT * FROM Notes")
    List<NotesModel> getAllNotes();


    @Delete
    void deleteTask(TaskesModel task);
    @Delete
    void deleteNote(NotesModel note);

    @Update
    void updateTask(TaskesModel task);
    @Update
    void updateNote(NotesModel note);

}
