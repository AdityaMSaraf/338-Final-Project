package com.example.finalproject.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.finalproject.CartItem;
import com.example.finalproject.Item;
import com.example.finalproject.User;

import java.util.List;

@Dao
public interface AppDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM "+AppDatabase.USER_TABLE)
    List<User> getUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :mUser")
    User getUserByUserName(String mUser);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mID = :id")
    User getUserByUserID(int id);

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE )
    List<Item> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mStock > 0")
    List<Item> getInStockItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mItemID = :id")
    Item getItemByID(int id);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mProduct = :name")
    Item getItemByName(String name);

    @Insert
    void insert(CartItem... items);

    @Update
    void update(CartItem... items);

    @Delete
    void delete(CartItem item);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE mUsername = :user AND mOrdered = 0")
    List<CartItem> getCartItemsByUsername(String user);

    @Query("INSERT INTO " + AppDatabase.CART_TABLE + " VALUES (:no, :prod, :img, :user, :price , 'false')")
    void insertCartItem(int no, String prod, int img, String user, int price);

    @Query("SELECT * FROM " +AppDatabase.CART_TABLE + " WHERE mOrdered = 1")
    List<CartItem> getOrders();
}
