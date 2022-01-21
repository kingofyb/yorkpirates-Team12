package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class Player extends GameObject {

    float speed = 70f;

    public Player(Array<Texture> frames, float fps){
        super(frames, fps);
    }

    public Player(Array<Texture> frames, float fps, int x, int y){
        super(frames, fps, x, y);
    }

    public Player(Array<Texture> frames, float fps, int x, int y, int width, int height){
        super(frames, fps, x, y, width, height);
    }

    public void update(){
        int horizontal = ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0);
        int vertical = ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ? 1 : 0);
        move(speed*horizontal, speed*vertical);
    }
}
