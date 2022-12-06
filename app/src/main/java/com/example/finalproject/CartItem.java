package com.example.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalproject.DB.AppDatabase;

@Entity(tableName = AppDatabase.CART_TABLE)
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int mID;
    String mProduct;
    int mImageCart;

    String mUsername;
    Integer mPrice;
    boolean mOrdered;

    public CartItem(String product, int imageCart, String username, Integer price, boolean ordered) {
        mProduct = product;
        mImageCart = imageCart;
        mOrdered = ordered;
        mUsername = username;
        mPrice = price;

    }

    public int getImageCart() {
        return mImageCart;
    }

    public void setImageCart(int imageCart) {
        mImageCart = imageCart;
    }

    public String getProduct() {
        return mProduct;
    }

    public void setProduct(String product) {
        mProduct = product;
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

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer price) {
        mPrice = price;
    }

    public boolean isOrdered() {
        return mOrdered;
    }

    public void setOrdered(boolean ordered) {
        mOrdered = ordered;
    }
}
