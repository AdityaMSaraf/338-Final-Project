package com.example.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalproject.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mID;

    private String mUsername;
    private String mPassword;


    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


}
