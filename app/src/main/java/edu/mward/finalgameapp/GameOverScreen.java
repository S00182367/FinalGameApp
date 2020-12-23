package edu.mward.finalgameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);
    }

        public void doHigh(View view) {
            //Summary Code
            Intent HighScoreScreenActivity = new Intent(view.getContext(), HighScoreScreen.class);

            //Starts new activity
            startActivity(HighScoreScreenActivity);
        }

    public void doReturn(View view) {
        Intent MainActivity = new Intent(view.getContext(), MainActivity.class);

        //Starts new activity
        startActivity(MainActivity);
    }
}
