package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

import static java.lang.Math.abs;

public class College extends GameObject {

    public boolean nearPlayer = false;
    private HealthBar collegeBar;
    private Indicator direction;

    private float greyTime;
    private long lastShotFired;
    private final String collegeName;
    private final Array<Texture> collegeImages;

    private static final int lootGained = 15;
    private static final int pointsGained = 50;
    private static final int shootFrequency = 1000; // How often the college can shoot.

    /**
     * Generates a college object within the game with animated frame(s) and a hit-box.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param name      The name of the college.
     * @param team      The team the college is on.
     */
    public College(Array<Texture> sprites, float x, float y, float scale, String name, String team, Player player){
        super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale, team);

        collegeImages = new Array<>();
        for(int i = 0; i < sprites.size; i++) {
            collegeImages.add(sprites.get(i));
        }

        greyTime = 0;
        setMaxHealth(500);
        lastShotFired = 0;
        collegeName = name;

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
     */
    public void update(GameScreen screen){
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
                    }
                }
            }
        }else{
            direction.visible = true;
        }

        if(isGrey){
            if(greyTime > 5){
                isGrey = false;
                greyTime = 0;
            }else{
                greyTime += 1;
            }
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
        isGrey = true;

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

                Array<Texture> sprites = new Array<>();
                sprites.add(collegeImages.get(1));
                changeImage(sprites,0);

                collegeBar.changeImage(healthBarSprite,0);
                currentHealth = maxHealth;
                collegeBar.resize(currentHealth);
                screen.collegesCaptured += 1;
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
        if(isGrey){
            batch.setShader(shader); // Set our grey-out shader to the batch
        }
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
        batch.setShader(null);
        collegeBar.draw(batch, 0);
        direction.draw(batch,0);
    }
}
