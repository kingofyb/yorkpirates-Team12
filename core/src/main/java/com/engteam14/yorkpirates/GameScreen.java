package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    public static YorkPirates game;
    public Player player;

    public static FitViewport viewp;
    public static ScoreManager points;
    public static ScoreManager loot;

    public Array<College> colleges;
    public Array<Projectile> projectiles;

    private final SpriteBatch HUDBatch;
    private final OrthographicCamera HUDCam;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    public Music instrumental;

    private static float elapsedTime = 0;
    private Vector3 followPos;
    public boolean followPlayer = false;

    public static final String playerTeam = "PLAYER";
    public static final String enemyTeam = "ENEMY";
    public TiledMap tiledMap;

    private final HUD gameHUD;

    /**
     * Initialises the main game screen, as well as relevant entities and data.
     * @param game  Passes in the base game class for reference.
     */
    public GameScreen(YorkPirates game){
        GameScreen.game = game;
        followPos = game.camera.position;

        // Initialise HUD
        HUDBatch = new SpriteBatch();
        HUDCam = new OrthographicCamera();
        HUDCam.setToOrtho(false, game.camera.viewportWidth, game.camera.viewportHeight);
        viewp = new FitViewport( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HUDCam); // change this to your needed viewport

        //initialise sound
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("Pirate1_Theme1.ogg"));
        instrumental.setLooping(true);
        instrumental.setVolume(0.8f);
        instrumental.play();

        // Initialise points and loot managers
        points = new ScoreManager();
        loot = new ScoreManager();

        // Initialise projectiles array to be used storing live projectiles
        projectiles = new Array<>();

        // Initialise sprites array to be used generating GameObjects
        Array<Texture> sprites = new Array<>();

        // Initialise player
        sprites.add(new Texture("ship (4).png"), new Texture("ship (4).png"));
        player = new Player(sprites, 2, game.camera.viewportWidth/2, game.camera.viewportHeight/2, 32, 16, playerTeam);
        sprites.clear();

        // Initialise map texture
        tiledMap = new TmxMapLoader().load("Pirate12.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Initialise colleges
        sprites.add(new Texture("tempCollege.png"));
        colleges = new Array<>();
        colleges.add(new College(sprites, 0, player.x+100f, player.y,20f, 40f, "testZero",enemyTeam,player));
        colleges.add(new College(sprites, 0, player.x+50f, player.y-50f,20f, 40f, "testOne",enemyTeam,player));
        colleges.add(new College(sprites, 0, player.x+100f, player.y+250f,20f, 40f, "testTwo",enemyTeam,player));
        colleges.add(new College(sprites, 0, player.x+50f, player.y+50f,20f, 40f, "testThree",enemyTeam,player));
        colleges.add(new College(sprites, 0, player.x-100f, player.y,20f, 40f, "Home",playerTeam,player));
        sprites.clear();

        gameHUD = new HUD(this);
    }

    /**
     * Is called once every frame. Runs update(), renders the game and then the HUD.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        elapsedTime += delta;
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);

        game.batch.begin(); // Begin drawing batch
        tiledMapRenderer.setView(game.camera); // Draw map first so behind everything
        tiledMapRenderer.render();
        for(int i = 0; i < projectiles.size; i++) {
            projectiles.get(i).draw(game.batch, 0);
        }
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).draw(game.batch, 0);
        }
        player.draw(game.batch, elapsedTime); // Player is last entity, all else drawn before them

        game.font.getData().setScale(0.5f);
        int tx = (int)(player.x/16f);
        int ty = (int)(player.y/16f);
        game.font.draw(game.batch, new Vector2(tx,ty).toString(), player.x+player.width/2, player.y+player.height/2);
        game.font.getData().setScale(1f);
        game.batch.end(); // End drawing batch

        HUDCam.update();
        HUDBatch.setProjectionMatrix(HUDCam.combined);

        HUDBatch.begin(); // Start drawing HUD
        game.font.draw(HUDBatch, points.GetString(), 0+HUDCam.viewportHeight*0.03f , HUDCam.viewportHeight*0.98f);
        game.font.draw(HUDBatch, loot.GetString(), HUDCam.viewportWidth*0.98f, HUDCam.viewportHeight*0.98f, 1f, Align.right, true);
        gameHUD.renderStage();
        HUDBatch.end();
        HUDCam.update();
        // End drawing HUD
    }

    /**
     * Is called once every frame. Used for game calculations that take place before rendering.
     */
    private void update(){
        // Call updates for every individual object
        player.update(this, game.camera);
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).update(this, game.camera);
        }

        // Check for projectile creation, then call projectile update
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Vector3 mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            Vector3 mousePos = game.camera.unproject(mouseVector);

            Array<Texture> sprites = new Array<>();
            sprites.add(new Texture("tempProjectile.png"));
            projectiles.add(new Projectile(sprites, 0, player, mousePos.x, mousePos.y, playerTeam));
        }
        for(int i = projectiles.size - 1; i >= 0; i--) {
            projectiles.get(i).update(this, game.camera);
        }

        // Camera calculations based on player movement
        if(followPlayer) followPos = new Vector3(player.x, player.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 1f || Math.abs(game.camera.position.y - followPos.y) > 1f){
            game.camera.position.slerp(followPos, 0.1f);
        }

        // Pause Game
        if(Gdx.input.isKeyJustPressed(Input.Keys.DEL)){
            pauseGame();
        }

        // Temporary shortcut to End Screen
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    /**
     * Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){
        HUDBatch.dispose();
        tiledMap.dispose();
        instrumental.dispose();
    }

    public void pauseGame(){
        game.setScreen(new PauseScreen(game,this));
    }

    public void gameEnd(boolean win){
        game.setScreen(new EndScreen(game, elapsedTime, points, loot, win));
    }
}
