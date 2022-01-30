package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;



public class HUD {

    public static Stage stage1;
    public static Label score;
    private static Table table;
    private static Skin skin;
    private static Label loot;
    private static Table tasks;
    private static Label message;

    public static void HUDinitialise(GameScreen screen){
        //initialise the stage
        System.out.println("rendering");
        stage1 = new Stage(screen.viewport);
        Gdx.input.setInputProcessor(stage1);




        //create main screen table
        table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
   //     table.setDebug(true);



        //create skin atlas1
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);



        //create tasks table
        tasks =new Table();

        tasks.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("transparent.png"))));
    //    tasks.debug();
        Label message = new Label("These are the tasks to do:", skin);
        message.setFontScale(0.5f, 0.5f);
        CheckBox task1 = new CheckBox("Destroy all colleges", skin);
        CheckBox task2  = new CheckBox("Survive 5 seconds", skin);
        task1.setDisabled(true);
        task1.setDisabled(true);

        loot = new Label(screen.loot.GetString(), skin);
        score = new Label(screen.points.GetString(), skin);

        tasks.add();
        score.setFontScale(1.5f);
        tasks.add(score).fillY();
        tasks.add();
        loot.setFontScale(1.5f);

        tasks.add(loot).fillY();
        tasks.row();
        tasks.add(message).pad(1).colspan(4);
        tasks.row();
        tasks.add(task1).left().pad(5).colspan(4);
        tasks.row();
        tasks.add(task2).left().pad(5).colspan(4);


        //first (top) row
        table.row();


        //second row (midsection)


        //bottom row
        ImageButton button1 = new ImageButton(skin, "Menu");


        table.setTouchable(Touchable.enabled);
        //row 1
        table.row();
        table.add(button1).size(100,100).left().top();
        table.add().expandX();
        table.add().minWidth(64);
        table.add().minWidth(64);
        table.add().minWidth(64);
        table.add();

        //row2
        table.row();


        //table in second row for tasks
        table.add();

        table.add().expand();

        table.add(tasks).colspan(4);
;
        table.row();
        //third row
        table.add();

        table.add().expandX();

        table.add();
        table.add();
        table.add();
        table.add().size(64,64).right();


        button1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.pauseGame();
            }
        });


        stage1.addActor(table);
        System.out.println("draw");



    }
    public static void renderStage(GameScreen screen){
        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());
        Gdx.input.setInputProcessor(stage1);
        stage1.draw();
    }

}
