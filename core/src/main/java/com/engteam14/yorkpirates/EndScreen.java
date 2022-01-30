package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen extends ScreenAdapter {
    private final YorkPirates game;
    private final Stage EndStage;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game      Passes in the base game class for reference.
     * @param screen    Passes in the game screen for reference.
     * @param win       Passes in the win status.
     */
    public EndScreen(YorkPirates game, GameScreen screen, boolean win){

        this.game = game;
        int seconds = (int) screen.elapsedTime;
        String time = ((seconds / 60) == 0 ? "" : ((seconds / 60) + " Minutes, ")) + (seconds % 60) + " Seconds Elapsed";
        String points = screen.points.Get() == 0 ? "No Points Gained" : screen.points.GetString() + " Points Gained";
        String loot = screen.loot.Get() == 0 ? "No Loot Won" : screen.loot.GetString() + " Loot Won";
        game.camera.position.lerp(new Vector3(game.camera.viewportWidth/2, game.camera.viewportHeight/2, 0f), 1f);
        String imageN;
        if (win) {
            imageN="game_win.png";
        }else{
            imageN="game_over.png";
        }

        Texture titleT = new Texture(Gdx.files.internal(imageN));
        Image title = new Image(titleT);

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        EndStage = new Stage(screen.viewport);
        Table table1 = new Table();
        table1.setFillParent(true);
        table1.setPosition(0, 0);
    //    table1.setDebug(true);
        Gdx.input.setInputProcessor(EndStage);
        ImageButton quitB = new ImageButton(skin, "Quit");
        ImageButton restartB = new ImageButton(skin, "Restart");

        quitB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();

            }
        });

        restartB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.restart();

            }
        });

        table1.row();
        table1.add().expand().uniform();
        title.setScaling(Scaling.fit);
        table1.add(title).expand().colspan(4).fill();
        table1.add().expand();

        //table row 2
        table1.row();
        table1.add().expand();
        table1.add().expand();
        table1.add(new Label(time, skin)).colspan(2).expand();
        table1.add().expand();
        table1.add().expand();

        //table row 3
        table1.row();
        table1.add().expand();
        table1.add().expand();
        table1.add(new Label(points, skin)).expand().colspan(2);
        table1.add().expand();
        table1.add().expand();

        //table row 4
        table1.row();
        table1.add().expand();
        table1.add().expand();
        table1.add(new Label(loot, skin)).expand().colspan(2);
        table1.add().expand();
        table1.add().expand();

        //table row 5
        table1.row();
        table1.add().expand().uniform();
        table1.add().expand();
        table1.add(restartB).expand().size(200f);
        table1.add(quitB).expand().size(200f);
        table1.add().expand();
        table1.add().expand();
        table1.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("background.png")))));

        EndStage.addActor(table1);
    }

    /**1
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        update();
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);

        EndStage.draw();


    /*        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
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
        game.batch.end();*/
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new TitleScreen(game));        }

    }
}
