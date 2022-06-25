package com.example.spacefight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    MediaPlayer background_music;
    boolean isMusic = false;
    static Difficulty.Level difficultyLevel = Difficulty.Level.medium;
    int musicMode;
    String musicStatus;
    Button soundButton;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        background_music = MediaPlayer.create(this, R.raw.background_music);
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Log.d("onCreate SP", sharedPreferences.getString("music mode", "kaki"));
        soundButton = findViewById(R.id.soundButton);
        loadMusicStatus(this);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMusic = !isMusic;
                if (isMusic){
                    background_music.start();
                    musicMode = R.string.soundOff;
                    sharedPreferences.edit().putString("music mode", "on").apply();
                    Log.d("sharedPreferences", sharedPreferences.getString("music mode", "kaki"));
                }
                else{
                    background_music.pause();
                    musicMode = R.string.soundOn;
                    sharedPreferences.edit().putString("music mode", "off").apply();
                    Log.d("sharedPreferences", sharedPreferences.getString("music mode", "kaki"));
                }
                soundButton.setText(musicMode);
            }
        });
    }

    public void loadMusicStatus(StartUp v){
        musicStatus = sharedPreferences.getString("music mode", "on");
        isMusic = musicStatus.equals("on");

        if(isMusic) {
            musicMode = R.string.soundOff;
            background_music.start();
        }
        else{
            musicMode = R.string.soundOn;
        }
        soundButton.setText(musicMode);
    }

    public void difficultyMenu(View view){
        startActivity(new Intent(this, DifficultyMenu.class));
        finish();
    }

    public void startGame(View view){
        background_music.pause();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}