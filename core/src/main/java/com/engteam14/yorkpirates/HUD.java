package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class HUD {

    private final Stage stage1;

    public HUD(GameScreen screen){
        //initialise the stage

        stage1 = new Stage(GameScreen.viewp);
        Gdx.input.setInputProcessor(stage1);

        Table table = new Table();
        table.setPosition(0, 0);
        table.setSize(GameScreen.viewp.getCamera().viewportWidth , GameScreen.viewp.getCamera().viewportWidth-50);
        table.setPosition(0, 0);
        table.setDebug(true);

        Table table2 = new Table();
        table2.setSize(GameScreen.viewp.getCamera().viewportWidth , 64);
        table2.setDebug(true);

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        skin.addRegions(atlas);

        Button button1 = new Button(skin);
        Button buttonMute = new TextButton("Mute",skin);
        table2.add(button1).size(64,64).left();
        table2.add().size(GameScreen.viewp.getCamera().viewportWidth-128,64);
        table2.add(buttonMute).size(64,64).right();

        table2.align(Align.left | Align.bottom);
        table2.add().size(GameScreen.viewp.getCamera().viewportWidth-128,64);
        button1.addListener(new ClickListener() {
                                public void clicked(InputEvent event, float x, float y) {
                                    screen.gameEnd(true );
                                }
                            });
        buttonMute.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (screen.instrumental.getVolume() == 0) {
                    screen.instrumental.setVolume(1);
                } else {
                    screen.instrumental.setVolume(0);
                    }

            }
        });
        table.row();
        stage1.addActor(table);
        stage1.addActor(table2);

    }
    public void renderStage(){
        stage1.draw();
    }

}
