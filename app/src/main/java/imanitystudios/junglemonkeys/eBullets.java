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

    private Bitmap banana;
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
    public eBullets(Context context, int x, int y)
    {
        super(context);
        System.out.println("BANANNANANANANA");
        //banana
        banana = BitmapFactory.decodeResource(getResources(),R.drawable.banana);
        banana = Bitmap.createScaledBitmap(banana, 85,85,true);
        bananaH = banana.getHeight();
        bananaW = banana.getWidth()/numbFrames;
        paint = new Paint();

       bananaDestRect = new Rect(x +bLeft,y +bTop,x + bLeft+bananaW,y+bTop+bananaH);
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
        System.out.println("updateBanana");

    }
    public void drawBanana(Canvas canvas){
        canvas.drawBitmap(banana, bananaToBeDrawn,bananaDestRect,paint);
        System.out.println("drawBANANANNA");

    }

}
