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

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     */
    public GameObject(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
        x = 0;
        y = 0;
        width = sprite.getWidth();
        height = sprite.getHeight();
        setHitbox();
    }

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     */
    public GameObject(Array<Texture> frames, float fps, float x, float y){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
        this.x = x;
        this.y = y;
        width = sprite.getWidth();
        height = sprite.getHeight();
        setHitbox();
    }

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     */
    public GameObject(Array<Texture> frames, float fps, float x, float y, float width, float height){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setHitbox();
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
    }

    /**
     * Sets the object's hitbox, based upon it's x, y, width and height values.
     */
    private void setHitbox(){
        hitbox = new Rectangle();
        updateHitboxPos();
        hitbox.width = width;
        hitbox.height = height;
    }

    /**
     * Updates the object's hitbox location to match the object's rendered location.
     */
    void updateHitboxPos() {
        hitbox.x = x - width/2;
        hitbox.y = y - height/2;
    }

    /**
     * Checks if this object overlaps with another.
     * @param rect  The other object to be checked against.
     * @return      True if overlapping, false otherwise.
     */
    public boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitbox.overlaps(rect);
    }

    /**
     * Called once per frame. Used to perform calculations such as collision.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    public void update(GameScreen screen, OrthographicCamera camera){
        if (overlaps(screen.player.hitbox)){
            screen.player.hit(screen, camera, this);
        }
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
    }
}
