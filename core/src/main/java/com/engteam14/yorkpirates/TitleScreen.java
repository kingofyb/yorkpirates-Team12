package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class TitleScreen extends ScreenAdapter {
    private final YorkPirates game;
    private final Stage stage;

    private StretchViewport viewport;
    private static final float logoScale = 0.33f;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public TitleScreen(YorkPirates game){
        this.game = game;

        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));

        skin.addRegions(atlas);

        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("", skin);

        table.add(nameLabel);
        table.add(nameText).width(100);
        Gdx.input.setInputProcessor(stage);

        table.row();
        stage.addActor(table);
    }

    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        stage.draw();
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setScreen(new GameScreen(game));
        }
    }
}
