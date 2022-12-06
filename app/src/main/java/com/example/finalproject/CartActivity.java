package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalproject.DB.AppDAO;
import com.example.finalproject.DB.AppDatabase;

import java.util.List;

public class CartActivity extends AppCompatActivity implements RecyclerViewInterface{
    CartAdapter adapter;
    private AppDAO mAppDAO;
    private static final String UNAME = "UNAME";
    List<CartItem> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.cartRecycler);

        getDB();
        getCart();

        adapter = new CartAdapter(this, items, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getCart(){
        String username = getIntent().getStringExtra(UNAME);
        items = mAppDAO.getCartItemsByUsername(username);
    }

    private void getDB(){
        mAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build().AppDAO();
    }

    public static Intent intentFactory(Context context, String username){
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(UNAME, username);
        return intent;
    }


    @Override
    public void onItemClick(int position) {
        CartItem order = items.get(position);
        order.setOrdered(true);
        mAppDAO.update(order);
        Toast.makeText(this, order.getProduct() + " Ordered", Toast.LENGTH_SHORT).show();
        items.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemLongClick(int position) {
        CartItem cancel = items.get(position);
        mAppDAO.delete(cancel);
        Toast.makeText(this, cancel.getProduct() + " removed from cart", Toast.LENGTH_SHORT).show();
        items.remove(position);
        adapter.notifyItemRemoved(position);
    }
}