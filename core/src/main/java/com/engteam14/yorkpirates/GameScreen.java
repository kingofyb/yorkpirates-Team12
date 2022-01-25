package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    public YorkPirates game;
    public Player player;
    public ScoreManager points;
    public ScoreManager loot;
    private final GameObject testCollider;
    public Array<College> colleges;
    public Array<Projectile> projectiles;

    private final SpriteBatch HUDBatch;
    private final OrthographicCamera HUDCam;
    private final Texture map;

    private float elapsedTime = 0;
    private Vector3 followPos;
    public float elapsedTime = 0;
    public boolean followPlayer = false;

    /**
     *  Initialises the main game screen, as well as relevant entities and data.
     * @param game  Passes in the base game class for reference.
     */
    public GameScreen(YorkPirates game){
        this.game = game;
        followPos = game.camera.position;

        // Initialise HUD
        HUDBatch = new SpriteBatch();
        HUDCam = new OrthographicCamera();
        HUDCam.setToOrtho(false, game.camera.viewportWidth, game.camera.viewportHeight);

        // Initialise points and loot managers
        points = new ScoreManager();
        loot = new ScoreManager();

        // Initialise projectiles array to be used storing live projectiles
        projectiles = new Array<>();

        // Initialise sprites array to be used generating GameObjects
        Array<Texture> sprites = new Array<>();

        // Initialise player
        sprites.add(new Texture("boat1.png"), new Texture("boat2.png"));
        player = new Player(sprites, 2, game.camera.viewportWidth/2, game.camera.viewportHeight/2, 34, 16);
        sprites.clear();

        // Initialise map texture
        map = new Texture("testback.png");

        // Initialise colleges
        sprites.add(new Texture("tempCollege.png"));
        colleges = new Array<>();
        colleges.add(new College(sprites, 0, player.x+100f, player.y,20f, 40f, "testZero",false));
        colleges.add(new College(sprites, 0, player.x+50f, player.y-50f,20f, 40f, "testOne",true));
        colleges.add(new College(sprites, 0, player.x+100f, player.y+50f,20f, 40f, "testTwo",true));
        colleges.add(new College(sprites, 0, player.x+50f, player.y+50f,20f, 40f, "testThree",true));
        colleges.add(new College(sprites, 0, player.x-100f, player.y,20f, 40f, "testFour",true));
        sprites.clear();

        // Temporary collide-able GameObject for testing purposes
        sprites.add(new Texture("collider.png"));
        testCollider = new GameObject(sprites, 0, player.x+20f, player.y+30f, 40f, 20f);
    }

    /**
     *  Is called once every frame. Runs update(), renders the game and then the HUD.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        elapsedTime += delta;
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);
        // Begin drawing batch
        game.batch.begin();
        game.batch.draw(map,0,0); // Draw map first so behind everything
        testCollider.draw(game.batch, 0);
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).draw(game.batch, 0);
        }
        for(int i = 0; i < projectiles.size; i++) {
            projectiles.get(i).draw(game.batch, 0);
        }
        player.draw(game.batch, elapsedTime); // Player is last entity, all else drawn before them
        game.batch.end();
        // End drawing batch
        HUDCam.update();
        HUDBatch.setProjectionMatrix(HUDCam.combined);
        // Start drawing HUD
        HUDBatch.begin();
        game.font.draw(HUDBatch, points.GetString(), HUDCam.viewportHeight-HUDCam.viewportHeight*0.98f, HUDCam.viewportHeight*0.98f);
        game.font.draw(HUDBatch, loot.GetString(), HUDCam.viewportWidth-(HUDCam.viewportHeight-HUDCam.viewportHeight*0.98f), HUDCam.viewportHeight*0.98f, 1f, Align.right, true);
        HUDBatch.end();
        // End drawing HUD
    }

    /**
     *  Is called once every frame. Used for game calculations that take place before rendering.
     */
    private void update(){
        // Call update for every individual object
        player.update(this, game.camera);
        testCollider.update(this, game.camera);
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).update(this, game.camera);
        }

        // Check for projectile creation, then call projectile update
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
            Array<Texture> sprites = new Array<>();
            sprites.add(new Texture("tempProjectile.png"));
            Vector3 mousePos = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            projectiles.add(new Projectile(sprites, 0, player.x, player.y, mousePos.x, mousePos.y, game));
        }
        for(int i = projectiles.size - 1; i >= 0; i--) {
            projectiles.get(i).update(this, game.camera);
        }

        // Camera calculations based on player movement
        if(followPlayer) followPos = new Vector3(player.x, player.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 1f || Math.abs(game.camera.position.y - followPos.y) > 1f){
            game.camera.position.slerp(followPos, 0.1f);
        }

        // Temporary shortcut to endscreen
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            game.setScreen(new EndScreen(game, elapsedTime, points, loot, true));
        } else if(Gdx.input.isKeyPressed(Input.Keys.DEL)){
            game.setScreen(new EndScreen(game, elapsedTime, points, loot, false));
        }
    }

    /**
     *  Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){
        HUDBatch.dispose();
        map.dispose();
    }
}
