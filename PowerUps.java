package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PowerUps extends GameObject{

    public static int capturedPowerUpsCount = 0;
    
    private final Array<Texture> PowerUpImages;
    private Array<Float> powerRotations;
    private final String powerUpName;
    private Array<GameObject> power;

    public PowerUps(Array<Texture> sprites, float x, float y, float scale, String name, Player player, String PowerUpImages) {
        super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale, PowerUpImages);
        powerUpName = name;
        this.power = new Array<>();
        this.powerRotations = new Array<>();
        for(int i = 0; i < sprites.size; i++) {
            PowerUpImages.add(sprites.get(i));
        }
    }

    /**
     * Add a powerup.
     * @param x The x position of the new boat relative to the college.
     * @param y The y position of the new boat relative to the college.
     */
    public void addPower(float x, float y, float rotation){
        power.add(new GameObject(PowerUpImages, 0, this.x+x, this.y+y, 25, 12, team));
        powerRotations.add(rotation);
    }


}