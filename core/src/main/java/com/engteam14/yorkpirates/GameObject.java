package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;

public class GameObject {
    Texture sprite;
    Animation<Texture> anim;
    Rectangle hitbox;
    float x;
    float y;
    float width;
    float height;

    public GameObject(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<Texture>(fps==0?0:(1f/fps), frames);
        x = 0;
        y = 0;
        width = sprite.getWidth();
        height = sprite.getHeight();
        setHitbox();
    }

    public GameObject(Array<Texture> frames, float fps, float x, float y){
        sprite = frames.get(0);
        anim = new Animation<Texture>(fps==0?0:(1f/fps), frames);
        this.x = x;
        this.y = y;
        width = sprite.getWidth();
        height = sprite.getHeight();
        setHitbox();
    }

    public GameObject(Array<Texture> frames, float fps, float x, float y, float width, float height){
        sprite = frames.get(0);
        anim = new Animation<Texture>(fps==0?0:(1f/fps), frames);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setHitbox();
    }

    void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
    }

    void setHitbox(){
        hitbox = new Rectangle();
        updateHitboxPos();
        hitbox.width = width;
        hitbox.height = height;
    }

    void updateHitboxPos() {
        hitbox.x = x - width/2;
        hitbox.y = y - height/2;
    }

    boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitbox.overlaps(rect);
    }

    void update(GameScreen screen, OrthographicCamera camera){
        if (overlaps(screen.player.hitbox)){
            screen.player.hit(screen, camera, this);
        }
    }

    void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
    }
}
