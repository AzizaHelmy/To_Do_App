package com.example.todoapp.RoomDB;

import android.content.Context;

import androidx.room.Room;


public class RoomFactory {
    private static TaskesDb taskessDb;

    public static TaskesDb getTaskessDb(Context context) {

        if (taskessDb == null) {

           taskessDb = Room.databaseBuilder(context, TaskesDb.class, "Taskes_db")
                    .build();
        }


        return taskessDb;
    }
}
