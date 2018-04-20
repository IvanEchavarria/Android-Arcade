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
    int left = 600;
    int right = 800;
    int top = 100;
    int bottom = 300;
    int speed = 6000;
    int stopLocation = -660;
    boolean move = true;
    //setting up the
    public Enemy(Context context)
    {
        super(context);
        monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyspritesheet);
        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/numbFrames;

        System.out.println("I am a fresh new Monkey");
        ourHolder = getHolder();
        paint = new Paint();
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
            left -= speed*0.02 ;
            right -= speed*0.02;
        }
        if(left <= stopLocation)
        {
            move = true;
            frameNumber = 0;
        }
      //  System.out.println(left);
    }

    //control where the monkey is drawn
    public void drawMonkey(Canvas canvas)
    {
        destRect = new Rect (screenWidth/2+left,screenHeight/2+top,screenWidth/2+right,screenHeight/2+bottom);
        canvas.drawBitmap(monkey,rectToBeDrawn, destRect, paint);
    }//katy is the best

    public void setScreen(int w, int h)
    {
        screenWidth = w;
        screenHeight = h;
    }
    public void setLocation(int L, int B,int T, int R){
        left = L;
        bottom = B;
        top = T;
        right = R;
    }
    public void damage(int attack)
    {
        hp -= attack;
    }

    public Rect getRectangle()
    {
        return destRect;
    }

}

