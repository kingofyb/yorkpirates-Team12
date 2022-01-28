package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.engteam14.yorkpirates.HealthBar;

import java.util.Objects;

import static java.lang.Math.abs;

public class College extends GameObject {

    public boolean nearPlayer = false;
    private HealthBar collegeBar;
    private Indicator direction;

    private final String collegeName;
    private static final int pointsGained = 50;
    private static final int lootGained = 15;
    private static final int shootFrequency = 1000; // How often the college can shoot.
    private long lastShotFired;

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
    public College(Array<Texture> frames, float fps, float x, float y, float width, float height, String name, String team, Player player){
        super(frames, fps, x, y, width, height, team);
        collegeName = name;
        setMaxHealth(500);
        lastShotFired = 0;

        Array<Texture> healthBarSprite = new Array<>();
        Array<Texture> indicatorSprite = new Array<>();
        if(Objects.equals(team, GameScreen.playerTeam)){
            healthBarSprite.add(new Texture("allyHealthBar.png"));
            indicatorSprite.add(new Texture("homeArrow.png"));
        }else{
            healthBarSprite.add(new Texture("enemyHealthBar.png"));
            indicatorSprite.add(new Texture("questArrow.png"));
        }
        collegeBar = new HealthBar(this,healthBarSprite);
        direction = new Indicator(this,player,indicatorSprite);
    }

    /**
     * Called once per frame. Used to perform calculations such as collision.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    public void update(GameScreen screen, OrthographicCamera camera){
        direction.move();
        float playerX = screen.player.x;
        float playerY = screen.player.y;
        float detectionRadius = 90f;
        nearPlayer = abs(this.x - playerX) < (detectionRadius*1.5) && abs(this.y - playerY) < detectionRadius;

        if(nearPlayer){
            direction.visible = false;

            if(!Objects.equals(team, GameScreen.playerTeam)) { // Checks if the college is an enemy of the player
                if (TimeUtils.timeSinceMillis(lastShotFired) > shootFrequency){
                    lastShotFired = TimeUtils.millis();
                    Array<Texture> sprites = new Array<>();
                    sprites.add(new Texture("tempProjectile.png"));
                    screen.projectiles.add(new Projectile(sprites, 0, this, playerX, playerY, team));
                }
            }else if(Objects.equals(collegeName, "Home")){
                if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    boolean victory = true;
                    for(int i = 0; i < screen.colleges.size; i++) {
                        if(!Objects.equals(screen.colleges.get(i).team, GameScreen.playerTeam)){
                            victory = false;
                        }
                    }
                    if(victory){
                        screen.gameEnd(true);
                    }else{
                        //Display Message "Still need to defeat ALL colleges to complete the game"
                    }

                }
            }
        }else{
            direction.visible = true;
        }
    }

    /**
     * Called when a projectile hits the college.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    @Override
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        currentHealth -= damage;
        if(currentHealth > 0){
            collegeBar.resize(currentHealth);
        }else{
            if(!Objects.equals(team, GameScreen.playerTeam)){ // Checks if the college is an enemy of the player
                screen.points.Add(pointsGained);
                screen.loot.Add(lootGained);

                Array<Texture> healthBarSprite = new Array<>();
                Array<Texture> indicatorSprite = new Array<>();
                healthBarSprite.add(new Texture("allyHealthBar.png"));
                indicatorSprite.add(new Texture("homeArrow.png"));

                collegeBar.changeImage(healthBarSprite,0);
                direction.changeImage(indicatorSprite,0);

                team = GameScreen.playerTeam;
            }else{
                collegeBar = null;
                direction = null;
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
        direction.draw(batch,0);
    }
}
