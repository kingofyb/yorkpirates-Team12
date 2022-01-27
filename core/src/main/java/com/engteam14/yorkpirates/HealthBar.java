package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class HealthBar extends GameObject {

    private final float startWidth;
    /**
     * Generates a college object within the game with animated frame(s) and a hitbox.
     * @param entity    The college which this bar is attached to.
     * @param frames    The animation frames, or a single sprite.
     */
    public HealthBar(GameObject entity,Array<Texture> frames) {
        super(frames, 0, entity.x, entity.y + entity.height/2 + 2f, entity.width, 2f, entity.team);

        startWidth = entity.width;
        setMaxHealth(entity.maxHealth);
    }

    /**
     * Generates a college object within the game with animated frame(s) and a hitbox.
     * @param currentValue  The current health value of the attached object
     */
    public void resize(float currentValue){
        currentHealth = currentValue;
        this.width = startWidth * (currentValue/maxHealth);
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    @Override
    void move(float x, float y){
        this.x = x;
        this.y = y;
    }
}
