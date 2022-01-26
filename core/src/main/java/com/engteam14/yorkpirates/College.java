package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.engteam14.yorkpirates.HealthBar;

import java.util.Objects;

public class College extends GameObject {

    public static final int collegeMaxHealth = 200;


    private int collegeHealth;
    private HealthBar collegeBar;

    private final Player player;
    private final String collegeName;
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
     * @param team      The team the college is on.
     */
    public College(Array<Texture> frames, float fps, float x, float y, float width, float height, String name, Player player, String team){
        super(frames, fps, x, y, width, height, team);
        collegeName = name;
        collegeHealth = collegeMaxHealth;
        this.player = player;

        Array<Texture> sprites = new Array<>();
        if(Objects.equals(team, player.team)){
            sprites.add(new Texture("allyHealthBar.png"));
        }else{
            sprites.add(new Texture("enemyHealthBar.png"));
        }
        collegeBar = new HealthBar(this,sprites);
    }

    /**
     * Called when a projectile hits them.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    public void hit(GameScreen screen, float damage, String projectileTeam){
        if(Objects.equals(team, projectileTeam)){ // Checks if projectile and college are on the same time
            return;
        }
        collegeHealth -= damage;
        if(collegeHealth > 0){
            collegeBar.resize(collegeHealth);
        }else{
            if(!Objects.equals(team, player.team)){ // Checks if the college is an enemy of the player
                screen.points.Add(pointsGained);
                screen.loot.Add(lootGained);
                Array<Texture> sprites = new Array<>();
                sprites.add(new Texture("allyHealthBar.png"));
                collegeBar = new HealthBar(this,sprites);
                team = player.team;
            }else{
                collegeBar = null;
                destroy(screen);
            }
        }
    }

    /**
     * Called when the college needs to be destroyed.
     * @param screen    The main game screen.
     */
    private void destroy(GameScreen screen){
        screen.colleges.removeValue(this,true);
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
        collegeBar.draw(batch, 0);
    }
}
