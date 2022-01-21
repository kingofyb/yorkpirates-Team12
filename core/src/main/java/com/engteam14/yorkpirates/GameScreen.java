package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    YorkPirates game;
    Player player;
    GameObject testCollider;
    Array<College> colleges;

    Texture map;

    float elapsedTime = 0;
    Vector3 followPos;
    boolean followPlayer = false;

    public GameScreen(YorkPirates game){
        this.game = game;
        followPos = game.camera.position;

        Array<Texture> sprites = new Array<>();
        sprites.add(new Texture("boat1.png"), new Texture("boat2.png"));
        player = new Player(sprites, 2, game.camera.viewportWidth/2, game.camera.viewportHeight/2);
        map = new Texture("testback.png");
        sprites.clear();

        sprites.add(new Texture("tempCollege.png"));
        colleges = new Array<>(5);
        colleges.add(new College(sprites, 0, player.x+100f, player.y,20f, 40f, "testZero"));
        colleges.add(new College(sprites, 0, player.x+50f, player.y-50f,20f, 40f, "testOne"));
        colleges.add(new College(sprites, 0, player.x+100f, player.y+50f,20f, 40f, "testTwo"));
        colleges.add(new College(sprites, 0, player.x+50f, player.y+50f,20f, 40f, "testThree"));
        colleges.add(new College(sprites, 0, player.x-100f, player.y,20f, 40f, "testFour"));
        sprites.clear();

        sprites.add(new Texture("collider.png"));
        testCollider = new GameObject(sprites, 0, player.x+20f, player.y+30f, 40f, 20f);
    }

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
        player.draw(game.batch, elapsedTime); // Draw player last so over everything
        game.batch.end();
        // End drawing batch
    }

    private void update(){
        player.update(this, game.camera);
        testCollider.update(this, game.camera);
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).update(this, game.camera);
        }
        if(followPlayer) followPos = new Vector3(player.x + player.camdiff.x, player.y + player.camdiff.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 10f || Math.abs(game.camera.position.y - followPos.y) > 10f){
            game.camera.position.slerp(followPos, 0.013f);
        }
    }

    @Override
    public void dispose(){

    }
}
