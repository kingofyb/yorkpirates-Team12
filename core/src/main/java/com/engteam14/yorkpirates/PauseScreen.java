package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;

    public PauseScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;
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
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(screen);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.closeGame(this);
        }
    }
}