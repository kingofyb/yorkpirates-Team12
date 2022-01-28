package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen extends ScreenAdapter {
    private final YorkPirates game;

    private final String time;
    private final String points;
    private final String loot;
    private final boolean win;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public EndScreen(YorkPirates game, float seconds, ScoreManager points, ScoreManager loot, boolean win){
        this.game = game;
        this.time = ((int)(seconds / 60) == 0 ? "" : ((int)(seconds / 60) + " Minutes, ")) + (int)(seconds % 60) + " Seconds Elapsed";
        this.points = points.Get() == 0 ? "No Points Gained" : points.GetString() + " Points Gained";
        this.loot = loot.Get() == 0 ? "No Loot Won" : loot.GetString() + " Loot Won";
        this.win = win;
        game.camera.position.lerp(new Vector3(game.camera.viewportWidth/2, game.camera.viewportHeight/2, 0f), 1f);
    }

    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        game.batch.begin();
        if (win)    game.font.setColor(0.7f, 1.0f, 0.6f, 1f);
        else        game.font.setColor(1.0f, 0.8f, 0.6f, 1f);
        game.font.getData().setScale(1.2f);
        game.font.draw(game.batch, win ? "Game Win" : "Game Over", game.camera.viewportWidth/2, game.camera.viewportHeight*0.8f+game.font.getLineHeight()/2, 1, Align.center, true);
        game.font.setColor(1f, 1f, 1f, 1f);
        game.font.getData().setScale(0.7f);
        game.font.draw(game.batch, time, game.camera.viewportWidth/2, game.camera.viewportHeight*0.6f+game.font.getLineHeight()/2, 0.9f, Align.center, true);
        game.font.draw(game.batch, points, game.camera.viewportWidth/2, game.camera.viewportHeight*0.5f+game.font.getLineHeight()/2, 0.9f, Align.center, true);
        game.font.draw(game.batch, loot, game.camera.viewportWidth/2, game.camera.viewportHeight*0.4f+game.font.getLineHeight()/2, 0.9f, Align.center, true);
        game.batch.end();
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(new TitleScreen(game));
        }
    }

    /**
     * Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){

    }
}
