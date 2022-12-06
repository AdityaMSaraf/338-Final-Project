cspackage com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.DB.AppDAO;
import com.example.finalproject.DB.AppDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserField;
    private EditText mPassField;
    private Button mLogin;
    private Button mCreate;
    private Button mDelete;
    private AppDAO mAppDAO;

    private String mUsername;
    private String mPassword;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getDB();

        mUserField = findViewById(R.id.username);
        mPassField = findViewById(R.id.password);
        mLogin = findViewById(R.id.loginBtn);
        mCreate = findViewById(R.id.createBtn);
        mDelete = findViewById(R.id.deleteBtn);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                if (checkForUserInDB()) {
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "WRONG PASSWORD", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getID());
                        startActivity(intent);
                    }
                }
            }
        });

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateUser.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                deleteUser();
                Toast.makeText(LoginActivity.this, "User Deleted!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    private void deleteUser(){
        mUser = mAppDAO.getUserByUserName(mUsername);
        mAppDAO.delete(mUser);
    }

    private void getValues() {

        mUsername = mUserField.getText().toString();
        mPassword = mPassField.getText().toString();

        if (mUsername.equals("LET ME IN") && mPassword.equals("LET ME INN")) {
            Intent intent = EasterEgg1.intentFactory(getApplicationContext());
            startActivity(intent);
        }
        if (mUsername.equals("DONT OPEN") && mPassword.equals("SECRET")) {
            Intent intent = Secret.intentFactory(getApplicationContext());
            startActivity(intent);
        }
    }

    private void getDB() {
        mAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build().AppDAO();
    }

    private boolean checkForUserInDB() {
        mUser = mAppDAO.getUserByUserName(mUsername);
        if (mUser == null) {
            Toast.makeText(this, "USER NOT FOUND", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

}