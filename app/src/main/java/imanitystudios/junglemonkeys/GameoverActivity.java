package imanitystudios.junglemonkeys;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameoverActivity extends AppCompatActivity {

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        back = (Button)findViewById(R.id.backToMenu);

        PlayButtonListener playButtonListener = new PlayButtonListener();
        back.setOnClickListener(playButtonListener);
    }

    private void startGame()
    {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    private class PlayButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            startGame();
        }
    }
}
