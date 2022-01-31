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
import com.badlogic.gdx.utils.Scaling;

public class HUD {

    public Label score;
    public Stage stage1;

    private Label message;
    private final Label loot;

    private final CheckBox collegesTask;
    private final CheckBox movementTask;
    private final CheckBox pointsTask;

    private final int DISTANCE_GOAL = 600;
    private final int POINT_GOAL = 150;

    public HUD(GameScreen screen){
        //initialise the stage
        System.out.println("rendering");
        stage1 = new Stage(screen.viewport);
        Gdx.input.setInputProcessor(stage1);

        //create main screen table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 0);
   //     table.setDebug(true);

        //create skin atlas1
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        //create tasks table
        Table tasks = new Table();

        tasks.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("transparent.png"))));
    //    tasks.debug();
        message = new Label(screen.playerName+"'s Tasks:", skin);
        message.setFontScale(0.5f, 0.5f);
        collegesTask = new CheckBox("Destroy all colleges 0/"+(screen.colleges.size-1), skin);
        movementTask = new CheckBox("Move "+DISTANCE_GOAL+"m 0/"+DISTANCE_GOAL, skin);
        pointsTask = new CheckBox("Get"+POINT_GOAL+"points 0/"+POINT_GOAL, skin);
        collegesTask.setChecked(true);
        movementTask.setChecked(true);
        pointsTask.setChecked(true);

        Texture coin = new Texture(Gdx.files.internal("loot.png"));
        Texture star = new Texture(Gdx.files.internal("points.png"));
        Image coinI = new Image(coin);
        Image starI = new Image(star);
        coinI.setScaling(Scaling.fit);
        starI.setScaling(Scaling.fit);
        loot = new Label(screen.loot.GetString(), skin);
        score = new Label(screen.points.GetString(), skin);

        tasks.add(starI);
        score.setFontScale(1.2f);
        tasks.add(score).pad(5);
        tasks.add(coinI);
        loot.setFontScale(1.2f);
        tasks.add(loot);
        tasks.row();
        tasks.add(message).pad(1).colspan(4);
        tasks.row();
        tasks.add(collegesTask).left().pad(5).colspan(4);
        tasks.row();
        tasks.add(movementTask).left().pad(5).colspan(4);
        tasks.row();
        tasks.add(pointsTask).left().pad(5).colspan(4);

        //first (top) row
        table.row();

        //second row (midsection)

        //bottom row
        ImageButton button1 = new ImageButton(skin, "Menu");

        table.setTouchable(Touchable.enabled);
        //row 1
        table.row();
        table.add(button1).size(150).left().top().pad(25);
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

    public void renderStage(GameScreen screen){
        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());

        if(screen.collegesCaptured >= screen.colleges.size-1){
            collegesTask.setText("Return home to win.");
        } else {
            collegesTask.setText("Capture all colleges:  "+Math.min(screen.collegesCaptured, screen.colleges.size-1)+"/"+(screen.colleges.size-1)+"  ");
        }

        movementTask.setChecked(screen.player.distance < DISTANCE_GOAL);
        movementTask.setText("Move "+DISTANCE_GOAL+"m:  "+Math.min((int)(screen.player.distance), DISTANCE_GOAL)+"/"+DISTANCE_GOAL+"  ");

        pointsTask.setChecked(screen.points.Get() < POINT_GOAL);
        pointsTask.setText("Get "+POINT_GOAL+" points:  "+Math.min(screen.points.Get(), POINT_GOAL)+"/"+POINT_GOAL+"  ");

        Gdx.input.setInputProcessor(stage1);
        stage1.draw();
    }

    public void updateName(GameScreen screen){
        message.setText(screen.playerName+"'s Tasks:");
    }
}
