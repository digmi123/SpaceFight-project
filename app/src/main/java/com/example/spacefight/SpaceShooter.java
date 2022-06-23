package com.example.spacefight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class SpaceShooter extends View {

    Context context;
    Display display;
    Bitmap background, hp;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static float screenWidth, screenHeight;
    int points = 0;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    Difficulty difficulty;
    OurSpaceship ourSpaceship;
    EnemySpaceship enemySpaceship;
    HeartBonus heartBonus;
    Random random;
    ArrayList<Shot> enemyShots, ourShots;
    ArrayList<HeartBonus> bonuses;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        difficulty = new Difficulty(StartUp.difficultyLevel);
        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        ourSpaceship = new OurSpaceship(context, display, screenWidth / 2, SpaceShooter.screenHeight, 10);
        enemySpaceship = new EnemySpaceship(context, display, screenWidth / 2, 0, difficulty.enemySpeed());
        bonuses = new ArrayList<>();
        handler = new Handler();

        //Creating the images.
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        background = Bitmap.createScaledBitmap(background, (int)screenWidth, (int)screenHeight, true);

        hp = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        hp = Bitmap.createScaledBitmap(hp, 150, 120, true);


        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {

        //Draw the game details such as background and life.
        drawGameScreenDetails(canvas);

        enemySpaceship.xPosition += enemySpaceship.speed;
        Log.d("The enemy speed", ""+enemySpaceship.speed);
        enemySpaceship.move(canvas);
        //if enemy collides the right wall velocity reverse.
        if ((enemySpaceship.xPosition + enemySpaceship.getEnemySpaceshipWidth() >= screenWidth) || enemySpaceship.xPosition <= 0) {
            enemySpaceship.speed *= -1;
        }

        enemySpaceship.shootingTimer--;
        if (enemySpaceship.shootingTimer < 0){
            enemySpaceship.shootingTimer = difficulty.enemyFireRate();
            Shot enemyShot = new Shot(context, display, enemySpaceship.xPosition + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.yPosition, difficulty.enemyShotsSpeed());
            enemyShots.add(enemyShot);
        }

        Bonus:
        switch(random.nextInt(difficulty.timerForBonus())) {
            case 1:
                heartBonus = new HeartBonus(context, display, random.nextFloat()*screenWidth, 0, difficulty.bonusSpeed());
                bonuses.add(heartBonus);
            case 2:
                // ShieldBonus
                break;
            default:
        }

        for(Bonus bonus: bonuses){
            bonus.yPosition += bonus.speed;
            bonus.move(canvas);
            if(bonus.isCollision(ourSpaceship)){
                bonus.activate(ourSpaceship);
            }
            if(bonus.yPosition > screenHeight){
                bonus.active = false;
            }
        }
        bonuses.removeIf(bonus -> (!bonus.active));


        if (ourSpaceship.getXPosition() > screenWidth - ourSpaceship.getOurSpaceshipWidth()) {
            ourSpaceship.setXPosition(screenWidth - ourSpaceship.getOurSpaceshipWidth());
        } else if (ourSpaceship.getXPosition() < 0) {
            ourSpaceship.setXPosition(0);
        }

        // Draw our Spaceship:
        ourSpaceship.move(canvas);

        // Draw the enemy shot towards our spaceship and if its being hit
        // remove the shot object from enemyShots.
        // Else if, it goes away through the bottom edge of the screen also remove the shot from enemyShots.
        // When there is no enemyShots on the screen, change enemyAction to false, so that enemy can shot.

        // If i will implement difficulty level i will make the shotY velocity in variable.
        for (Shot shot: enemyShots) {
            //Changing the Y position of the image and moving it on the canvas.
            shot.yPosition += shot.speed;
            shot.move(canvas);
            if(shot.isCollision(ourSpaceship)){
                shot.active = false;
                ourSpaceship.lives--;
            }
            if(shot.yPosition > screenHeight){
                shot.active = false;
            }
        }
        enemyShots.removeIf(s -> (!s.active));



        // Draw our spaceship shots Towards the enemy. if there is a collision between our shot and enemy
        // spaceship, increment points, remove the shot from OurShots and make sound if necessary of hit
        // Else if our shot goes away off the screen through the top edge of the screen also remove
        // the shot object from OUrShots ArrayList

        // If i will implement difficulty level i will make the shotY velocity in variable.
        for (Shot shot: ourShots) {
            shot.yPosition -= 15;
            shot.move(canvas);
            if (shot.isCollision(enemySpaceship)) {
                points++;
                shot.active = false;
                //make sound if necessary.
            } else if (shot.yPosition <= 0) {
                shot.active = false;
            }
        }
        ourShots.removeIf(s -> (!s.active));


        // if not paused, we will call the postDelayed( method on handler object which will
        // cause the run method inside Runnable to be executed after 30 millisecond, that is the value of
        // UPDATE_MILLIS.
        if (!paused) {
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    public void drawHearts(Canvas canvas){
        int top = 50;
        for (int i = 0; i < ourSpaceship.lives; i++)
        {
            canvas.drawBitmap(hp, screenWidth - (hp.getWidth() * (i+1)) + i*5, top, null);
        }


        //Condition for game over.
        if (ourSpaceship.lives == 0) {
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int touchX = (int) event.getX();

        // When event.getAction() is MotionEvent.ACTION_UP, if ourShots ArrayList size < 1,
        // Create a new shot.
        // This way we restrict ourselves to making just one shot at a time on the screen.
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //if (ourShots.size() < 1) {
                Shot ourShot = new Shot(context, display,ourSpaceship.getXPosition() + (float) ourSpaceship.getOurSpaceshipWidth() / 2, ourSpaceship.getYPosition(), 15);
                ourShots.add(ourShot);
            //}
        }
        // When event.getAction is MotionEvent.ACTION_DOWN, control ourSpaceship
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            ourSpaceship.setXPosition(touchX);
//        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control ourSpaceship
        // along with the touch.
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ourSpaceship.setXPosition(touchX);
        }
        // Returning true in an onTouchEvent tells android system that you already handled
        // the touch event and no further handling is required.
        return true;
    }

    public void drawGameScreenDetails(Canvas canvas){
        //Draw background Points and life that i need to add on Canvas.
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Points: " + points, 0, TEXT_SIZE, scorePaint);

        //draw the hearts on the screen:
        drawHearts(canvas);
    }

}
