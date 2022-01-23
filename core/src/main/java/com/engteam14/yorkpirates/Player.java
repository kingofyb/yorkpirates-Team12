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

    public static final int POINT_FREQ = 1000;
    public static final double CAMERA_SLACK = 0.1;

    public Vector2 camdiff;
    private Vector2 oldpos;
    private float speed = 70f;
    private long lastMovementScore;
    private int lastxdir;
    private int lastydir;
    private boolean moving = false;

    public Player(Array<Texture> frames, float fps){
        super(frames, fps);
        lastMovementScore = 0;
    }

    public Player(Array<Texture> frames, float fps, float x, float y){
        super(frames, fps, x, y);
        lastMovementScore = 0;
    }

    public Player(Array<Texture> frames, float fps, float x, float y, float width, float height){
        super(frames, fps, x, y, width, height);
        lastMovementScore = 0;
    }

    @Override
    public void update(GameScreen screen, OrthographicCamera camera){
        oldpos = new Vector2(x,y);
        // Movement Calculations
        int horizontal = ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0);
        int vertical = ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ? 1 : 0);
        if (horizontal != 0 || vertical != 0){
            move(speed*horizontal, speed*vertical);
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

    private void ProcessCamera(GameScreen screen, OrthographicCamera camera) {
        camdiff = new Vector2(x - camera.position.x, y - camera.position.y);
        if (Math.abs(camdiff.x) > camera.viewportWidth/2*CAMERA_SLACK || Math.abs(camdiff.y) > camera.viewportWidth/2*CAMERA_SLACK){
                screen.followPlayer = true;
        } else  screen.followPlayer = false;
    }

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
