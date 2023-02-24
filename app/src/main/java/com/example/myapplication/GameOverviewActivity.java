package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Controller.GameController;

public class GameOverviewActivity extends AppCompatActivity {

    public GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);

        gameController = GameController.getInstance();
        gameController.SetGameOverviewActivity(this);
        Log.d("App", "gameController.SetGameOverviewActivity in GameOverviewActivity");
        InitializeNewGame();


    }

    public void InitializeNewGame(){
        Intent game = getIntent();
        String gameMode = game.getStringExtra("GameMode");
        Log.d("App", "InitializeNewGame in GameOverviewActivity");
        gameController.InitializeNewGame(gameMode);


    }

    public void  SetTeamPlayerList(String teamName, String listContent){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1PlayerList)).setText(listContent);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2PlayerList)).setText(listContent);
        }
    }

    public void SetGameOverviewTotalRoundCount(String content){
        ((TextView)findViewById(R.id.TotalRoundCountView)).setText(content);
    }
    public void SetGameOverviewRoundCountList(String content){
        ((TextView)findViewById(R.id.RoundCountListView)).setText(content);

    }
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1TotalPointsView)).setText(content);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2TotalPointsView)).setText(content);
    }

    }
    public void SetGameOverviewTeamListPoints(String teamName, String listContent) {
        if (teamName == "Team 1") {
            ((TextView) findViewById(R.id.Team1PointsListView)).setText(listContent);
        }
        if (teamName == "Team 2") {
            ((TextView) findViewById(R.id.Team2PointsListView)).setText(listContent);
        }
    }

    public void NextRoundButtonClicked(View v){

        gameController.NextRound();
        launchPlayRound(v);

    }

    public void launchPlayRound(View v){

        String gameMode;
        Intent playRound = new Intent(this, PlayRoundActivity.class);

        //gameMode = (((Button)v).getText().toString());
        //playRound.putExtra("GameMode", gameMode);
        startActivity(playRound);
    }


}