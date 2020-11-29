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
    @ColumnInfo(name = "checked")
    private boolean ckecked;
    @ColumnInfo(name = "remender")
    private boolean remender;
    @ColumnInfo(name = "crossOut")
    private boolean crossOut;

    public TaskesModel(String details, String date, boolean ckecked, boolean remender, boolean crossOut) {
        this.details = details;
        this.date = date;
        this.ckecked = ckecked;
        this.remender = remender;
        this.crossOut = crossOut;
    }


    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
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

