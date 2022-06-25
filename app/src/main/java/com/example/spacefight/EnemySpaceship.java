package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;

import java.util.Random;

public class EnemySpaceship extends Entity{

    int shootingTimer = 0;
    Context context;
    Bitmap enemySpaceship;

    public EnemySpaceship(Context context, Point screenSize, float spaceShipX, float spaceShipY, int speed){
        super(context, R.drawable.space_ship, 250, 200, spaceShipX - 125, spaceShipY, speed);
        this.context = context;

        enemySpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.space_ship);
        enemySpaceship = Bitmap.createScaledBitmap(enemySpaceship, 300,300, true);

    }

    public Bitmap getEnemySpaceship(){
        return enemySpaceship;
    }

    int getEnemySpaceshipWidth(){
        return enemySpaceship.getWidth();
    }

    int getEnemySpaceshipHeight(){
        return enemySpaceship.getHeight();
    }
}
