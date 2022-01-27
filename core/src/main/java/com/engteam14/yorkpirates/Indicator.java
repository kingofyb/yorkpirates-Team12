package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Objects;
import static java.lang.Math.*;

public class Indicator extends GameObject{

    private final Player player;
    private final College college;

    private Vector2 gradient;

    public Indicator(College college, Player player, Array<Texture> frames) {
        super(frames, 0, player.x, player.y, 10f, 5f, college.team);

        this.player = player;
        this.college = college;
        gradient = updateGradient();
        move();
    }

    /**
     * Called when drawing the Indicator.
     * @param batch         The batch to draw the player within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        Texture frame = sprite;
        float rotation = (float) Math.toDegrees(Math.atan2(gradient.y,gradient.x));

        batch.draw(frame, x - width/2, y - height/2, width/2, height/2, width, height, 1f, 1f, rotation, 0, 0, frame.getWidth(), frame.getHeight(), false, false);
    }

    /**
     * Called when updating the gradient of the arrow.
     */
    public Vector2 updateGradient(){
        float changeInX = college.x - player.x;
        float changeInY = college.y - player.y;
        float scaleFactor = max(abs(changeInX),abs(changeInY));
        float dx = changeInX / scaleFactor;
        float dy = changeInY / scaleFactor;

        return (new Vector2(dx, dy));
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     */
    void move(){
        gradient = updateGradient();
        this.x = player.x + (50 * gradient.x);
        this.y = player.y + (50 * gradient.y);
    }
}
