package com.example.myapplication.Models;

import android.util.Log;
import android.view.View;

import com.example.myapplication.Controller.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameModel {
    private GameController gameController;

    //Numer of Players in current Game
    private int numPlayers = 0;

    //Count of Rounds in current Game
    private int roundCount = 0;

    //Game Points of both Teams in current Game
    private int team1Points = 0;
    private int team2Points = 0;

    //Size of the depicted Game Field in current Game
    private int fieldSizeX;
    private int fieldSizeY;

    List<Integer> roundsList;
    List<Integer> team1RoundPoints;
    List<Integer> team2RoundPoints;

    public GameModel(GameController nGameController){
        gameController = nGameController;
    }
    //Initializes the game withe the selected amount of Players
    public void InitializeGame(String newGameMode){
        ResetGameSettings();
        SetGameSettingsToGameMode(newGameMode);


    }

    public void SetGameSettingsToGameMode(String gMode){
        String gameMode = gMode;

        switch(gMode){

            case "1 vs.1":
                numPlayers= 1;
                break;
            case "2 vs.2":
                numPlayers= 3;
                break;
            case "3 vs.3":
                numPlayers= 5;
                break;
            default:
                Log.e("GameInfo","Game Mode Error");
                break;
        }
        SetGameOverviewTeamsList(numPlayers);


    }

    public void ResetGameSettings(){
        roundCount =0;
        team1Points = 0;
        team2Points = 0;
        roundsList = new ArrayList<>();
        team1RoundPoints = new ArrayList<>();
        team2RoundPoints = new ArrayList<>();


    }

    public void SetGameOverviewTeamsList(int numPlayers){

        String team1Content = "";
        String team2Content = "";
        boolean team1 = true;

        for (int i =0; i<= numPlayers; i++){
            if (team1){
                team1Content = team1Content + "Player "+ String.valueOf(i+1) + "\n";
                team1 = false;

            }else{
                team2Content = team2Content + "Player "+ String.valueOf(i+1) + "\n";
                team1 = true;
            }
        }
        gameController.SetGameOverviewTeamPlayerList("Team 1", team1Content);
        gameController.SetGameOverviewTeamPlayerList("Team 2", team2Content);

    }

    public void NextGameRound(){

        EndGameRound(RandomIntReturn(),RandomIntReturn());
    }

    public void EndGameRound(int nTeam1points, int nTeam2points){
        UpdateGameSettings(nTeam1points,nTeam2points);
        SetGameOverviewRounds();
        SetGameOverviewPlayerPoints();


    }

    public void SetGameOverviewRounds(){
        String roundsTotal = Integer.toString(roundCount);
        String roundsListView = "";
        for(int i = 0; i < roundsList.size(); i++){
            roundsListView += Integer.toString(roundsList.get(i))+"\n";
        }

        gameController.SetGameOverviewTotalRoundCount(roundsTotal);
        gameController.SetGameOverviewRoundCountList(roundsListView);
    }

    public void SetGameOverviewPlayerPoints(){
        int team1TotalPoints = 0;
        int team2TotalPoints = 0;
        String team1RoundPointsList = "";
        String team2RoundPointsList = "";

        for(int i = 0; i < team1RoundPoints.size(); i++){
            team1TotalPoints += team1RoundPoints.get(i);
            team1RoundPointsList += Integer.toString(team1RoundPoints.get(i))+"\n";
        }
        for(int i = 0; i < team2RoundPoints.size(); i++){
            team2TotalPoints += team2RoundPoints.get(i);
            team2RoundPointsList += Integer.toString(team2RoundPoints.get(i))+"\n";
        }
        gameController.SetGameOverviewTeamTotalPoints("Team 1", Integer.toString(team1TotalPoints));
        gameController.SetGameOverviewTeamTotalPoints("Team 2", Integer.toString(team2TotalPoints));

        gameController.SetGameOverviewTeamListPoints("Team 1", team1RoundPointsList);
        gameController.SetGameOverviewTeamListPoints("Team 2", team2RoundPointsList);

    }

    public  void UpdateGameSettings(int nTeam1points, int nTeam2points){
        roundCount ++;
        roundsList.add(roundCount);
        team1RoundPoints.add(nTeam1points);
        team2RoundPoints.add(nTeam2points);
        team1Points += nTeam1points;
        team2Points += nTeam2points;
    }



    //Close the current Game
    public void EndGame(View view){



    }

    public int RandomIntReturn(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
        return randomNum;
    }


}
