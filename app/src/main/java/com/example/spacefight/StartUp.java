package com.example.spacefight;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    MediaPlayer background_music;
    boolean musicPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        startMusic(this);
        Button soundButton = findViewById(R.id.soundButton);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPauseMusic(view);
                if (!musicPaused){
                    Log.d("hi", "onClick: changeText");
                    soundButton.setText(R.string.musicOff);
                }
                else{
                    soundButton.setText(R.string.musicOn);
                }
            }
        });
    }

    public void startMusic(StartUp v){
        if (background_music == null){
            background_music = MediaPlayer.create(this, R.raw.caves_of_dawn_10376);
        }
        background_music.start();
    }

    public void startPauseMusic(View view){
        if(musicPaused){
            musicPaused = false;
            background_music.start();
        }
        else{
            musicPaused = true;
            background_music.pause();
        }
    }

    public void difficultyMenu(View view){
        startActivity(new Intent(this, DifficultyMenu.class));
        finish();
    }

    public void startGame(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}