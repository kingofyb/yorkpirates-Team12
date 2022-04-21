package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;

import static java.lang.Math.*;

public class Power extends PowerUps {

    private Array<Texture> powerTexture;
    private Array<PowerUps> powers;
    private Array<Float> powerRotations;
    /**
     * Generates a powerup object within the game with a hit-box.
     *
     * @param x    The x coordinate within the map to initialise the object at.
     * @param y    The y coordinate within the map to initialise the object at.
     * @param name The name of the college.
     * @param team The team the college is on.
     */
    public Power(Array<Texture> sprites, float x, float y, float scale, String powerup) {
        super(sprites, 0, x, y, sprites.get(0).getWidth() * scale, sprites.get(0).getHeight() * scale, powerup);

    }

    /**
     * Add a powerup to this power.
     * @param x The x position of the new boat relative to the college.
     * @param y The y position of the new boat relative to the college.
     */
    public void addPower(float x, float y, float rotation){
        powers.add(new PowerUps(powerTexture, 0, this.x+x, this.y+y, 25, 12, powerup));
        powerRotations.add(rotation);
    }

    /**
     * Called when the power-up needs to be destroyed.
     * @param screen    The main game screen.
     */
    private void destroy(GameScreen screen){
        screen.powerups.removeValue(this,true);
    }
}
