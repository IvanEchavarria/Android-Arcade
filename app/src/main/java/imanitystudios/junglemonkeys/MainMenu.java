package imanitystudios.junglemonkeys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {

    Button quit;
    Button Play;
    Button instruction;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //setting up the buttons
        quit = (Button) findViewById(R.id.quit);
        Play = (Button) findViewById(R.id.StartGame);
        instruction = (Button)findViewById(R.id.Instruction);
        back = (Button)findViewById(R.id.back);

        PlayButtonListener playButtonListener = new PlayButtonListener();
        Play.setOnClickListener(playButtonListener);


        QuitButtonListener quitListener = new QuitButtonListener();
        quit.setOnClickListener(quitListener);

        InstructionButtonListener instrcutionListener = new InstructionButtonListener();
        instruction.setOnClickListener(instrcutionListener);

        backButtonListener backButtonListener = new backButtonListener();
        back.setOnClickListener(backButtonListener);
    }


    //Starting the game activity
    private void startGame()
    {
        Intent intent = new Intent(this, MainActivity.class);
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
    //Exit Game
    private class QuitButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    }
    //Instruction
    private class InstructionButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            quit.setVisibility(View.INVISIBLE);
            Play.setVisibility(View.INVISIBLE);
            instruction.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
        }
    }

    private class backButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            quit.setVisibility(View.VISIBLE);
            Play.setVisibility(View.VISIBLE);
            instruction.setVisibility(View.VISIBLE);
            back.setVisibility(View.INVISIBLE);
        }
    }

}
