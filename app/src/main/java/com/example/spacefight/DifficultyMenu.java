package com.example.spacefight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DifficultyMenu extends AppCompatActivity {
    Button backBtn;
    Button easyBtn, mediumBtn, hardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_levels);

        backBtn = findViewById(R.id.backBtn);
        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);

        //Setting onClick listeners.
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPrevActivity();
            }
        });
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUp.difficultyLevel = Difficulty.Level.easy;
                Toast.makeText(DifficultyMenu.this, "The difficulty is easy", Toast.LENGTH_SHORT).show();
            }
        });
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUp.difficultyLevel = Difficulty.Level.medium;
                Toast.makeText(DifficultyMenu.this, "The difficulty is normal", Toast.LENGTH_SHORT).show();
            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DifficultyMenu.this, "The difficulty is hard", Toast.LENGTH_SHORT).show();
                StartUp.difficultyLevel = Difficulty.Level.hard;
            }
        });
    }

    public void backToPrevActivity(){
        Intent intent = new Intent(DifficultyMenu.this, StartUp.class);
        DifficultyMenu.this.startActivity(intent);
    }
}