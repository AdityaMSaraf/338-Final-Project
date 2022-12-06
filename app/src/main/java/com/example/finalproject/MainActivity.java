package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalproject.DB.AppDAO;
import com.example.finalproject.DB.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    public static final String UID_KEY = "com.example.finalproject.UserID";
    public static final String PREFERENCES_KEY = "com.example.finalproject.PREFKEY";
    private SharedPreferences mPreferences = null;

    private AppDAO mAppDAO;
    private int mUserID = -1;
    private User mUser;
    private List<Item> items;
    MenuItem admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDB();
        checkForUser();
        loginUser(getIntent().getIntExtra(UID_KEY, -1));
        checkItems();
        addUserToPref(getIntent().getIntExtra(UID_KEY, -1));
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter(getApplicationContext(), items, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_btn, menu);
        admin = menu.findItem(R.id.person);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutBtn:
                logoutUser();
                return true;
            case R.id.cart:
                openCart();
                return true;
            case R.id.person:
                openAdmin();
                return true;
            case R.id.articles:
                openArticles();
                return true;
            case R.id.video:
                openVideo();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void openAdmin(){
        Intent intent = AdminActivity.intentFactory(getApplicationContext(), mUser.getUsername());
        startActivity(intent);
    }


    private void loginUser(int userID){
        if(userID == -1){

        }else{
            mUser = mAppDAO.getUserByUserID(userID);
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDB(){
        mAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build().AppDAO();
    }

    private void openCart(){
        Intent intent = CartActivity.intentFactory(this, mUser.getUsername());
        startActivity(intent);
    }

    private void openArticles(){
        openUrl("https://www.theverge.com/");
    }

    private void openVideo(){
        openUrl("https://www.youtube.com/channel/UCXuqSBlHAE6Xw-yeJA0Tunw");
    }

    public void openUrl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void logoutUser(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to log out?");
        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearPref();
                clearIntent();
                mUserID = -1;
                checkForUser();
            }
        });
        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }

    private void clearPref(){
        addUserToPref(-1);
    }
    private void addUserToPref(int UID){
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(UID_KEY, UID);
    }

    private void clearIntent(){
        getIntent().putExtra(UID_KEY, -1);
    }

    private void checkForUser(){
        mUserID = getIntent().getIntExtra(UID_KEY, -1);
        if(mUserID != -1){
            return;
        }
        if(mPreferences == null){
            getPrefs();
        } else {
            mUserID = mPreferences.getInt(UID_KEY, -1);
        }
        if(mUserID !=-1){
            return;
        }

        if(mUserID == -1){
            List<User> users = mAppDAO.getUsers();
            if(users.isEmpty()){
                User admin = new User("admin", "admin");
                User altUser = new User("thanos", "balance");
                mAppDAO.insert(admin, altUser);
            }

            Intent intent = LoginActivity.intentFactory(this);
            startActivity(intent);

        }
    }


    private void checkItems(){
        if(items == null){
            items = mAppDAO.getAllItems();
            if (items.size()<=0){
                Item mRTX = new Item("NVIDIA RTX 4090", 1499, 10, R.drawable.rtx4090);
                Item mRadeon = new Item("AMD Radeon 7900XTX", 999, 10, R.drawable.r7900xtx);
                Item mRyzen = new Item("AMD Ryzen 9 7950X", 579, 3, R.drawable.amd);
                Item mIntel = new Item("Intel Core i9 13900K", 529, 1, R.drawable.intel);
                Item mARC = new Item("Intel ARC A770", 329, 0, R.drawable.a770);
                mAppDAO.insert(mRTX, mRadeon, mRyzen, mIntel, mARC);
            }
        }
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY , Context.MODE_PRIVATE);
    }

    public static Intent intentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(UID_KEY, userID);
        return intent;
    }

    @Override
    public void onItemClick(int position) {

        Item item = items.get(position);
        item.setStock(item.getStock()-1);
        mAppDAO.update(item);
        if(item.getStock() <=0){
            Toast.makeText(this, item.getProduct() + " is out of stock :/", Toast.LENGTH_SHORT).show();
        }else{
            CartItem mCartItem = new CartItem(item.getProduct(), item.getImage(), mUser.getUsername(), item.getPrice(), false);
            mAppDAO.insert(mCartItem);
            Toast.makeText(this, item.getProduct()+" added to cart!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if(mUser.getUsername().equals("admin")){
            Item item = items.get(position);
            item.setStock(0);
            Toast.makeText(this, item.getProduct() + " set as out of stock!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Only admins can mark items out of stock", Toast.LENGTH_SHORT).show();
        }
    }
}