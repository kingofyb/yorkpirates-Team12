package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends GameObject {

    private static final int POINT_FREQ = 1000; // How often the player gains points by moving.
    private static final double CAMERA_SLACK = 0.1; // What percentage of the screen the player can move in before the camera follows.
    private static final float SPEED = 70f; // Player movement speed.

    public Vector2 camdiff;
    private Vector2 oldpos;
    private long lastMovementScore;
    private int lastxdir;
    private int lastydir;
    private boolean moving = false;

    /**
     *  Generates a player object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     */
    public Player(Array<Texture> frames, float fps){
        super(frames, fps);
        lastMovementScore = 0;
    }

    /**
     *  Generates a player object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     */
    public Player(Array<Texture> frames, float fps, float x, float y){
        super(frames, fps, x, y);
        lastMovementScore = 0;
    }

    /**
     *  Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     */
    public Player(Array<Texture> frames, float fps, float x, float y, float width, float height){
        super(frames, fps, x, y, width, height);
        lastMovementScore = 0;
    }

    /**
     *  Called once per frame. Used to perform calculations such as player/camera movement.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    @Override
    public void update(GameScreen screen, OrthographicCamera camera){
        oldpos = new Vector2(x,y);
        // Movement Calculations
        int horizontal = ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0);
        int vertical = ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ? 1 : 0);
        if (horizontal != 0 || vertical != 0){
            move(SPEED*horizontal, SPEED*vertical);
            updateHitboxPos();
            if (TimeUtils.timeSinceMillis(lastMovementScore) > POINT_FREQ){
                lastMovementScore = TimeUtils.millis();
                screen.points.Add(1);
            }
            lastxdir = horizontal;
            lastydir = vertical;
            moving = true;
        } else moving = false;

        // Camera Calculations
        ProcessCamera(screen, camera);
    }

    /**
     *  Called after update(), calculates whether the camera should follow the player and passes it to the game screen.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    private void ProcessCamera(GameScreen screen, OrthographicCamera camera) {
        camdiff = new Vector2(x - camera.position.x, y - camera.position.y);
        screen.followPlayer = Math.abs(camdiff.x) > camera.viewportWidth / 2 * CAMERA_SLACK || Math.abs(camdiff.y) > camera.viewportWidth / 2 * CAMERA_SLACK;
    }

    /**
     *  Called when drawing the player.
     * @param batch         The batch to draw the player within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        Texture frame = sprite;
        float rx = x - width/2;
        float rw = width;
        if (moving) {
            frame = anim.getKeyFrame(elapsedTime, true);
        }
        if (lastxdir < 0 || (lastydir < 0 && lastxdir <= 0)){
            rx += width;
            rw = -rw;
        }
        batch.draw(frame, rx, y - height/2, rw, height);
    }

    /**
     *  Called by another object if the player hits them.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     * @param source    The object the player hit.
     */
    public void hit(GameScreen screen, OrthographicCamera camera, GameObject source){
        Vector2 newpos = new Vector2(x, y);
        x = oldpos.x;
        updateHitboxPos();
        if (source.overlaps(hitbox)) {
            x = newpos.x;
            y = oldpos.y;
            updateHitboxPos();
            if (source.overlaps(hitbox)) {
                x = oldpos.x;
                updateHitboxPos();
            }
        }
        moving = false;
        ProcessCamera(screen, camera);
    }
}
