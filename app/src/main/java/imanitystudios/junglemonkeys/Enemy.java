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
    int left = 1700;
    int right = 800;
    int top = 650;
    int bottom = 300;
    int speed = 20;
    int stopLocation = 340;
    boolean move = true;


    //MonkeyAttack
    private Bitmap banana;
    int bananaW;
    int bananaH;
    Rect bananaToBeDrawn;
    Rect bananaDestRect;
    Paint bPaint;
    int bFrameNumber = 0;
    boolean attack = true;
    int bLeft = -20;
    int bTop = 80;
    //setting up the
    public Enemy(Context context)
    {
        super(context);
        monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyspritesheet);
        monkey = Bitmap.createScaledBitmap(monkey, 360,250,true);
        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/numbFrames;

        //banana
        banana = BitmapFactory.decodeResource(getResources(),R.drawable.banana);
        banana = Bitmap.createScaledBitmap(banana, 85,85,true);
        bananaH = banana.getHeight();
        bananaW = banana.getWidth()/numbFrames;

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
            left -= speed ;
        }
        if(left <= stopLocation)
        {
            move = true;
            frameNumber = 0;
        }
        bananaToBeDrawn = new Rect((bFrameNumber * bananaW) - 1, 0, (bFrameNumber * bananaW + bananaW) - 1, bananaH);
        if(attack)
        {
            bFrameNumber++;
            if(bFrameNumber == numbFrames){
                bFrameNumber = 0;
            }
            bLeft -=speed;

        }
        System.out.println(left);
    }

    //control where the monkey is drawn
    public void drawMonkey(Canvas canvas)
    {
        destRect = new Rect (left,top,left + monkeyW,top + monkeyH);
        canvas.drawBitmap(monkey,rectToBeDrawn, destRect, paint);

        bananaDestRect = new Rect(left +bLeft,top +bTop,left + bLeft+bananaW,top+bTop+bananaH);
        canvas.drawBitmap(banana, bananaToBeDrawn,bananaDestRect,bPaint);
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

