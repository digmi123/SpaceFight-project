package com.example.spacefight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DifficultyMenu extends AppCompatActivity {
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_levels);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPrevActivity();
            }
        });
    }

    public void backToPrevActivity(){
        Intent intent = new Intent(DifficultyMenu.this, StartUp.class);
        DifficultyMenu.this.startActivity(intent);
    }
}
