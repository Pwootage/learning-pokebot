package com.pwootage.pokebot.video;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameFrame {
	private static final Logger	logger		= LoggerFactory.getLogger(GameFrame.class);
	public static final int		WIDTH		= 160;
	public static final int		HEIGHT		= 144;
	public static final int		TILES_WIDE	= WIDTH / GameTile.WIDTH;
	public static final int		TILES_HIGH	= HEIGHT / GameTile.HEIGHT;
	private static final int[]	COLORS		= { 0x000000FF, 0x666666FF, 0xAAAAAAFF, 0xFFFFFFFF };
	private int[]				rgba;
	
	public GameFrame(byte[] data, int off, int len) {
		ByteBuffer buff = ByteBuffer.wrap(data, off, len);
		rgba = new int[WIDTH * HEIGHT];
		int colorsFound = 0;
		int[] colors = new int[4];
		outer: for (int i = off; i < WIDTH * HEIGHT + off; i++) {
			rgba[i] = buff.getInt();
			int c = rgba[i];
			for (int j = 0; j < colorsFound; j++) {
				if (colors[j] == c) {
					continue outer;
				}
			}
			if (colorsFound < 4) {
				// insert into array at correct position based on intensity
				int j = 0;
				for (; j < colorsFound && colorIntensityGreater(c, colors[j]); j++)
					;
				colorsFound++;
				for (int k = colorsFound - 1; k > j; k--) {
					colors[k] = colors[k - 1];
				}
				colors[j] = c;
			} else {
				// logger.warn("Got more than 3 colors... Everything else will be white");
			}
		}
		while (colorsFound < 4) {
			// Fill in with some extra colors
			int c = 0x88888888;
			int j = 0;
			for (; j < colorsFound && colorIntensityGreater(c, colors[j]); j++)
				;
			colorsFound++;
			for (int k = colorsFound - 1; k > j; k--) {
				colors[k] = colors[k - 1];
			}
			colors[j] = c;
		}
		// printColors(colors);
		outer: for (int i = 0; i < WIDTH * HEIGHT; i++) {
			int c = rgba[i];
			for (int j = 0; j < 4; j++) {
				if (colors[j] == c) {
					rgba[i] = GameFrame.COLORS[j];
					continue outer;
				}
			}
			rgba[i] = 0xFF00FFFF;
		}
	}
	
	private void printColors(int[] colors) {
		int[] r = new int[4];
		int[] g = new int[4];
		int[] b = new int[4];
		int[] intensity = new int[4];
		for (int i = 0; i < 4; i++) {
			int c = colors[i];
			r[i] = (c >> 8 & 0xFF);
			g[i] = (c >> 16 & 0xFF);
			b[i] = (c >> 24 & 0xFF);
			intensity[i] = r[i] + g[i] + b[i];
		}
		logger.debug("Colors: r: {}, g: {}, b: {}, intensity: {}", Arrays.toString(r), Arrays.toString(g), Arrays.toString(b), Arrays.toString(intensity));
	}
	
	private boolean colorIntensityGreater(int c1, int c2) {
		int r1 = (c1 >> 8 & 0xFF);
		int g1 = (c1 >> 16 & 0xFF);
		int b1 = (c1 >> 24 & 0xFF);
		int sum1 = r1 + g1 + b1;
		int r2 = (c2 >> 8 & 0xFF);
		int g2 = (c2 >> 16 & 0xFF);
		int b2 = (c2 >> 24 & 0xFF);
		int sum2 = r2 + g2 + b2;
		return sum1 > sum2;
	}
	
	public int[] getPixelArray() {
		return rgba;
	}
	
	public int getPixel(int x, int y) {
		return rgba[y * WIDTH + x];
	}
	
	public GameTile[] splitToTiles() {
		GameTile[] tiles = new GameTile[TILES_WIDE * TILES_HIGH];
		for (int x = 0; x < TILES_WIDE; x++) {
			for (int y = 0; y < TILES_HIGH; y++) {
				int ind = y * TILES_WIDE + x;
				tiles[ind] = extractTile(x, y);
			}
		}
		return tiles;
	}
	
	private GameTile extractTile(int x, int y) {
		int[] tileData = new int[GameTile.WIDTH * GameTile.HEIGHT];
		int xOff = x * GameTile.WIDTH;
		int yOff = y * GameTile.HEIGHT;
		for (int xPos = 0; xPos < GameTile.WIDTH; xPos++) {
			for (int yPos = 0; yPos < GameTile.HEIGHT; yPos++) {
				int ind = yPos * GameTile.WIDTH + xPos;
				int indInArray = (yOff + yPos) * GameFrame.WIDTH + xOff + xPos;
				tileData[ind] = rgba[indInArray];
			}
		}
		return new GameTile(tileData);
	}
}
