package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Function is executed when one of the "start game" buttons is clicked.
    //The activity "GameOverviewActivity" is initialized and the parameter "GameMode" is passed, to determine the number of players partaking in the game.
    public void launchGame(View v){
        String gameMode;
        Intent game = new Intent(this, GameOverviewActivity.class);
        gameMode = (((Button)v).getText().toString());
        game.putExtra("GameMode", gameMode);
        startActivity(game);
    }

    public void closeApp(View v){
        finish();
    }
}