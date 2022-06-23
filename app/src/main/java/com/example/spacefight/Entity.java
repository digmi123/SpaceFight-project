package com.example.spacefight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Entity {

    Context context;
    boolean active = true;
    Bitmap characterBitmap;
    float xPosition, yPosition;
    int width, height, speed;
    Rect rect;

    public Entity(Context context, int drawableImage, int width, int height, float xPosition, float yPosition, int speed) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.characterBitmap = BitmapFactory.decodeResource(context.getResources(), drawableImage);
        this.characterBitmap = Bitmap.createScaledBitmap(this.characterBitmap, width, height, true);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
        this.rect = new Rect();
    }

    public Bitmap getBitmap() {
        return characterBitmap;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRightBorder() {
        return xPosition + characterBitmap.getWidth();
    }

    public float getLeftBorder() {
        return xPosition;
    }

    public float getTopBorder() {
        return yPosition;
    }

    public float getBottomBorder() {
        return yPosition + characterBitmap.getHeight();
    }

    public float getSpriteWidth() {
        return characterBitmap.getWidth();
    }

    public float getSpriteHeight() {
        return characterBitmap.getHeight();
    }

    public void move(Canvas canvas) {
        canvas.drawBitmap(characterBitmap, getXPosition(), getYPosition(), null);
        rect.set((int) xPosition, (int) yPosition, (int) xPosition + characterBitmap.getWidth(), (int) yPosition + characterBitmap.getHeight());
    }

    public boolean isCollision(Entity other) {
        if(other!=null){
            return this.rect.intersect(other.rect);
        }
        return false;
    }

}