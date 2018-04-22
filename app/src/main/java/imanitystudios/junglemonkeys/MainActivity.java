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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Looper;
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

    ColaAttack colaAttack;

    Vector<MentosAttack> mentosList;
    Vector<eBullets> eBulletList;
    Vector<Enemy> enemyList;

    int enemySpawnerClock;

    Context globalContext;

    //stats
    int health = 100;
    int score = 0;
    float timer = 0;

    MediaPlayer mainTheme;
    MediaPlayer bananaShot;
    MediaPlayer monkeyDeath;
    MediaPlayer colaSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished / 1000;
            }

            public void onFinish() {

            }
        }.start();

        mainTheme = MediaPlayer.create(this,R.raw.mainsong);
        mainTheme.setLooping(true);
        mainTheme.start();
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

        setContentView(drawingClass);

        mentosList = new Vector<>();

        eBulletList = new Vector<>();

        enemyList = new Vector<>();

       bananaShot = MediaPlayer.create(this,R.raw.shot);
       monkeyDeath = MediaPlayer.create(this,R.raw.monkeydie);
       colaSound = MediaPlayer.create(this,R.raw.colaboil);

        globalContext = this;
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
                      //  Toast.makeText(this,"pressed Candy button",Toast.LENGTH_LONG).show();
                    }
                }
                else if(!buttonPressed && xInitial >= colaPosX && xInitial <= colaPosX + cola.getWidth())
                {
                    if(yInitial >= colaPosy && yInitial <= colaPosy + cola.getHeight())
                    {
                        buttonPressed = true;
                        //Toast.makeText(this,"pressed Cola button",Toast.LENGTH_LONG).show();

                        if(colaAttack == null)
                        {
                            colaAttack = new ColaAttack(this);
                            colaAttack.setScreen(screenWidth,screenWidth);
                            colaSound.start();
                        }

                    }
                }
                else if(!buttonPressed && xInitial >= gummyPosX && xInitial <= gummyPosX + gummy.getWidth())
                {
                    if(yInitial >= gummyPosy && yInitial <= gummyPosy + gummy.getHeight())
                    {
                        buttonPressed = true;
                       // Toast.makeText(this,"pressed Gummy button",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if(mentosList.size()<2) {
                        MentosAttack mentoOBJ = new MentosAttack(this);
                        mentoOBJ.setLocation((int) xInitial);
                        mentoOBJ.setScreen(screenWidth, screenHeight);
                        mentosList.add(mentoOBJ);
                    }
//
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
                System.gc();/// Added garbage collector
            }
        }

        public void update()
        {

            if(enemySpawnerClock >= 2)
            {
                enemySpawnerClock = 0;
                if(enemyList.size()<8)
                {
                    createEnemy();
                }
            }
            enemySpawnerClock++;

            if(enemyList != null && enemyList.size() > 0)
            {
                for (int i = 0; i < enemyList.size(); i++) {
                    if (enemyList != null && enemyList.elementAt(i).readyForAction) {
                        if (enemyList.elementAt(i) != null)
                        {
                            addBullet(enemyList.elementAt(i).shoot());
                            bananaShot.start();
                        }

                    }
                }
            }


        }

        private void draw()
        {
            if(ourHolder.getSurface().isValid())
            {
                canvas = ourHolder.lockCanvas();
                canvas.drawBitmap(background,0,0, null);
                paint.setTextSize(50);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setColor(Color.rgb(255,165,0));
                canvas.drawText(" Health: "+(int)health,50,40,paint);
                canvas.drawText("Score:"+(int)score,1400,40,paint);
                canvas.drawText("Time: "+(int)timer, screenWidth/2,40,paint);

                if(colaAttack !=null)
                {
                    colaAttack.update();
                    colaAttack.drawCola(canvas);
                }

                if(eBulletList.size()>0)
                {
                    for(int i = 0; i< eBulletList.size();i++)
                    {
                        eBulletList.elementAt(i).update();
                        eBulletList.elementAt(i).drawBanana(canvas);
                        if (eBulletList.elementAt(i).getRectangle().left < (screenWidth/2)-600){
                            eBulletList.removeElementAt(i);
                            health -=10;
                        }

                    }
                }

                if(enemyList != null && enemyList.size() > 0)
                {
                    for (int i = 0; i < enemyList.size(); i++)
                    {
                        if (enemyList.elementAt(i) != null)
                        {
                            enemyList.elementAt(i).update();
                            enemyList.elementAt(i).drawMonkey(canvas);
                        }
                    }
                }

                if(mentosList != null && mentosList.size() > 0)
                {
                    for(int i = 0; i < mentosList.size(); i++)
                    {
                        if(mentosList.elementAt(i) != null) {
                            mentosList.elementAt(i).update(timeThisFrame);
                            mentosList.elementAt(i).drawMento(canvas);

                            if (mentosList.elementAt(i).getRectangle().top > screenHeight)
                            {
                                mentosList.removeElementAt(i);
                            }
                            else
                            {

                                for (int j = 0; j < enemyList.size(); j++) {
                                    if (mentosList.elementAt(i).getRectangle().intersect(enemyList.elementAt(j).getRectangle())) {
                                        score+=10;
                                        System.out.println("HIT LE MONKEY");
                                        enemyList.removeElementAt(j);
                                        monkeyDeath.start();
                                        mentosList.removeElementAt(i);
                                        i = -1;
                                        break;
                                    }
                                }//
                                if (i != -1 && colaAttack != null && mentosList.elementAt(i) != null) {
                                    if (mentosList.elementAt(i).getRectangle().intersect(colaAttack.getRectangle())) {
                                        colaAttack = null;
                                        mentosList.removeElementAt(i);
                                    }
                                }
                            }
                        }
                    }
                }


                if(enemyList != null && enemyList.size() > 0)
                {
                    for (int i = 0;i < enemyList.size();i++)
                    {
                        if(colaAttack!=null )
                        {
                            if(colaAttack.getRectangle().intersect(enemyList.elementAt(i).getRectangle()))
                            {
                                colaAttack.damage(20);
                                enemyList.elementAt(i).damage(20);
                                if(enemyList.elementAt(i).hp <= 0)
                                {
                                    enemyList.removeElementAt(i);
                                    monkeyDeath.start();
                                }

                                if(colaAttack.hp <= 0){
                                    colaAttack = null;
                                    break;
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
    public void addBullet(eBullets bullets)
    {
        eBulletList.add(bullets);
    }

    public void createEnemy()
    {
        if(Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        Enemy monkey = new Enemy(this);
        monkey.setScreen(screenWidth, screenHeight);
        if(enemyList.size() == 0||enemyList.size() == 4)
        {
            monkey.setLocation(-100);
        }
        else if(enemyList.size() == 1||enemyList.size() ==5)
        {
            monkey.setLocation(-75);
        }
        else if(enemyList.size() == 2||enemyList.size() ==6)
        {
            monkey.setLocation(-50);
        }
        else if(enemyList.size() == 3||enemyList.size() ==7)
        {
            monkey.setLocation(0);
        }
        if(enemyList.size()>=4 &&enemyList.size()<8)
        {
            monkey.setStopPoint(120);
        }
        else if(enemyList.size()>=8){
            monkey.setStopPoint(240);
        }
        enemyList.add(monkey);
    }

}
