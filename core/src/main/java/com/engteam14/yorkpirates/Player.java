package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

public class Player extends GameObject {

    public Vector2 camDiff;
    private Vector2 oldPos;
    private HealthBar playerHealth;

    private int previousDirectionX;
    private int previousDirectionY;
    private boolean moving = false;
    private long lastMovementScore;

    private static final int pointFrequency = 1000; // How often the player gains points by moving.
    private static final double cameraSlack = 0.1; // What percentage of the screen the player can move in before the camera follows.
    private static final float speed = 70f; // Player movement speed.

    /**
     *  Generates a player object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param team      The team the player is on.
     */
    public Player(Array<Texture> frames, float fps, String team){
        this(frames, fps,0,0,team);
    }

    /**
     *  Generates a player object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param team      The team the player is on.
     */
    public Player(Array<Texture> frames, float fps, float x, float y, String team){
        this(frames,fps,x,y,frames.get(0).getWidth(),frames.get(0).getHeight(),team);
    }

    /**
     *  Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the player is on.
     */
    public Player(Array<Texture> frames, float fps, float x, float y, float width, float height, String team){
        super(frames, fps, x, y, width, height, team);
        lastMovementScore = 0;

        setMaxHealth(200);
        Array<Texture> sprites = new Array<>();
        sprites.add(new Texture("allyHealthBar.png"));
        playerHealth = new HealthBar(this,sprites);
    }

    /**
     *  Called once per frame. Used to perform calculations such as player/camera movement.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    @Override
    public void update(GameScreen screen, OrthographicCamera camera){
        oldPos = new Vector2(x,y);
        // Movement Calculations
        int horizontal = ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0);
        int vertical = ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ? 1 : 0);
        if (horizontal != 0 || vertical != 0){
            move(speed*horizontal, speed*vertical);
            updateHitboxPos();
            if (TimeUtils.timeSinceMillis(lastMovementScore) > pointFrequency){
                lastMovementScore = TimeUtils.millis();
                screen.points.Add(1);
            }
            previousDirectionX = horizontal;
            previousDirectionY = vertical;
            moving = true;
        } else moving = false;

        // Camera Calculations
        ProcessCamera(screen, camera);
    }

    /**
     * Moves the player within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    @Override
    void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
        playerHealth.move(this.x, this.y + height/2 + 2f);
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
            playerHealth.resize(currentHealth);
        }else{
            playerHealth = null;
            screen.gameEnd(false);
        }
    }

    /**
     *  Called after update(), calculates whether the camera should follow the player and passes it to the game screen.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    private void ProcessCamera(GameScreen screen, OrthographicCamera camera) {
        camDiff = new Vector2(x - camera.position.x, y - camera.position.y);
        screen.followPlayer = Math.abs(camDiff.x) > camera.viewportWidth / 2 * cameraSlack || Math.abs(camDiff.y) > camera.viewportWidth / 2 * cameraSlack;
    }

    /**
     *  Called when drawing the player.
     * @param batch         The batch to draw the player within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        Texture frame = sprite;
        if (moving) {
            frame = anim.getKeyFrame(elapsedTime, true);
        }
        float rotation = (float) Math.toDegrees(Math.atan2(previousDirectionY, previousDirectionX));

        //batch.draw(frame, x - width/2, y - height/2, width, height);
        batch.draw(frame, x - width/2, y - height/2, width/2, height/2, width, height, 1f, 1f, rotation, 0, 0, frame.getWidth(), frame.getHeight(), false, false);
        if(!(playerHealth == null)) playerHealth.draw(batch, 0);

    }

    /**
     *  Called by another object if the player hits them.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     * @param source    The object the player hit.
     */
    public void hit(GameScreen screen, OrthographicCamera camera, GameObject source){
        Vector2 newpos = new Vector2(x, y);
        x = oldPos.x;
        updateHitboxPos();
        if (source.overlaps(hitbox)) {
            x = newpos.x;
            y = oldPos.y;
            updateHitboxPos();
            if (source.overlaps(hitbox)) {
                x = oldPos.x;
                updateHitboxPos();
            }
        }
        moving = false;
        ProcessCamera(screen, camera);
    }
}
