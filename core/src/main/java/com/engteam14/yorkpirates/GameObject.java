package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class GameObject {
    Texture sprite;
    Animation<Texture> anim;
    int x;
    int y;
    int width;
    int height;

    public GameObject(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<Texture>(1f/fps, frames);
        x = 0;
        y = 0;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public GameObject(Array<Texture> frames, float fps, int x, int y){
        sprite = frames.get(0);
        anim = new Animation<Texture>(1f/fps, frames);
        this.x = x;
        this.y = y;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public GameObject(Array<Texture> frames, float fps, int x, int y, int width, int height){
        sprite = frames.get(0);
        anim = new Animation<Texture>((1f/fps), frames);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void move(int x, int y){
        x += x * Gdx.graphics.getDeltaTime();
        y += y * Gdx.graphics.getDeltaTime();
    }

    void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2);
    }
}
