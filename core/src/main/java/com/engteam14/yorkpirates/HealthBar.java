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

    private final float maxHealth;
    private final float startWidth;
    private float currentHealth;

    /**
     * Generates a college object within the game with animated frame(s) and a hitbox.
     * @param entity    The college which this bar is attached to.
     * @param frames    The animation frames, or a single sprite.
     */
    public HealthBar(College entity,Array<Texture> frames) {
        super(frames, 0, entity.x, entity.y + entity.height/2 + 2f, entity.width, 2f, entity.team);

        startWidth = entity.width;
        maxHealth = College.collegeMaxHealth;
        currentHealth = maxHealth;
    }

    public void resize(float currentValue){
        currentHealth = currentValue;
        this.width = startWidth * (currentHealth/maxHealth);
    }
}
