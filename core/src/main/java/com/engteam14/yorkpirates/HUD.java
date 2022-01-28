package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class HUD {

    public Stage stage1;
    public Label score;

    private final Label loot;

    public HUD(GameScreen screen){
        //initialise the stage
        System.out.println("rendering");
        stage1 = new Stage(screen.viewport);
        Gdx.input.setInputProcessor(stage1);

        //create main screen table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
        //table.setDebug(true);

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("uiskin");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.addRegions(atlas);
        table.row();
        score = new Label(screen.points.GetString(), skin);
        score.setFontScale(5);
        table.add(score);
        table.add().prefWidth(screen.viewport.getScreenWidth()-80f);
        loot = new Label(screen.loot.GetString(), skin);
        loot.setFontScale(5);
        table.add(loot).padRight(5).left();

        table.row();
        table.add().prefHeight(screen.viewport.getScreenHeight());
        table.row();

        ImageButton button1 = new ImageButton(skin, "default");
        ImageButton buttonMute = new ImageButton(skin, "music");
        buttonMute.setChecked(true);
        table.add(button1).size(64,64).left();
        table.add(new Label("table", skin)).expandX();
        table.add(buttonMute).size(64,64).right();
        table.setTouchable(Touchable.enabled);
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

        stage1.addActor(table);
        System.out.println("draw");

        Gdx.input.setInputProcessor(stage1);
    }

    public void renderStage(GameScreen screen){
        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());

        stage1.draw();
    }
}
