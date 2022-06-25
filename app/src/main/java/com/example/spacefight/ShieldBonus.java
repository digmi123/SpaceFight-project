package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;

public class ShieldBonus extends Bonus{

    Bitmap shieldBonus;
    Context context;

    public ShieldBonus(Context context, Display display, float heartX, float heartY, int speed){
        super(context, R.drawable.shield, 80, 80, heartX - 40, heartY - 40, speed);
        this.context = context;
    }
    public Bitmap getHeartBonus(){
        return shieldBonus;
    }

    public int getHeartWidth(){
        return shieldBonus.getWidth();
    }

    public int getHeartHeight(){
        return shieldBonus.getHeight();
    }


    @Override
    public void activate(OurSpaceship ourSpaceShip) {
        ourSpaceShip.protectionTime = 300;
        this.active = false;
    }
}