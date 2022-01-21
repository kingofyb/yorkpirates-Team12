package com.engteam14.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class YorkPirates extends Game {
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;

	int screenToPixelRatio = 15;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16*screenToPixelRatio, 9*screenToPixelRatio);
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}