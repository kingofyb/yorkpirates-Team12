package com.engteam14.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class YorkPirates extends Game {

	public static final int SCREEN_TO_PIXEL_RATIO = 16; // Determines the pixel ratio of the game.

	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	Array<Array<Boolean>> edges;

	/**
	 *	Initialises base game class.
	 */
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16*SCREEN_TO_PIXEL_RATIO, 9*SCREEN_TO_PIXEL_RATIO);
		batch = new SpriteBatch();
		font = new BitmapFont();

		edges = new Array<>();
		String data = Gdx.files.internal("edges.csv").readString();
		for(String row: data.split("\n")){
			Array<Boolean> newrow = new Array<>();
			for(String num: row.split(",")){
				if(num.equals("-1")) newrow.add(true);
				else newrow.add(false);
			}
			edges.insert(0, newrow);
		}
		System.out.println(edges.get(0).get(3));

		setScreen(new TitleScreen(this));
	}

	/**
	 * 	Disposes of data when game finishes execution.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}