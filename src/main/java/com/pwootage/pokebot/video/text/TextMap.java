package com.pwootage.pokebot.video.text;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pwootage.pokebot.video.GameTile;

public class TextMap {
	private List<TextChar>	chars	= new ArrayList<TextChar>();
	
	public TextMap(BufferedImage img, Scanner map) {
		int tilesWide = img.getWidth() / GameTile.WIDTH;
		for (int i = 0; map.hasNext(); i++) {
			String charVal = map.nextLine();
			int row = i / tilesWide;
			int col = i % tilesWide;
			TextChar c = new TextChar(img, col * GameTile.WIDTH, row * GameTile.HEIGHT, charVal);
			chars.add(c);
		}
	}
	
	public TextChar findMatch(GameTile t) {
		for (TextChar c : chars) {
			if (c.matches(t)) {
				return c;
			}
		}
		return null;
	}
}
