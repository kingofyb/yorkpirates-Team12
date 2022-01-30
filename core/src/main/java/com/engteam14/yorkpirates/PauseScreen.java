package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;


public class PauseScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private Skin skin;
    private Stage pauseStage;

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

     //   table.debug();
        ImageTextButton resumeB = new ImageTextButton("Resume", skin, "Resume" );
        resumeB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(screen);

            }
        });
        TextButton quitB = new TextButton("Quit", skin );
        quitB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();

            }
        });
        TextButton restartB = new TextButton("End Game", skin );
        restartB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.gameEnd(false);

            }
        });
        ImageTextButton music = new ImageTextButton("", skin );
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


        table.columnDefaults(0).width(screen.viewport.getScreenWidth()/5f);
        table.columnDefaults(5).width(screen.viewport.getScreenWidth()/5f);



        table.row().padTop(20f);
        table.add().expandY();
        table.add(title).colspan(4).expandY().fill();

        table.add().expandY();


        table.row().padBottom(10f).padTop(10f);
        table.add().expand();
        table.add().expand();
        table.add(resumeB).expand().colspan(2);
        table.add().expand();
        table.add().expand();

        table.row().padTop(10f).padBottom(10f);
        table.add().expand();
        table.add().expand();
        table.add(restartB).expand().fillX().colspan(2).uniform();
        table.add().expand();
        table.add().expand();
        table.row().padTop(10f).padBottom(10f);

        table.add().expand();
        table.add().expand();
        table.add(music).expand().fillX().colspan(2).uniform();
        table.add().expand();
        table.add().expand();

        table.row().padTop(10f).padBottom(10f);
        table.add().expand();
        table.add().expand();
        table.add(quitB).expand().fillX().colspan(2).uniform();
        table.add().expand();
        table.add().expand();
        table.row().padTop(10f).padBottom(10f);

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
        Gdx.input.setInputProcessor(pauseStage);

        pauseStage.draw();



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
