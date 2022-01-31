package com.engteam14.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class YorkPirates extends Game {

	public BitmapFont font;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Array<Array<Boolean>> edges;
	public Animation<TextureRegion> logo;
	public Animation<TextureRegion> mouse;
	public Animation<TextureRegion> keyboard;

	private static final int screenToPixelRatio = 16; // Determines the pixel ratio of the game.

	/**
	 * Initialises base game class.
	 */
	@Override
	public void create () {
		Gdx.graphics.setForegroundFPS(30);
		Gdx.graphics.setVSync(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16*screenToPixelRatio, 9*screenToPixelRatio);
		batch = new SpriteBatch();
		TextureAtlas atlas;
		atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
		skin.addRegions(atlas);
		font = skin.getFont("Raleway-Bold");

		logo = getAnimator("logo.png", 10,8);
		mouse = getAnimator("mouse.png",5, 5);
		keyboard = getAnimator("keyboard.png",1, 16);

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

	private Animation<TextureRegion> getAnimator(String path, int x, int y) {
		Texture logosheet = new Texture(Gdx.files.internal(path));
		TextureRegion[][] split = TextureRegion.split(logosheet, logosheet.getWidth() / y, logosheet.getHeight() / x);
		Array<TextureRegion> frames = new Array<>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				frames.add(split[i][j]);
			}
		}
		return new Animation<TextureRegion>(0.05f, frames);
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