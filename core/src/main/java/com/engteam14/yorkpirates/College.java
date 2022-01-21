package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.engteam14.yorkpirates.HealthBar;

public class College extends GameObject {

    public College(Array<Texture> frames, float fps){
        super(frames, fps);
    }

    public College(Array<Texture> frames, float fps, int x, int y){
        super(frames, fps, x, y);
    }

    public College(Array<Texture> frames, float fps, int x, int y, int width, int height){
        super(frames, fps, x, y, width, height);
    }

    public void update(){

    }

}
