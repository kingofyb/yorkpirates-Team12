package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleScreen extends ScreenAdapter {
    private final YorkPirates game;

    private static final float logoScale = 0.33f;

    private final Texture title;

    /**
     *  Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public TitleScreen(YorkPirates game){
        this.game = game;
        title = new Texture("libgdx.png");
    }

    /**
     *  Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        game.batch.begin();
        float newheight = title.getHeight()*(game.camera.viewportWidth*logoScale / title.getWidth());
        game.batch.draw(title, game.camera.viewportWidth*((1-logoScale)/2), game.camera.viewportHeight/2-newheight/2, game.camera.viewportWidth*logoScale, newheight);
        game.batch.end();
    }

    /**
     *  Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
        }
    }

    /**
     *  Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){
        title.dispose();
    }
}
