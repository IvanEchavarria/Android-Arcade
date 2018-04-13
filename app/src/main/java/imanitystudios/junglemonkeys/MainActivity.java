/*
Source File:   MainActivity
Authors & IDs: Taha Saleem     100803048\
               Ivan Echavarria 101092562
Creation Date: 12/04/2018


File Description: it's our Main activity

*/

package imanitystudios.junglemonkeys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {


    Rect rectToBeDrawn;

    public Canvas canvas;

    Bitmap enemyAnimation;
    Bitmap background;

    int frameHeight = 221;
    int frameWidth = 224;
    int frameNumber;

    int screenWidth;
    int screenHeight;

    long lastFrameTime;
    int fps;
    int hi;

    Intent i;

    DrawingClass drawingClass;

    Enemy monkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); // Gets the screen size and puts in a point type that contains x and y
        screenWidth = size.x;
        screenHeight = size.y;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        drawingClass = new DrawingClass(this);
        monkey = new Enemy(this);

        monkey.setScreen(screenWidth, screenHeight);
        setContentView(drawingClass);


    }

    class DrawingClass extends View
    {
        public DrawingClass(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(w, h, oldw, oldh);
            background = Bitmap.createScaledBitmap(background, w, h, true);
        }

        @Override
        protected  void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);

            canvas.drawBitmap(background,0,0, null);

            //System.out.println("IS THIS BEING CALLED?");
           monkey.drawMonkey(canvas);
           invalidate();
        }

    }


}
