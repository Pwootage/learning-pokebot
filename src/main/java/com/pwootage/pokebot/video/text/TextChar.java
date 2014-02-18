package com.pwootage.pokebot.video.text;

import java.awt.image.BufferedImage;

import com.pwootage.pokebot.video.GameTile;

public class TextChar {
	private static final int	HALF_GRAY	= (255 + 255 + 255) / 2;
	/**
	 * 0 for light, 1 for dark
	 */
	private byte[]				data;
	private String				value;
	
	public TextChar(BufferedImage img, int x, int y, String value) {
		data = new byte[GameTile.WIDTH * GameTile.HEIGHT];
		for (int i = 0; i < GameTile.WIDTH * GameTile.HEIGHT; i++) {
			int row = i / GameTile.WIDTH + y;
			int col = i % GameTile.WIDTH + x;
			int rgba = img.getRGB(row, col);
			byte b = (byte) (rgba >> 0 & 0xFF);
			byte g = (byte) (rgba >> 8 & 0xFF);
			byte r = (byte) (rgba >> 16 & 0xFF);
			// byte a = (byte) (rgba >> 24 & 0xFF);
			int sum = r + g + b;
			if (sum > HALF_GRAY) {
				data[i] = 1;
			} else {
				data[i] = 0;
			}
		}
		this.value = value;
	}
	
	public boolean matches(GameTile tile) {
		int matchingSquares = 0;
		for (int i = 0; i < GameTile.WIDTH * GameTile.HEIGHT; i++) {
			int rgba = tile.getPixel(i);
			// byte a = (byte) (rgba >> 0 & 0xFF);
			byte r = (byte) (rgba >> 8 & 0xFF);
			byte g = (byte) (rgba >> 16 & 0xFF);
			byte b = (byte) (rgba >> 24 & 0xFF);
			int sum = r + g + b;
			if (sum > HALF_GRAY && data[i] == 1) {
				matchingSquares++;
			} else if (sum < HALF_GRAY && data[i] == 0) {
				matchingSquares++;
			}
		}
		if (matchingSquares >= 64) {
			return true;
		} else {
			return false;
		}
	}
}
