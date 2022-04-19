package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

public class PowerUps{
    public float x;
    public float y;
    public float width;
    public float height;
    
    Rectangle hitBox;
    public String Power;
    
    Texture sprite;
    
    Animation<Texture> anim;

    PowerUps(Array<Texture> frames, float fps, float x, float y, float width, float height, String PowerType){
        changeImage(frames,fps);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setHitbox();
    }

    // public PowerUps(Texture texture, float x2, float y2, float scaleX, float scaleY) {
    // }

    public PowerUps(Texture texture, float x2, float y2, float scaleX, float scaleY) {
    }

    void changeImage(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
    }

    private void setHitbox(){
        hitBox = new Rectangle();
        updateHitboxPos();
        hitBox.width = width;
        hitBox.height = height;
    }

    void updateHitboxPos() {
        hitBox.x = x - width/2;
        hitBox.y = y - height/2;
    }

    boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitBox.overlaps(rect);
    }

    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
    }
}





// public class PowerUps extends Sprite implements Poolable{
//     public static int capturedPowerUpsCount;
//     public float x, y;
//     public boolean remove;
//     public boolean check;
//     public int value;
//     private float w, h;

//     public PowerUps(Texture texture, float x, float y, float scaleX, float scaleY){
//        super(texture);
//        setX(x);
//        setY(y);
//        this.remove = false;
//        this.w = this.getTexture().getWidth();
//        this.h = this.getTexture().getHeight();
//        this.setSize(w/scaleX, h/scaleY);
//        this.value = 1; 
//     }

//     public void draw(SpriteBatch spriteBatch){
//         super.draw(spriteBatch);
//     }

//     public void hit(){
//         getRemove();
//     }

//     public boolean getRemove(){
//         return this.remove;
//     }

//     /**
//      * checks for collision with the player
//      * @param r     pass in the player's BoundingRectangle
//      * @return      if coin and the player overlap, return true
//      *                  otherwise false.
//      */
//     public boolean collisionCheck(Rectangle r){
//         if (this.getBoundingRectangle().overlaps(r)){
//             this.remove = true;
//             return true;
//         }
//         return false;
//     }

//     /**
//      * Used for pooling
//      * reset the value of the powerup
//      * reset the remove attribute
//      */
//     @Override
//     public void reset() {
//         this.value = 1;
//         this.remove = false;
//     }

// }

// public class PowerUps extends GameObject{

//     public static int capturedPowerUpsCount = 0;
    
//     private final Array<Texture> PowerUpImages;
//     private Array<Float> powerRotations;
//     private final String powerUpName;
//     private Array<GameObject> power;

//     public PowerUps(Array<Texture> sprites, float x, float y, float scale, String name, Player player, String PowerUpImages) {
//         super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale, PowerUpImages);
//         powerUpName = name;
//         this.power = new Array<>();
//         this.powerRotations = new Array<>();
//         PowerUpImages = new Array<>();
//         for(int i = 0; i < sprites.size; i++) {
//             PowerUpImages.add(sprites.get(i));
//         }
//     }

//     /**
//      * Add a powerup.
//      * @param x The x position of the new boat relative to the college.
//      * @param y The y position of the new boat relative to the college.
//      */
//     public void addPower(float x, float y, float rotation){
//         power.add(new GameObject(PowerUpImages, 0, this.x+x, this.y+y, 25, 12, team));
//         powerRotations.add(rotation);
//     }


// }