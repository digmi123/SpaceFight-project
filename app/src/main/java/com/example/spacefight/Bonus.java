package com.example.spacefight;

import android.content.Context;

public abstract class Bonus extends Entity{

    public Bonus(Context context, int drawableImage, int width, int height, float xPosition, float yPosition, int speed) {
        super(context, drawableImage, width, height, xPosition, yPosition, speed);
    }
    public abstract void activate(OurSpaceship ourSpaceShip);
}
