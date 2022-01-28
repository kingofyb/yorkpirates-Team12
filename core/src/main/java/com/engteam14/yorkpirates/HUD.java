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

    public static Stage stage1;
    public static Label score;
    private static Table table;
    private static Skin skin;
    private static Label loot;

    public static void HUDinitialise(GameScreen screen){
        //initialise the stage
        System.out.println("rendering");
        stage1 = new Stage(screen.viewp);
        Gdx.input.setInputProcessor(stage1);

//create main screen table
        table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
        table.setDebug(true);

/*// create bottom bar table
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
        midsec.setHeight(table.getHeight()-topbar.getHeight()- bottombar.getHeight());*/

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.addRegions(atlas);
        table.row();
        score = new Label(screen.points.GetString(), skin);
        score.setFontScale(5);
        table.add(score);
        table.add().prefWidth(screen.viewp.getScreenWidth()-80f);
        loot = new Label(screen.loot.GetString(), skin);
        loot.setFontScale(5);
        table.add(loot).padRight(5).left();

        table.row();
        table.add().prefHeight(screen.viewp.getScreenHeight());
        table.row();

        Button button1 = new Button(skin);
        Button buttonMute = new TextButton("Mute",skin);
        table.add(button1).size(64,64).left();
        table.add(new Label("table", skin)).expandX();
        table.add(buttonMute).size(64,64).right();
        table.setTouchable(Touchable.enabled);
        button1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.gameEnd(true );
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
    public static void renderStage(GameScreen screen){
        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());

        stage1.draw();
    }

}
