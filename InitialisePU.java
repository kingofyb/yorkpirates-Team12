package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class InitialisePU extends PowerUps{


    private Array<Texture> powerTexture;
    private Array<GameObject> powers;
    
    private Array<Float> powerRotations;
    private final Array<Texture> powerImages;

    InitialisePU(YorkPirates game, Array<Texture> sprites, float x, float y, float scale, String powerTexture) {
        // public GiveMoreDamage(Texture sprites, float x, float y, float scaleX, float scaleY)
        super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale, powerTexture);
        //super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale);
        
        (Array<Texture> frames, float fps, float x, float y, float width, float height){
        
        
        this.powerTexture = new Array<>();
        this.powers = new Array<>();
        this.powerTexture.add(new Texture(Gdx.files.internal(powerTexture)));
        powerImages = new Array<>();
        for(int i = 0; i < sprites.size; i++) {
            powerImages.add(sprites.get(i));
        } 
    }
    private void destroy(GameScreen screen){
        screen.powerups.removeValue(this,true);
    }
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        // Draw powerup
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);

        // Draw boats before college so under
        batch.setShader(null);
        for(int i = 0; i < powers.size; i++){
            PowerUps powers = powers.get(i); // was GameObject
            batch.draw(powerTexture.get(0), powers.x+powers.height, powers.y, 0,0, powers.width, powers.height, 1f, 1f, powerRotations.get(i), 0, 0, powerTexture.get(0).getWidth(), powerTexture.get(0).getHeight(), false, false);
        }
    }
    public void addPower(float x, float y, float rotation){
        powers.add(new PowerUps(powerTexture, 0, this.x+x, this.y+y, 25, 12));
        powerRotations.add(rotation);
    }
    
}
