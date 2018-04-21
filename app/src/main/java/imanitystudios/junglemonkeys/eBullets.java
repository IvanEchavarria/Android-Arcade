package imanitystudios.junglemonkeys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;

/**
 * Created by taha on 2018-04-21.
 */


public class eBullets extends SurfaceView {

    private Bitmap bullet;
    int bananaW;
    int bananaH;
    Rect bananaToBeDrawn;
    Rect bananaDestRect;
    Paint paint;
    int bFrameNumber = 0;
    boolean attack = true;
    int bLeft = -20;
    int bTop = 80;
    int numbFrames = 4;
    int speed = 40;
    int xPos;
    int yPos;
    int screenWidth;
    int screenHeight;

    public eBullets(Context context, int x, int y,int h, int w)
    {
        super(context);

        //banana
        bullet = BitmapFactory.decodeResource(getResources(),R.drawable.banana);
        bullet = Bitmap.createScaledBitmap(bullet, 85,85,true);
        bananaH = bullet.getHeight();
        bananaW = bullet.getWidth()/numbFrames;
        paint = new Paint();

       xPos = x;
       yPos = y;
       screenHeight = h;
       screenWidth = w;
        //bananaDestRect = new Rect(bLeft,bTop, bLeft+bananaW,bTop+bananaH);
    }
    public void update(){
        bananaToBeDrawn = new Rect((bFrameNumber * bananaW) - 1, 0, (bFrameNumber * bananaW + bananaW) - 1, bananaH);
        if(attack)
        {
            bFrameNumber++;
            if(bFrameNumber == numbFrames){
                bFrameNumber = 0;
            }
            bLeft -=speed;
        }


    }
    public void drawBanana(Canvas canvas){
        bananaDestRect = new Rect(screenWidth/2+xPos +bLeft,screenHeight/2+yPos +bTop,screenWidth/2+xPos + bLeft+bananaW,screenHeight/2+yPos+bTop+bananaH);
        canvas.drawBitmap(bullet, bananaToBeDrawn,bananaDestRect,paint);
        System.out.println(bananaDestRect);

    }

}
