package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.DB.AppDAO;
import com.example.finalproject.DB.AppDatabase;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private static final String USERNAME = "USERNAME";
    StringBuilder sb = new StringBuilder();
    TextView mTitle, mOrders;
    Button mExit;
    private AppDAO mAppDAO;
    List<CartItem> orders;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getDB();
        orders = getOrders();

        String userName = getIntent().getStringExtra(USERNAME);
        mUser = mAppDAO.getUserByUserName(userName);


        mTitle = findViewById(R.id.admin);
        mOrders = findViewById(R.id.orders);
        mExit = findViewById(R.id.exit);

        sb.append("Current orders are: \n");

        for(CartItem order : orders){
            sb.append(order.getProduct());
            sb.append(" ordered by user ");
            sb.append(order.getUsername());
            sb.append(" at price $" +order.getPrice());
            sb.append("\n");
        }

        if(userName.equals("admin")){
            mOrders.setText(sb.toString());
        } else {
            mTitle.setText("Only admins can view orders");
            mOrders.setText("Error not an admin \n Currently logged in as "+ mUser.getUsername());
        }


        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getID());
                startActivity(intent);
            }
        });
    }

    private void getDB(){
        mAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build().AppDAO();
    }

    private List<CartItem> getOrders(){
        return mAppDAO.getOrders();
    }

    public static Intent intentFactory(Context context, String username){
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(USERNAME, username);
        return intent;
    }


}