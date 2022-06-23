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
    EditText nameEditText, playerNameTExtTest;
    Button saveBtn, scoreBoardBtn;
    TextView tvPoints;
    ArrayList<scoreModel> scoresArrayList;
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        saveBtn = findViewById(R.id.saveBtn);
        scoreBoardBtn = findViewById(R.id.scoreBoardBtn);
        playerNameTExtTest = findViewById(R.id.playerName);

        loadData();

        points = getIntent().getExtras().getInt("points");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);

        //if the points greater than minimum score in list: player can enter name and save his score.

        if (scoresArrayList.size() == 0 || (points > scoresArrayList.get(scoresArrayList.size() - 1).playerScore)) {
        Log.d("GameOver.soresArrayList", "size of the scoresArrayList : "+scoresArrayList.size());

                Log.d("GameOver.if", "in the if of arrayList");
                //Show editText of player name in order for the player to save his score if he wants.
//            LinearLayout myLinearLayout = findViewById(R.id.scoreLinearLayout);
//            nameEditText = new EditText(this);
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            nameEditText.setHint(R.string.playerNameHint);
//            nameEditText.setTextSize(30);
//            nameEditText.setLayoutParams(params);
//            myLinearLayout.addView(nameEditText);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (playerNameTExtTest.getText().toString() != "" ){
                        saveData(playerNameTExtTest.getText().toString(), points);
                    }
                }
            });
        }

        scoreBoardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, ScoreBoard.class);
                GameOver.this.startActivity(intent);
            }
        });

    }

    //onClick SaveBtn :
//    scoreModel newPlayerScore = new scoreModel(playerName.getText().toString(), points);
//    scoresArrayList.add(newPlayerScore);

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
    }

    public void loadData() {
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
       scoresArrayList.add(newPlayerScore);
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
