package com.example.spacefight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class GameOver extends AppCompatActivity {
    EditText playerNameTExtTest;
    Button saveBtn, scoreBoardBtn;
    TextView tvPoints;
    ArrayList<scoreModel> scoresArrayList;
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        boolean isNewScoreGreaterThanMin =  scoresArrayList.size() < 10 || (points > scoresArrayList.get(scoresArrayList.size() - 1).playerScore);
        if (isNewScoreGreaterThanMin) {
            setContentView(R.layout.game_over_add_to_scoreboard);
            saveBtn = findViewById(R.id.saveBtn);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (playerNameTExtTest.getText().toString() != "" ){
                        saveData(playerNameTExtTest.getText().toString(), points);
                    }
                }
            });
        }
        else{
            setContentView(R.layout.game_over);
        }

        scoreBoardBtn = findViewById(R.id.scoreBoardBtn);
        playerNameTExtTest = findViewById(R.id.playerName);


        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);

        //if the points greater than minimum score in list: player can enter name and save his score.

        scoreBoardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, ScoreBoard.class);
                GameOver.this.startActivity(intent);
            }
        });
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
    }

    public void loadData() {
        points = getIntent().getExtras().getInt("points");
        SharedPreferences sharedPreferences =  getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<scoreModel>>() {}.getType();

        // in below line we are getting data from gson and saving it to our array list
        scoresArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (scoresArrayList == null) {
            // if the array list is empty creating a new array list.
            scoresArrayList = new ArrayList<scoreModel>();
        }
    }

    private void saveData(String name, int score) {

        Log.d("saveData", "saved the data "+name+", "+score);

       scoreModel newPlayerScore = new scoreModel(name, score);
       if (scoresArrayList.size() == 10){
           scoresArrayList.set(scoresArrayList.size() - 1, newPlayerScore);
       }
       else{
           scoresArrayList.add(newPlayerScore);
       }
        Log.d("added data to arrayList", ""+scoresArrayList);


        // method for saving the data in array list.
        // creating a variable for storing data in shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        //sort the arrayList:
        Collections.sort(scoresArrayList);

        // getting data from gson and storing it in a string.
        String scoresJson = gson.toJson(scoresArrayList);

        // below line is to save data in shared prefs in the form of string.
        editor.putString("scores", scoresJson);

        // below line is to apply changes and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Log.d("saved to shared pref", "saved the data to shared preferences");
    }
}
