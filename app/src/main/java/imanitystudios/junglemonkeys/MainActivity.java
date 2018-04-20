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
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    long timeThisFrame;

    Rect rectToBeDrawn;

    Canvas canvas;

    Bitmap background;

    Bitmap candy;
    int   candyPosX;
    int   candyPosy;

    Bitmap cola;
    int   colaPosX;
    int   colaPosy;

    Bitmap gummy;
    int   gummyPosX;
    int   gummyPosy;


    int screenWidth;
    int screenHeight;

    long lastFrameTime;
    int fps;
    int hi;

    boolean buttonPressed = false;

    Intent i;

    DrawingClass drawingClass;

    Enemy monkey;

    Vector<MentosAttack> mentosList;


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

        candy = BitmapFactory.decodeResource(getResources(), R.drawable.candy);
        candy = Bitmap.createScaledBitmap(candy, 160, 160, true);

        candyPosX = 100;
        candyPosy = screenHeight - candy.getHeight() - 90 ;

        cola = BitmapFactory.decodeResource(getResources(), R.drawable.cola) ;
        cola = Bitmap.createScaledBitmap(cola, 160, 160, true);

        colaPosX = 100 + candy.getWidth();
        colaPosy = screenHeight - cola.getHeight() - 90;

        gummy = BitmapFactory.decodeResource(getResources(), R.drawable.gummy);
        gummy = Bitmap.createScaledBitmap(gummy, 160, 160, true);

        gummyPosX = 100 + candy.getWidth() + gummy.getWidth();
        gummyPosy = screenHeight - gummy.getHeight() - 90;

        drawingClass = new DrawingClass(this);
        monkey = new Enemy(this);

        monkey.setScreen(screenWidth, screenHeight);
        setContentView(drawingClass);

        mentosList = new Vector<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action)
        {
            case (MotionEvent.ACTION_DOWN) :

               float  xInitial = event.getRawX();
               float  yInitial = event.getRawY();

                if( !buttonPressed && xInitial >= candyPosX && xInitial <= candyPosX + candy.getWidth())
                {
                    if(yInitial >= candyPosy && yInitial <= candyPosy + candy.getHeight())
                    {
                        buttonPressed = true;
                        Toast.makeText(this,"pressed Candy button",Toast.LENGTH_LONG).show();
                    }
                }
                else if(!buttonPressed && xInitial >= colaPosX && xInitial <= colaPosX + cola.getWidth())
                {
                    if(yInitial >= colaPosy && yInitial <= colaPosy + cola.getHeight())
                    {
                        buttonPressed = true;
                        Toast.makeText(this,"pressed Cola button",Toast.LENGTH_LONG).show();
                    }
                }
                else if(!buttonPressed && xInitial >= gummyPosX && xInitial <= gummyPosX + gummy.getWidth())
                {
                    if(yInitial >= gummyPosy && yInitial <= gummyPosy + gummy.getHeight())
                    {
                        buttonPressed = true;
                        Toast.makeText(this,"pressed Gummy button",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    MentosAttack mentoOBJ = new MentosAttack(this);
                    mentoOBJ.setLocation((int)xInitial);
                    mentoOBJ.setScreen(screenWidth, screenHeight);
                    mentosList.add(mentoOBJ);
                }
                return true;

            case (MotionEvent.ACTION_UP) :

                buttonPressed = false;
                return true;
        }

        return super.onTouchEvent(event);
    }

    class DrawingClass extends SurfaceView implements Runnable
    {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        Paint paint;


        public DrawingClass(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
        }

        @Override
        public void run()
        {
            while (playing)
            {
                update();
                draw();
                controlFPS();
            }
        }

        public void update()
        {
        }

        private void draw()
        {
            if(ourHolder.getSurface().isValid())
            {
                canvas = ourHolder.lockCanvas();

                canvas.drawBitmap(background,0,0, null);

                if(monkey != null)
                {
                    monkey.update();
                    monkey.drawMonkey(canvas);
                }

                if(mentosList != null)
                {
                    for(int i = 0; i < mentosList.size(); i++)
                    {
                        if(mentosList.elementAt(i) != null)
                        {
                            mentosList.elementAt(i).update(timeThisFrame);
                            mentosList.elementAt(i).drawMento(canvas);
                            if(monkey != null)
                            {
                                if(mentosList.elementAt(i).getRectangle().intersect(monkey.getRectangle()))
                                {
                                    System.out.println("HIT LE MONKEY");
                                    monkey = null;
                                    mentosList.removeElementAt(i);
                                }
                            }
                        }
                    }
                }

                canvas.drawBitmap(candy, 100, screenHeight - candy.getHeight() - 90 , null);
                canvas.drawBitmap(cola, 100 + candy.getWidth(), screenHeight - cola.getHeight() - 90 , null);
                canvas.drawBitmap(gummy, 100 + candy.getWidth() + gummy.getWidth(), screenHeight - gummy.getHeight() - 90 , null);

                ourHolder.unlockCanvasAndPost(canvas);

            }
        }

        public void controlFPS()
        {
            timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 500 - timeThisFrame;

            if(timeThisFrame > 0)
            {
                fps = (int) (1000 / timeThisFrame);
            }
            if(timeToSleep >0)
            {
                try
                {
                    ourThread.sleep(timeToSleep);
                }
                catch (InterruptedException e)
                {

                }
            }
            lastFrameTime = System.currentTimeMillis();
        }

        public void pause()
        {
            playing = false;
            try {
                ourThread.join();
            }
            catch (InterruptedException e)
            {
            }
        }
        public void resume()
        {
            playing = true;
            ourThread = new Thread(this);
            ourThread.start();

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(w, h, oldw, oldh);
            background = Bitmap.createScaledBitmap(background, w, h, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawingClass.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawingClass.pause();
    }


}
