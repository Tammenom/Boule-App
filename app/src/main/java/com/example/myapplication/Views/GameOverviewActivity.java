package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.R;

public class GameOverviewActivity extends AppCompatActivity {

    public GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        //The singleton class "GameController" is initialized.
        gameController = GameController.getInstance();
        //The "GameOverview"-Variable inside the GameController object is set to this Object.
        gameController.SetGameOverviewActivity(this);
        //Initialize a new Game by calling the InitializeNewGame method.
        InitializeNewGame();


    }

    //When executed calls the InitializeNewGame method in game Controller and passes the "GameMode" parameter, to determine the number of players partaking in the game.
    public void InitializeNewGame(){
        Intent game = getIntent();
        String gameMode = game.getStringExtra("GameMode");
        Log.d("App", "InitializeNewGame in GameOverviewActivity");
        gameController.InitializeNewGame(gameMode);
    }


    //Sets the TextView for the Members of the two Teams, to the String that is passed in.
    public void  SetTeamPlayerList(String teamName, String listContent){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1PlayerList)).setText(listContent);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2PlayerList)).setText(listContent);
        }
    }

    //Sets the TextView for the Members of the two Teams, to the String that is passed into the function.
    public void SetGameOverviewTotalRoundCount(String content){
        ((TextView)findViewById(R.id.TotalRoundCountView)).setText(content);
    }

    //Sets the TextView for the overall Count of played Game Rounds, to the String that is passed into the function.
    public void SetGameOverviewRoundCountList(String content){
        ((TextView)findViewById(R.id.RoundCountListView)).setText(content);
    }

    //Sets the TextView for the Members of the two Teams, to the String that is passed into the function.
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1TotalPointsView)).setText(content);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2TotalPointsView)).setText(content);
    }
    }

    //Sets the TextView for both teams' total points to the String that is passed to the function.
    public void SetGameOverviewTeamListPoints(String teamName, String listContent) {
        if (teamName == "Team 1") {
            ((TextView) findViewById(R.id.Team1PointsListView)).setText(listContent);
        }
        if (teamName == "Team 2") {
            ((TextView) findViewById(R.id.Team2PointsListView)).setText(listContent);
        }
    }
    public void SetNextButtonText(String nText){
        ((Button) findViewById(R.id.nextRoundButton)).setText(nText);
    }

    public void ExitGameButtonClicked(View v){finish();}

    //Is executed when "Next Round Button" is clicked, calls the LaunchNewGameRound function.
    public void NextRoundButtonClicked(View v){
        LaunchNewGameRound(v);
    }

    //New activity "GameRoundActivity" is initialized.
    public void LaunchNewGameRound(View v){
        Intent gameRound = new Intent(this, GameRoundActivity.class);
        startActivity(gameRound);
    }


}