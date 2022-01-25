package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.engteam14.yorkpirates.HealthBar;

public class College extends GameObject {

    private int collegeHealth;
    private HealthBar collegeBar;
    public boolean enemy;

    private final String collegeName;
    private static final int collegeMaxHealth = 200;
    private static final int pointsGained = 50;
    private static final int lootGained = 15;

    /**
     * Generates a college object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param name      The name of the college.
     * @param foe       Boolean determining whether college is an enemy.
     */
    public College(Array<Texture> frames, float fps, float x, float y, float width, float height, String name, boolean foe){
        super(frames, fps, x, y, width, height);
        collegeName = name;
        collegeHealth = collegeMaxHealth;

        enemy = foe;
        collegeBar = new HealthBar(enemy,collegeMaxHealth,collegeHealth,collegeName);
    }

    /**
     * Called when a projectile hits them.
     * @param damage    The damage dealt by the projectile.
     */
    public void hit(GameScreen screen, float damage){
        collegeHealth -= damage;
        if(collegeHealth > 0){
            collegeBar.update(collegeHealth);
        }else{
            screen.points.Add(pointsGained);
            screen.loot.Add(lootGained);
            collegeBar = null;
            destroy(screen);
        }
    }

    private void destroy(GameScreen screen){
        screen.colleges.removeValue(this,true);
    }
}
