package imanitystudios.junglemonkeys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by spawn on 2018-04-19.
 */

public class ColaAttack extends SurfaceView {

    private Bitmap cola;
    int colaW;
    int colaH;
    Rect rectToBeDrawn;
    Rect destRect;
    int screenWidth;
    int screenHeight;
    int frameNumber = 5;
    int numberOfFrames = 6;



    SurfaceHolder ourHolder;
    Paint paint;

    //pos and move the gab between the numbers should be always 200
    int left = 800;
    int right = 100;
    int top = 820;
    int bottom = 100;

        int stopLocation = -660;

        //setting up the
    public ColaAttack(Context context)
        {
            super(context);
            cola = BitmapFactory.decodeResource(getResources(), R.drawable.water);
            //cola = Bitmap.createScaledBitmap(cola, 200, 100, true);

        colaH = cola.getHeight();
        colaW = cola.getWidth() / numberOfFrames;
        ourHolder = getHolder();
        paint = new Paint();
        rectToBeDrawn = new Rect((frameNumber * colaW), 0, (frameNumber * colaW + colaW), colaH);
    }
    //The update is in charge of making the animation move and move
    public void update()
    {

    }

    //control where the cola is drawn
    public void drawCola(Canvas canvas)
    {
        destRect = new Rect (left, top, left + colaW ,colaH + top);
        canvas.drawBitmap(cola,rectToBeDrawn, destRect, paint);
    }

    public void setScreen(int w, int h)
    {
        screenWidth = w;
        screenHeight = h;
    }
    public Rect getRectangle()
    {
        return destRect;
    }



}
