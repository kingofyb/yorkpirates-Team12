package com.engteam14.yorkpirates;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class ScoreManager {

    private int score;

    /**
     *  Initialises a ScoreManager with a default score of 0.
     */
    public ScoreManager(){
        score = 0;
    }

    /**
     *  Initialises a ScoreManager.
     * @param defaultScore  the default score value.
     */
    public ScoreManager(int defaultScore){
        score = defaultScore;
    }

    /**
     *  Adds an integer value to the score.
     * @param amount    the value to be added.
     */
    public void Add(int amount, HUD hud1){
        score += amount;
        hud1.HUDinitialise();

    }

    /**
     *  Subtracts an integer value from the score.
     * @param amount    the value to be subtracted.
     */
    public void Subtract(int amount ,HUD hud1){
        Add(-amount, hud1);
    }

    /**
     *  Gets the score value in integer form.
     * @return  the score.
     */
    public int Get(){
        return score;
    }

    /**
     *  Gets the score value in string form.
     * @return  the score.
     */
    public String GetString() {
        return Integer.toString(score);
    }
}
