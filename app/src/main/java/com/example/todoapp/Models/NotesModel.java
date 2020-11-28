package com.example.todoapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "Notes")
public class NotesModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "title")
    private String titile;
    @ColumnInfo(name = "date")
    private String date;
    private int optionMenu;

    public NotesModel(String titile,String content,  String date) {
        this.titile = titile;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOptionMenu() {
        return optionMenu;
    }

    public void setOptionMenu(int optionMenu) {
        this.optionMenu = optionMenu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
