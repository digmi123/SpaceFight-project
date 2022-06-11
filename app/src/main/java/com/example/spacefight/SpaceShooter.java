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
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SpaceShooter extends View {

    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 1;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    OurSpaceship ourSpaceship;
    EnemySpaceship enemySpaceship;
    Random random;
    ArrayList<Shot> enemyShots, ourShots;

    boolean enemyAction = false;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        ourSpaceship = new OurSpaceship(context);
        enemySpaceship = new EnemySpaceship(context);
        handler = new Handler();

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);

        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw background Points and life that i need to add on Canvas.
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Points: " + points, 0, TEXT_SIZE, scorePaint);

        //Need to implement LifeImage.
//        for(int i = 0; i < life; i++){
//            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
//        }

        //When life == 0 Game stops and launch GameOver activity with points.
//        if (life == 0) {
//            paused = true;
//            handler = null;
//            Intent intent = new Intent(context, GameOver.class);
//            intent.putExtra("points", points);
//            context.startActivity(intent);
//            ((Activity) context).finish();
//        }
        //Testing:
        if (points == 0) {
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        enemySpaceship.ex += enemySpaceship.enemyVelocity;
        //if enemy collides the right wall velocity reverse.
        if (enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() >= screenWidth) {
            enemySpaceship.enemyVelocity *= -1;
        }

        //if enemy collides the left wall velocity reverse.
        if (enemySpaceship.ex <= 0) {
            enemySpaceship.enemyVelocity *= -1;
        }

        if (enemyAction == false) {
            if (enemySpaceship.ex >= 200 + random.nextInt(400)) {
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey);
                enemyShots.add(enemyShot);
                //We are making enemyShot true so that the enemy can take one shot at a time.
                enemyAction = true;

            }

            if (enemySpaceship.ex >= 400 + random.nextInt(800)) {
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey);
                enemyShots.add(enemyShot);
                //We are making enemyShot true so that the enemy can take one shot at a time.
                enemyAction = true;

            } else {
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey);
                enemyShots.add(enemyShot);
                //We are making enemyShot true so that the enemy can take one shot at a time.
                enemyAction = true;

            }
        }
        // Draw the enemy spaceship:
        canvas.drawBitmap(enemySpaceship.getEnemySpaceship(), enemySpaceship.ex, enemySpaceship.ey, null);

        if (ourSpaceship.ox > screenWidth - ourSpaceship.getOurSpaceshipWidth()) {
            ourSpaceship.ox = screenWidth - ourSpaceship.getOurSpaceshipWidth();
        } else if (ourSpaceship.ox < 0) {
            ourSpaceship.ox = 0;
        }

        // Draw our Spaceship:
        canvas.drawBitmap(ourSpaceship.getOurSpaceship(), ourSpaceship.ox, ourSpaceship.oy, null);
        // Draw the enemy shot towards our spaceship and if its being hit, decrement life,
        // remove the shot object from enemyShots.
        // Else if, it goes away through the bottom edge of the screen also remove the shot from enemyShots.
        // When there is no enemyShots on the screen, change enemyAction to false, so that enemy can shot.

        // If i will implement difficulty level i will make the shotY velocity in variable.
        for (int i = 0; i < enemyShots.size(); i++) {
            enemyShots.get(i).shotY += 15;
            canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shotX, enemyShots.get(i).shotY, null);
            if ((enemyShots.get(i).shotX >= ourSpaceship.ox)
                    && enemyShots.get(i).shotX <= ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth()
                    && enemyShots.get(i).shotY >= ourSpaceship.oy
                    && enemyShots.get(i).shotY <= screenHeight) {

                //life--;
                enemyShots.remove(i);
                if (points > 0)
                    points--;

            } else if (enemyShots.get(i).shotY >= screenHeight) {
                enemyShots.remove(i);
            }
            if (enemyShots.size() < 1) {
                enemyAction = false;
            }
        }

        // Draw our spaceship shots Towards the enemy. if there is a collision between our shot and enemy
        // spaceship, increment points, remove the shot from OurShots and make sound if necessary of hit
        // Else if our shot goes away off the screen through the top edge of the screen also remove
        // the shot object from OUrShots ArrayList

        // If i will implement difficulty level i will make the shotY velocity in variable.
        for (int i = 0; i < ourShots.size(); i++) {
            ourShots.get(i).shotY -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shotX, ourShots.get(i).shotY, null);
            if ((ourShots.get(i).shotX >= enemySpaceship.ex)
                    && ourShots.get(i).shotX <= enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth()
                    && ourShots.get(i).shotY <= enemySpaceship.getEnemySpaceshipWidth()
                    && ourShots.get(i).shotY >= enemySpaceship.ey) {
                points++;
                ourShots.remove(i);
                //make sound if necessary.
            } else if (ourShots.get(i).shotY <= 0) {
                ourShots.remove(i);
            }
        }


        // if not paused, we will call the postDelayed( method on handler object which will
        // cause the run method inside Runnable to be executed after 30 millisecond, that is the value of
        // UPDATE_MILLIS.
        if (!paused) {
            handler.postDelayed(runnable, UPDATE_MILLIS);
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
                Shot ourShot = new Shot(context, ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth() / 2, ourSpaceship.oy);
                ourShots.add(ourShot);
            //}
        }
        // When event.getAction is MotionEvent.ACTION_DOWN, control ourSpaceship
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ourSpaceship.ox = touchX;
        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control ourSpaceship
        // along with the touch.
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ourSpaceship.ox = touchX;
        }
        // Returning true in an onTouchEvent tells android system that you already handled
        // the touch event and no further handling is required.
        return true;
    }

}
