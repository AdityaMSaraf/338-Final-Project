package com.example.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.finalproject.DB.AppDatabase;

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int mItemID;
    String mProduct;
    Integer mPrice;
    Integer mStock;
    int mImage;

    public Item(String product, Integer price, Integer stock, int image) {
        mProduct = product;
        mPrice = price;
        mStock = stock;
        mImage = image;
    }

    public int getItemID() {
        return mItemID;
    }

    public void setItemID(int itemID) {
        mItemID = itemID;
    }

    public Integer getStock() {
        return mStock;
    }

    public void setStock(Integer stock) {
        mStock = stock;
    }

    public String getProduct() {
        return mProduct;
    }

    public void setProduct(String product) {
        mProduct = product;
    }

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer price) {
        mPrice = price;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}
