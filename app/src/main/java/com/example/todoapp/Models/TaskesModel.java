package com.example.todoapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
@Entity(tableName = "Taskes")
public class TaskesModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String titile;
    @ColumnInfo(name = "details")
    private String details;
    @ColumnInfo(name = "date")
    private String date;
    private boolean ckecked;
    private boolean remender;



    public TaskesModel(String details, String date) {
        this.details = details;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCkecked() {
        return ckecked;
    }

    public void setCkecked(boolean ckecked) {
        this.ckecked = ckecked;
    }

    public boolean isRemender() {
        return remender;
    }

    public void setRemender(boolean remender) {
        this.remender = remender;
    }
}

