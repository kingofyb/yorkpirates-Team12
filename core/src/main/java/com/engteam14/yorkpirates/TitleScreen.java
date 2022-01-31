package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleScreen extends ScreenAdapter {
    private final YorkPirates game;
    private final GameScreen nextGame;
    private final Stage stage;
    private final TextField nameText;
    private final Cell<Image> titleCell;
    private final Animation<TextureRegion> anim;

    private float elapsedTime = 0f;

    /**
     * Initialises the title screen, as well as relevant textures and data it may contain.
     * @param game  Passes in the base game class for reference.
     */
    public TitleScreen(YorkPirates game){
        this.game = game;
        nextGame = new GameScreen(game, null);
        nextGame.isPaused = true;
        nextGame.playerName = "Player";

        stage = new Stage();

        TiledMap tiledMap = new TmxMapLoader().load("pirate12.tmx");
        TiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        anim = game.logo;
        TextureRegion titleT = anim.getKeyFrame(0f);
        Image title = new Image(titleT);
        title.scaleBy(0.25f);

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
        //tiledMapRenderer.setView(game.camera); // Draw map first so behind everything
        //tiledMapRenderer.render();
        nextGame.render(delta);
        TextureRegion frame = anim.getKeyFrame(elapsedTime, true);
        titleCell.setActor(new Image(frame));
        stage.draw();
    }

    /**
     * Is called once every frame to check for player input.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            newGame();
        }
    }

    /**
     * Is called to create a new game screen.
     */
    private void newGame(){
        String playerName;
        if ( nameText.getText().equals("Name (optional)") || nameText.getText().equals("")) {
            playerName = "Player";

        } else{
            playerName = nameText.getText();
        }
        nextGame.isPaused = false;
        nextGame.playerName = playerName;
        nextGame.gameHUD.updateName(nextGame);
        game.setScreen(nextGame);
    }

    /**
     * Calls the close game function in the main game class.
     */
    private void quitGame() {
        game.closeGame();
    }
}
