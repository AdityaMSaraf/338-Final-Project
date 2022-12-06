package com.example.finalproject.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalproject.CartItem;
import com.example.finalproject.Item;
import com.example.finalproject.User;


@Database(entities = {User.class, Item.class, CartItem.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Database.db";
    public static final String USER_TABLE = "USER";
    public static final String ITEM_TABLE = "ITEM";
    public static final String CART_TABLE = "CART";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract AppDAO AppDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
