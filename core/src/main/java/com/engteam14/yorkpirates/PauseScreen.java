package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;


public class PauseScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage pauseStage;

    public PauseScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        pauseStage = new Stage(screen.viewport);
        Gdx.input.setInputProcessor(pauseStage);

        Table table = new Table();
        table.setFillParent(true);
        table.setSize(screen.viewport.getScreenWidth(), screen.viewport.getScreenHeight());
        table.setPosition(0, 0);
        table.setTouchable(Touchable.enabled);

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        TextButton resumeB = new TextButton("Resume", skin);
        resumeB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                continueGame();
            }
        });

        TextButton quitB = new TextButton("Quit", skin);
        quitB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();

            }
        });

        TextButton restartB = new TextButton("End Game", skin);
        restartB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.gameEnd(false);

            }
        });

        TextButton music = new TextButton("", skin);
        if (screen.instrumental.getVolume() == 0) {
            music.setText("Turn Music ON");
        } else {
            music.setText("Turn Music OFF");

        }
        music.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.instrumental.getVolume() == 0) {
                    screen.instrumental.setVolume(1);
                    music.setText("Turn Music OFF");
                } else {
                    screen.instrumental.setVolume(0);
                    music.setText("Turn Music ON");

                }
            }
        });

        Texture titleT = new Texture(Gdx.files.internal("paused.png"));
        Image title = new Image(titleT);
        title.setScaling(Scaling.fit);

        table.columnDefaults(0).width(screen.viewport.getScreenWidth()/5f);
        table.columnDefaults(5).width(screen.viewport.getScreenWidth()/5f);

        table.row().pad(10);
        table.add().expand();
        table.add(title).colspan(4).fill().expand().pad(50f);

        table.add().expand();

        table.row();
        table.add().expand();
        table.add().expand();
        table.add(resumeB).expand().fillX().expand().colspan(2).padLeft(40).padRight(40);
        table.add().expand();
        table.add().expand();

        table.row();
        table.add().expand();
        table.add().expand();
        table.add(restartB).expand().fillX().colspan(2).padLeft(40).padRight(40);
        table.add().expand();
        table.add().expand();
        table.row();
        table.add().expand();
        table.add().expand();
        table.add(music).expand().fillX().colspan(2).padLeft(40).padRight(40);
        table.add().expand();
        table.add().expand();
        table.row();
        table.add().expand();
        table.add().expand();
        table.add(quitB).expand().fillX().colspan(2).padLeft(40).padRight(40);
        table.add().expand();
        table.add().expand();
        table.setBackground(skin.getDrawable("Selection"));

        pauseStage.addActor(table);
        pauseStage.draw();
        Gdx.input.setInputProcessor(pauseStage);
    }

    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        update();
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta);
        Gdx.input.setInputProcessor(pauseStage);
        pauseStage.draw();
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            continueGame();
        }
    }

    /**
     * Generates a HUD object within the game that controls elements of the UI.
     */
    private void continueGame() {
        screen.isPaused = false;
        screen.lastPause = screen.elapsedTime;
        game.setScreen(screen);
    }
}
