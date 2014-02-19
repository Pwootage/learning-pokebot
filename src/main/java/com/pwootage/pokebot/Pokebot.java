/**
 * pokebot  Copyright (C) 2014 Pwootage
 * 
 * pokebot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * pokebot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with pokebot.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pwootage.pokebot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwootage.pokebot.text.TextManager;
import com.pwootage.pokebot.video.GameFrame;
import com.pwootage.pokebot.video.GameTile;
import com.pwootage.pokebot.video.TileRegistry;
import com.pwootage.pokebot.video.text.TextMap;

/**
 * This class represents the main center of Pokebot; the place where everything comes together.
 * 
 * @author Pwootage
 *
 */
public class Pokebot {
	private static final Logger	logger	= LoggerFactory.getLogger(Pokebot.class);
	private PokebotUI			ui;
	private TileRegistry		registry;
	private TextManager			textManager;
	
	public Pokebot(PokebotUI ui) throws IOException {
		this.ui = ui;
		BufferedImage img = ImageIO.read(Pokebot.class.getResource("/com/pwootage/pokebot/res/text-map.png"));
		try (Scanner s = new Scanner(Pokebot.class.getResourceAsStream("/com/pwootage/pokebot/res/text-map.txt"))) {
			registry = new TileRegistry(new TextMap(img, s));
		}
		textManager = new TextManager();
	}
	
	public void processFrame(GameFrame gf) {
		ui.setCurrentFrame(gf);
		GameTile[] tiles = gf.splitToTiles();
		registry.processTiles(tiles);
		ui.updateUniqueTiles(registry.getUniqueTiles());
		String text = textManager.findText(tiles, registry, GameFrame.TILES_WIDE);
		if (text.length() > 0) {
			logger.info("FOUND TEXT! {}", text);
		}
	}
}
