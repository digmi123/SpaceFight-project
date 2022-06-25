package com.example.spacefight;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {
    boolean isEnable;
    MediaPlayer bonusSound;
    MediaPlayer laserShotSound;
    MediaPlayer hitSound;

    public Sound(Context context, boolean isEnable) {
        this.isEnable = isEnable;
        bonusSound = MediaPlayer.create(context, R.raw.bonus);
        laserShotSound = MediaPlayer.create(context, R.raw.laser_shot);
        hitSound = MediaPlayer.create(context, R.raw.hit);
    }

    public void playBonusSound(){
        if(!isEnable){
            return;
        }
        bonusSound.start();
    }
    public void playLaserShotSound(){
        if(!isEnable){
            return;
        }
        laserShotSound.start();
    }
    public void playHitSound(){
        if(!isEnable){
            return;
        }
        hitSound.start();
    }
}
