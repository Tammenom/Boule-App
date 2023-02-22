package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayRoundActivity extends AppCompatActivity {

    BouleFieldView bouleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);
         bouleView = (BouleFieldView) findViewById(R.id.bouleFieldView);

    }

    public void throwANewBall(View v){
        bouleView.ThrowButtonClicked();
    }
}