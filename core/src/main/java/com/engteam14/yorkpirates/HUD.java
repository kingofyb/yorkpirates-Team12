package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


public class HUD {

    private static Stage stage1;
    public static Label score;
    public static void HUDinitialise(){
        //initialise the stage

        stage1 = new Stage(GameScreen.viewp);
        Gdx.input.setInputProcessor(stage1);
//create main screen table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
        table.setDebug(true);
// create bottom bar table
        Table bottombar = new Table();
        bottombar.setDebug(true);
        bottombar.align(Align.left | Align.bottom);
//create top bar table
        Table topbar = new Table();
        topbar.align(Align.left | Align.top);
        topbar.setDebug(true);
//create mid sec
        Table midsec = new Table();
        midsec.setDebug(true);
        midsec.setHeight(table.getHeight()-topbar.getHeight()- bottombar.getHeight());

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.addRegions(atlas);




        Button button1 = new Button(skin);
        Button buttonMute = new TextButton("Mute",skin);
        bottombar.add(button1).size(64,64).left();
        bottombar.add(new Label("bottombar", skin)).size(GameScreen.viewp.getCamera().viewportWidth-128,64);
        bottombar.add(buttonMute).size(64,64).right();

        button1.addListener(new ClickListener() {
                                public void clicked(InputEvent event, float x, float y) {
                                    GameScreen.gameEnd(true );
                                }
                            });
        buttonMute.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (GameScreen.instrumental.getVolume() == 0) {
                    GameScreen.instrumental.setVolume(1);
                } else {
                    GameScreen.instrumental.setVolume(0);
                    }

            }
        });
        Label score = new Label(GameScreen.points.GetString(), skin);
        score.setFontScale(5);
        topbar.add(score).prefWidth(GameScreen.viewp.getCamera().viewportWidth).left();
        table.row();
        table.add(topbar);
        table.row();
        table.add(midsec).expand();
        table.row();
        table.add(bottombar).fillX();
        stage1.addActor(table);
        stage1.draw();



    }
    public static void renderStage(){
        stage1.draw();
    }

}
