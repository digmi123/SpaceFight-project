package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;

import java.util.Random;

public class OurSpaceship extends Entity{

    Context context;
    Bitmap ourSpaceship;
    int lives = 3;

    public OurSpaceship(Context context, Display display, float spaceShipX, float spaceShipY, int speed){
        super(context, R.drawable.space_ship, 300, 200,spaceShipX-125 , spaceShipY - 270, 5);
        //int width = display.getWidth()/1;
        this.context = context;

        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.space_ship);
        ourSpaceship = Bitmap.createScaledBitmap(ourSpaceship, 250, 200, true);
    }

    public Bitmap getOurSpaceship(){
        return ourSpaceship;
    }

    int getOurSpaceshipWidth(){
        return ourSpaceship.getWidth();
    }

    int getOurSpaceshipHeight(){
        return ourSpaceship.getHeight();
    }
}
