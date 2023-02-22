package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchGame(View v){

        String gameMode;
        Intent game = new Intent(this, GameOverviewActivity.class);

        gameMode = (((Button)v).getText().toString());
        game.putExtra("GameMode", gameMode);
        startActivity(game);
    }
}