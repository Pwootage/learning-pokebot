package com.pwootage.pokebot.video.text;

import java.awt.image.BufferedImage;
import java.util.Scanner;

import com.pwootage.pokebot.video.GameTile;

public class TextMap {
	private GameTile[]	tiles;
	
	public TextMap(BufferedImage img, Scanner map) {
		int tilesWide = img.getWidth() / GameTile.WIDTH;
		for (int i = 0; map.hasNext(); i++) {
			String charVal = map.nextLine();
			int row = i / tilesWide;
			int col = i % tilesWide;
			TextChar c = new TextChar(img, col * GameTile.WIDTH, row * GameTile.HEIGHT, charVal);
		}
	}
}
