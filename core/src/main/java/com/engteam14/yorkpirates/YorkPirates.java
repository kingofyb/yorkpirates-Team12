package com.engteam14.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class YorkPirates extends Game {

	public BitmapFont font;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Array<Array<Boolean>> edges;
	public Animation<TextureRegion> logo;

	private static final int screenToPixelRatio = 16; // Determines the pixel ratio of the game.

	/**
	 * Initialises base game class.
	 */
	@Override
	public void create () {
		Gdx.graphics.setVSync(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16*screenToPixelRatio, 9*screenToPixelRatio);
		batch = new SpriteBatch();
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
		skin.addRegions(atlas);
		font = skin.getFont("Raleway-Bold");

		Texture logoSheet = new Texture(Gdx.files.internal("logo.png"));
		TextureRegion[][] split = TextureRegion.split(logoSheet, logoSheet.getWidth() / 8, logoSheet.getHeight() / 10);
		Array<TextureRegion> frames = new Array<>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				frames.add(split[i][j]);
			}
		}
		logo = new Animation<>(0.05f, frames);

		edges = new Array<>();
		String data = Gdx.files.internal("edges.csv").readString();
		for(String row: data.split("\n")){
			Array<Boolean> newRow = new Array<>();
			for(String num: row.split(",")){
				if(num.equals("-1")) newRow.add(true);
				else newRow.add(false);
			}
			edges.insert(0, newRow);
		}
		setScreen(new TitleScreen(this));
	}

	/**
	 * Disposes of data when game finishes execution.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	/**
	 * Closes the application
	 */
	public void closeGame(){
		Gdx.app.exit();
	}
}