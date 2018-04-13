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

/**
 * Created by tahasaleem on 2018-04-12.
 */

public class Enemy extends SurfaceView{

     //nvas;
    private Bitmap monkey;
    int monkeyW;
    int monkeyH;
    Rect rectToBeDrawn;
    int numbFrames = 4;
    int frameNumber;
    int screenWidth;
    int screenHeight;
    long timeThisFrame = 0;
    float deltaTime;
    float animationClock = 0.0f;

    Intent i;
    Thread ourThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playingAnim = true;
    Paint paint;

    //pos and move the gab between the numbers should be always 200
    int left = 600;
    int right = 800;
    int top = 100;
    int bottom = 300;
    int speed = 30;
    int stopLocation = -660;
    boolean move = true;
    //setting up the
    public Enemy(Context context)
    {
        super(context);
        monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyspritesheet);
        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/numbFrames;

        ourHolder = getHolder();
        paint = new Paint();
    }
    //The update is in charge of making the animation move and move the monkey
    public void update()
    {
        if(left >= stopLocation)
        {
            rectToBeDrawn = new Rect((frameNumber * monkeyW) - 1, 0, (frameNumber * monkeyW + monkeyW) - 1, monkeyH);
            frameNumber++;
            if (numbFrames == frameNumber) {
                frameNumber = 0;
            }
            left -= speed;
            right -= speed;
        }
        System.out.println(left);
    }

    //control where the monkey is drawn
    public void drawMonkey(Canvas canvas)
    {
        Rect destRect = new Rect (screenWidth/2+left,screenHeight/2+top,screenWidth/2+right,screenHeight/2+bottom);
        canvas.drawBitmap(monkey,rectToBeDrawn, destRect, paint);
    }//katy is the best

    public void setScreen(int w, int h)
    {
        screenWidth = w;
        screenHeight = h;
    }

}

