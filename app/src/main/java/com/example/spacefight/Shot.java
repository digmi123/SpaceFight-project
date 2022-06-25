package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.Display;

public class Shot extends Entity{

    Bitmap shot;
    Context context;


    public Shot(Context context, Display display, float shotX, float shotY, int speed){
        super(context, R.drawable.laser_bullet, 80, 80, shotX - 40, shotY - 40, speed);
        this.context = context;

//        shotSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer shotSound) {
//                shotSound.release();
//            }
//        });
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
