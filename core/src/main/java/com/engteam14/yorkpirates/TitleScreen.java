package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class TitleScreen extends ScreenAdapter {
    private final YorkPirates game;
    private final Stage stage;
    private final TextField nameText;
    private final Cell<Image> titleCell;
    private final Animation<TextureRegion> anim;

    private StretchViewport viewport;
    private float elapsedTime = 0f;
    private final float logoScale = 0.33f;
    private String playerName;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public TitleScreen(YorkPirates game){
        this.game = game;

        stage = new Stage();

        tiledMap = new TmxMapLoader().load("pirate12.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        anim = game.logo;
        TextureRegion titleT = anim.getKeyFrame(0f);
        Image title = new Image(titleT);
        title.scaleBy(1/4);

        Table table = new Table();
        table.setFillParent(true);
        //table.debug();

        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        Gdx.input.setInputProcessor(stage);
        skin.addRegions(atlas);

        table.add().expandX();
        titleCell = table.add(title);
        titleCell.fill().prefHeight(game.camera.viewportHeight+40).prefWidth(game.camera.viewportWidth).colspan(5).expand();

        table.add().expandX();
        //new row
        table.row();
        table.add().expand();
        table.add().expand();
        nameText = new TextField("Name (optional)", skin, "edges");
        nameText.setAlignment(Align.center);
        nameText.setOnlyFontChars(true);
        nameText.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                nameText.setText("");
            }});
        table.add().expand();

        table.add(nameText).fillX().pad(80).padBottom(-30);
        table.add().expand();

        table.add().expandY();
        table.add().expand();
        //
        table.row();
        table.add();
        table.add();

        table.add();
        ImageTextButton startButton = new ImageTextButton("Play", skin);
        table.add(startButton);
        table.add();
        table.add();
        table.add();

        table.row();

        table.add().expand();
        table.add().expand();

        table.add().expand();
        ImageTextButton quitButton = new ImageTextButton("Exit Game", skin, "Quit");
        table.add(quitButton);
        table.add().expand();
        table.add().expand();
        table.add().expand();

        table.row();

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                newGame();
            }
        });

        quitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                quitGame();
            }
        });

        table.setBackground(skin.getDrawable("Selection"));
        stage.addActor(table);
    }

    //loading.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("90.png"))); for animation?
    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        elapsedTime += delta;
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0f, 0f, 0f, 1.0f);
        tiledMapRenderer.setView(game.camera); // Draw map first so behind everything
        tiledMapRenderer.render();
        TextureRegion frame = anim.getKeyFrame(elapsedTime, true);
        titleCell.setActor(new Image(frame));
        stage.draw();
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            newGame();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            quitGame();
        }
    }

    private void newGame(){
        if ( nameText.getText().equals("Name (optional)") || nameText.getText().equals("")) {
            playerName = "Player";

        } else{
            playerName = nameText.getText();
        }
        game.setScreen(new GameScreen(game, playerName));
    }


    private void quitGame() {
        game.closeGame();
    }
}
