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
package com.pwootage.pokebot.video;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.pwootage.pokebot.video.text.TextChar;
import com.pwootage.pokebot.video.text.TextMap;

/**
 * This class maintains a registry of all known unique tiles.
 * 
 * @author Pwootage
 *
 */
public class TileRegistry {
	private Set<GameTile>				tiles			= new LinkedHashSet<>();
	private HashMap<GameTile, String>	tileToTextMap	= new HashMap<>();
	private TextMap						textMap;
	
	public TileRegistry(TextMap textMap) {
		this.textMap = textMap;
	}
	
	public void processTiles(GameTile[] tiles) {
		for (GameTile tile : tiles) {
			if (!this.tiles.contains(tile)) {
				processNewTile(tile);
			}
		}
	}
	
	private void processNewTile(GameTile tile) {
		// Need to check for moving tiles, somehow?
		tiles.add(tile);
		TextChar c = textMap.findMatch(tile);
		if (c != null) {
			tileToTextMap.put(tile, c.getValue());
		}
	}
	
	public Set<GameTile> getUniqueTiles() {
		return tiles;
	}
	
	public String textLookup(GameTile gameTile) {
		return tileToTextMap.get(gameTile);
	}
}
