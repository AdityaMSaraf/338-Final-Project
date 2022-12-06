package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DB.AppDAO;
import com.example.finalproject.DB.AppDatabase;

import java.util.List;

public class CreateUser extends AppCompatActivity {

    private TextView mError;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirm;
    private Button mCreate;

    private String mUserString;
    private String mPassString;
    private String mConfirmString;

    private AppDAO mAppDAO;
    private User mUser;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        getDB();

        mError = findViewById(R.id.errorText);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mConfirm = findViewById(R.id.passwordConfirm);
        mCreate = findViewById(R.id.createBtn1);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                if(checkForUserExistInDB()){
                    if(!validatePassword()){
                        mError.setText("Passwords don't match");
                    } else{
                        User user = new User(mUserString, mPassString);
                        mAppDAO.insert(user);
                        Toast.makeText(CreateUser.this, "User successfully created!", Toast.LENGTH_SHORT).show();
                        Intent intent = LoginActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean checkForUserExistInDB(){
        mUser = mAppDAO.getUserByUserName(mUserString);
        if(mUser == null){
            return true;
        }
        mError.setText("Username already exists");
        return false;
    }

    private boolean validatePassword(){
        return mPassString.equals(mConfirmString);
    }

    private void getValues(){
        mUserString = mUsername.getText().toString();
        mPassString = mPassword.getText().toString();
        mConfirmString = mConfirm.getText().toString();
        if(mUserString.equals("Darth Vader")){
            mCreate.setBackgroundColor(Color.RED);
            mCreate.setTextColor(Color.BLACK);
        } else if(mUserString.equals("Pickle Rick")){
            mCreate.setBackgroundColor(Color.GREEN);
            mCreate.setTextColor(Color.BLACK);
        }
    }

    private void getDB(){
        mAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build().AppDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, CreateUser.class);
        return intent;
    }
}