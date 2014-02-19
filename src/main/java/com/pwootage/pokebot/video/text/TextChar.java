package com.pwootage.pokebot.video.text;

import java.awt.image.BufferedImage;

import com.pwootage.pokebot.video.GameTile;

public class TextChar {
	public static final int	HALF_GRAY	= (255 + 255 + 255) / 2;
	/**
	 * 0 for light, 1 for dark
	 */
	private byte[]			data;
	private int				blackSquares;
	private String			value;
	
	public TextChar(BufferedImage img, int x, int y, String value) {
		data = new byte[GameTile.WIDTH * GameTile.HEIGHT];
		for (int i = 0; i < GameTile.WIDTH * GameTile.HEIGHT; i++) {
			int row = i / GameTile.WIDTH + y;
			int col = i % GameTile.WIDTH + x;
			// if (col % GameTile.WIDTH == 0) {
			// System.out.println();
			// }
			int rgba = img.getRGB(col, row);
			int b = (rgba >> 0 & 0xFF);
			int g = (rgba >> 8 & 0xFF);
			int r = (rgba >> 16 & 0xFF);
			// int a = (rgba >> 24 & 0xFF);
			int sum = r + g + b;
			if (sum < HALF_GRAY) {
				// System.out.print("■");
				blackSquares++;
				data[i] = 1;
			} else {
				// System.out.print(" ");
				data[i] = 0;
			}
		}
		this.value = value;
	}
	
	public boolean matches(GameTile tile) {
		int matchingSquares = 0;
		String debug = "";
		for (int i = 0; i < GameTile.WIDTH * GameTile.HEIGHT; i++) {
			int rgba = tile.getPixel(i);
			// byte a = (byte) (rgba >> 0 & 0xFF);
			int r = (rgba >> 8 & 0xFF);
			int g = (rgba >> 16 & 0xFF);
			int b = (rgba >> 24 & 0xFF);
			int sum = r + g + b;
			if (i % GameTile.WIDTH == 0) {
				debug += "\n";
			}
			if (sum < HALF_GRAY) {
				if (data[i] == 1) {
					matchingSquares++;
				}
				debug += "■";
			} else {
				if (data[i] == 0) {
					matchingSquares++;
				}
				debug += " ";
			}
		}
		if (matchingSquares >= 64) {
			System.out.println(debug);
			return true;
		} else {
			return false;
		}
	}
	
	public String getValue() {
		return value;
	}
}
