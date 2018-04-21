package imanitystudios.junglemonkeys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Vector;

/**
 * Created by tahasaleem on 2018-04-12.
 */

public class Enemy extends SurfaceView{
    private Bitmap monkey;
    int monkeyW;
    int monkeyH;
    Rect rectToBeDrawn;
    Rect destRect;
    int numbFrames = 4;
    int frameNumber;
    int screenWidth;
    int screenHeight;

    Intent i;
    Thread ourThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playingAnim = true;
    Paint paint;


    int hp = 100;
    //pos and move the gab between the numbers should be always 200
    int left = 850;
    int right = 800;
    int top = 200;
    int bottom = 300;
    int speed = 20;
    int stopLocation = screenWidth/2;
    boolean move = true;
    Context eContext;
    int counter = 0;

    boolean readyForAction = false;

    //setting up the
    public Enemy(Context context)
    {
        super(context);
        monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyspritesheet);
        monkey = Bitmap.createScaledBitmap(monkey, 360,250,true);
        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/numbFrames;

        eContext = context;

    }
    //The update is in charge of making the animation move and move the monkey
    public void update()
    {
        rectToBeDrawn = new Rect((frameNumber * monkeyW) - 1, 0, (frameNumber * monkeyW + monkeyW) - 1, monkeyH);
        if(left >= stopLocation)
        {
            frameNumber++;
            if (numbFrames == frameNumber) {
                frameNumber = 0;
            }
            left -= speed;
        }
        if(left <= stopLocation)
        {
            move = true;
            frameNumber = 0;
            counter++;
            if(counter == 5){
                readyForAction = true;
                counter = 0;
            }
        }

    }

    //control where the monkey is drawn
    public void drawMonkey(Canvas canvas)
    {
        destRect = new Rect (screenWidth/2+left,screenHeight/2+top,screenWidth/2+left + monkeyW,screenHeight/2+top + monkeyH);
        canvas.drawBitmap(monkey,rectToBeDrawn, destRect, paint);

    }//katy is the best

    public void setScreen(int w, int h)
    {
        screenWidth = w;
        screenHeight = h;
    }
    public void setLocation(int T)
    {
        top += T;
    }
    public void damage(int attack)
    {
        hp -= attack;
    }

    public Rect getRectangle()
    {
        return destRect;
    }

    public eBullets shoot()
    {
        readyForAction = false;
        eBullets bulletOBJ = new eBullets(eContext, left, top,screenHeight,screenWidth);
        return bulletOBJ;
    }
    public void setStopPoint(int s){
        stopLocation+=s;
    }

}

