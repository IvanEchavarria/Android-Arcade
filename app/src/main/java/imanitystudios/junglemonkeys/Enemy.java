package imanitystudios.junglemonkeys;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * Created by tahasaleem on 2018-04-12.
 */

public class Enemy extends View {
    private Bitmap monkey;
    int monkeyW;
    int monkeyH;




    public Enemy(Context context)
    {
        super(context);

        monkeyH = monkey.getHeight();
        monkeyW = monkey.getWidth()/4;
    }


}

