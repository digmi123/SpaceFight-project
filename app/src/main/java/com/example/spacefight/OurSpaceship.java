package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;


public class OurSpaceship extends Entity{

    Context context;
    Bitmap shield;
    int lives = 3;
    int protectionTime = 0;

    public OurSpaceship(Context context, Point screenSize, float spaceShipX, float spaceShipY, int speed){
        super(context, R.drawable.space_ship, 300, 200,spaceShipX-125 , spaceShipY - 270, 5);
        this.context = context;
    }

    public Bitmap getOurSpaceship(){
        return characterBitmap;
    }

    int getOurSpaceshipWidth(){
        return characterBitmap.getWidth();
    }

    int getOurSpaceshipHeight(){
        return characterBitmap.getHeight();
    }

    public void drawShield(Canvas canvas){
            shield = BitmapFactory.decodeResource(context.getResources(), R.drawable.shieldactive);
            shield = Bitmap.createScaledBitmap(shield, width, height, true);
            canvas.drawBitmap(shield, xPosition, yPosition, null);
    }
}
