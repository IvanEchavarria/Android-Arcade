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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by tahasaleem on 2018-04-12.
 */

public class Enemy extends SurfaceView {
    Canvas canvas;
    private Bitmap monkey;
    int monkeyW;
    int monkeyH;
    Rect rectToBeDrawn;
    int numbFrames = 4;
    int frameNumber;


    int screenWidth;
    int screenHeight;

    Intent i;
    Thread ourThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playingAnim;
    Paint paint;

    public Enemy(Context context)
    {
        super(context);
        monkey = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyspritesheet);
        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/numbFrames;

        ourHolder = getHolder();
        paint = new Paint();
    }
    public void update(){
        rectToBeDrawn = new Rect((frameNumber*monkeyW)-1,0,(frameNumber*monkeyW+monkeyW)-1,monkeyH+0);
        frameNumber++;

        if(numbFrames==frameNumber)
        {
            numbFrames = 0;
        }
    }
    public void draw()
    {
        Rect destRect = new Rect (screenWidth/2-100,screenHeight/2-100,screenWidth/2+100,screenHeight/2+100);
        canvas.drawBitmap(monkey,rectToBeDrawn, destRect, paint);
    }//katy is the best

}

