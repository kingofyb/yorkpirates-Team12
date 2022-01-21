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

    Texture map;

    float elapsedTime = 0;
    Vector3 followPos;
    boolean followPlayer = false;

    public GameScreen(YorkPirates game){
        this.game = game;
        followPos = game.camera.position;
        Array<Texture> sprites = new Array<Texture>();
        sprites.add(new Texture("boat1.png"), new Texture("boat2.png"));
        player = new Player(sprites, 2, (int)(game.camera.viewportWidth/2), (int)(game.camera.viewportHeight/2));
        map = new Texture("testback.png");
    }

    @Override
    public void render(float delta){
        elapsedTime += delta;
        update();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);
        game.batch.begin();
        game.batch.draw(map,0,0);
        player.draw(game.batch, elapsedTime);
        game.batch.end();
    }

    private void update(){
        player.update(this, game.camera);
        if(followPlayer) followPos = new Vector3(player.x + player.camdiff.x, player.y + player.camdiff.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 10f || Math.abs(game.camera.position.y - followPos.y) > 10f){
            game.camera.position.slerp(followPos, 0.013f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            game.setScreen(new TitleScreen(game));
        }
    }


    @Override
    public void dispose(){

    }
}
