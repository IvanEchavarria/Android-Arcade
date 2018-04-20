package imanitystudios.junglemonkeys;

import android.content.Context;
import android.content.Intent;
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

public class MentosAttack extends SurfaceView {

    private Bitmap mentos;
    int mentosW;
    int mentosH;
    Rect rectToBeDrawn;
    Rect destRect;
    int screenWidth;
    int screenHeight;




    SurfaceHolder ourHolder;
    Paint paint;

    //pos and move the gab between the numbers should be always 200
    int left = 400;
    int right = 100;
    int top = -100;
    int bottom = 100;
    int speed = 6000;
    int stopLocation = -660;

    //setting up the
    public MentosAttack(Context context)
    {
        super(context);
        mentos = BitmapFactory.decodeResource(getResources(), R.drawable.candy);
        mentos = Bitmap.createScaledBitmap(mentos, 150, 150, true);

        mentosH = mentos.getHeight();
        mentosW = mentos.getWidth();
        ourHolder = getHolder();
        paint = new Paint();
    }
    //The update is in charge of making the animation move and move
    public void update(Long deltaTime)
    {
        rectToBeDrawn = new Rect(0, 0, mentosW, mentosH);
        top += speed*0.02;
    }

    //control where the mento is drawn
    public void drawMento(Canvas canvas)
    {
        destRect = new Rect (left, top, left + mentosW ,mentosH + top);
        canvas.drawBitmap(mentos,rectToBeDrawn, destRect, paint);
    }

    public void setScreen(int w, int h)
    {
        screenWidth = w;
        screenHeight = h;
    }
    public void setLocation(int L)
    {
        left = L;
    }

    public Rect getRectangle()
    {
        return destRect;
    }


}
