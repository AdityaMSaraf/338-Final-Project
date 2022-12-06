package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;

public class Secret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        VideoView videoView = findViewById(R.id.SHH);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.secret;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.start();

//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, Secret.class);
        return  intent;
    }
}