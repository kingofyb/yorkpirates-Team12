package com.engteam14.yorkpirates;

public class ScoreManager {

    private int score;

    public ScoreManager(){
        score = 0;
    }

    public ScoreManager(int defaultScore){
        score = defaultScore;
    }

    public void Add(int amount){
        score += amount;
    }

    public void Subtract(int amount){
        Add(-amount);
    }

    public int Get(){
        return score;
    }

    public String GetString() {
        return Integer.toString(score);
    }
}
