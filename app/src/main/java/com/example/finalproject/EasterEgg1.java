package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class EasterEgg1 extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg1);

        img = findViewById(R.id.imageView2);

    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, EasterEgg1.class);
        return intent;
    }
}