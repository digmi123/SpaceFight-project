package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class OurSpaceship {

    Context context;
    Bitmap ourSpaceship;
    int ox, oy;
    //int velocity;
    Random random;

    public OurSpaceship(Context context){
        this.context = context;

        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.space_ship);
        ourSpaceship = Bitmap.createScaledBitmap(ourSpaceship, 250, 200, true);

        random = new Random();
        ox = random.nextInt(SpaceShooter.screenWidth);
        oy = SpaceShooter.screenHeight - ourSpaceship.getHeight() - 80;

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
