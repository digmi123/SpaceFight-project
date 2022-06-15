package com.example.spacefight;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {
    ArrayList<scoreModel> scoresArrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);
        loadData();
        listView = (ListView) findViewById(R.id.listView);

        //Taking the data from the sharedPreferences and storing in the scoresArrayList.

        //Taking the scoresArrayList and populate the listView with it.
        fillListView();
    }
    //load data from shared preferences.



    public void loadData() {
        SharedPreferences sharedPreferences =  getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<scoreModel>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        scoresArrayList = gson.fromJson(json, type);
        Log.d("returned Gson", ""+gson.fromJson(json, type));
        Log.d("ScoreBoard.loadData", "got the json into the arrayList");

        // checking below if the array list is empty or not
        if (scoresArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            Log.d("ScoreBoard.loadData.if", "The arrayList is null initializing");
            scoresArrayList = new ArrayList<scoreModel>();
        }
    }

    public void fillListView(){
        CustomAdapter myCustomAdapter = new CustomAdapter(ScoreBoard.this, scoresArrayList);
        Log.d("ScoreBoard fillListView", "Created the adapter for listView");
        listView.setAdapter(myCustomAdapter);
        Log.d("ScoreBoard fillListView", "adapter for listView has been set");
    }
}

