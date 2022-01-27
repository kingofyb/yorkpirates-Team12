package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;


public class HUD {

    private static Stage stage1;

    public static void HUDinitialise(){
        //initialise the stage

        stage1 = new Stage(GameScreen.viewp);
        Table table = new Table();
        table.setFillParent(true);
        Table table2 = new Table();
        table.setPosition(190, 142);
        table2.setSize(64, 64);
        table.setSize(260, 195);
        table.setPosition(0, 0);
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        skin.addRegions(atlas);



        Button button1 = new Button(skin);

        table2.add(button1).size(64,64);
        button1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return false;
                }
            });
        table.row();
        stage1.addActor(table);
        stage1.addActor(table2);
    }
    public static void renderStage(){
        stage1.draw();
    }

}
