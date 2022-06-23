package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;

public class HeartBonus extends Bonus{

    Bitmap heartBonus;
    Context context;

    public HeartBonus(Context context, Display display, float heartX, float heartY, int speed){
        super(context, R.drawable.heart, 80, 80, heartX - 40, heartY - 40, speed);
        this.context = context;
    }
    public Bitmap getHeartBonus(){
        return heartBonus;
    }

    public int getHeartWidth(){
        return heartBonus.getWidth();
    }

    public int getHeartHeight(){
        return heartBonus.getHeight();
    }

    @Override
    public void activate(OurSpaceship ourSpaceShip) {
        ourSpaceShip.lives++;
        this.active = false;
    }
}
