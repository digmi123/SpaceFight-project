package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Shot {

    Bitmap shot;
    Context context;
    int shotX, shotY;

    public Shot(Context context, int shotX, int shotY){
        this.context = context;

        shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_bullet);
        shot = Bitmap.createScaledBitmap(shot, 80, 80, true);

        this.shotX = shotX;
        this.shotY = shotY;

    }
    public Bitmap getShot(){
        return shot;
    }

    public int getShotWidth(){
        return shot.getWidth();
    }

    public int getShotHeight(){
        return shot.getHeight();
    }
}
