package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class TitleScreen extends ScreenAdapter {
    private final YorkPirates game;
    private final Stage stage;
    private final TextField nameText;
    private final Animation<Texture> anim;

    private StretchViewport viewport;
    private static final float logoScale = 0.33f;
    private String playerName;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public TitleScreen(YorkPirates game){
        this.game = game;

        stage = new Stage();

        Array<Texture> frames = null;
        float fps;
        anim = new Animation<>(0.5f, frames);
        Texture titleT = new Texture("logo/Logo_00000.png");
        Image title = new Image(titleT);
        title.scaleBy(1/4);

        Table table = new Table();
        table.setFillParent(true);
       // table.debug();

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));

        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        Gdx.input.setInputProcessor(stage);
        skin.addRegions(atlas);

        table.add().expandX();
        table.add(title).fill().prefHeight(game.camera.viewportHeight+40).prefWidth(game.camera.viewportWidth).colspan(5).expand();

        table.add().expandX();
        //new row
        table.row();
        table.add().expand();
        table.add().expand();
        nameText = new TextField("Name (optional)", skin);
        nameText.setAlignment(Align.center);
        nameText.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                nameText.setText("");
            }});
        table.add().expand();

        table.add(nameText).fillX().pad(65);
        table.add().expand();

        table.add().expandY();
        table.add().expand();
        //
        table.row();
        table.add();
        table.add();

        table.add();
        table.add(new ImageButton(skin, "Play"));
        table.add();
        table.add();
        table.add();

        table.row();

        table.add().expand();
        table.add().expand();

        table.add().expand();
        table.add(new TextButton("Quit", skin));
        table.add().expand();
        table.add().expand();
        table.add().expand();

        table.row();

        stage.addActor(table);
    }

    //loading.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("90.png"))); for animation?
    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println(nameText.getText());
            if ( nameText.getText() == "Enter Name (optional)" || nameText.getText() == "") {
                playerName = "Player";

            } else{
                playerName = nameText.getText();
            }
            System.out.println(playerName);
            game.setScreen(new GameScreen(game));


        }else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.closeGame(this);
        }
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0f, 0f, 0f, 1.0f);
        stage.draw();
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){

    }
}
