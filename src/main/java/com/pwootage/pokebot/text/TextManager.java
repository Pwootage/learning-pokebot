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
package com.pwootage.pokebot.text;

import com.pwootage.pokebot.video.GameTile;
import com.pwootage.pokebot.video.TileRegistry;

/**
 * Is able to parse text from a frame, given the correct inputs.
 * Potentially also the place which keeps track of what text is seen where?
 * 
 * @author Pwootage
 *
 */
public class TextManager {
	
	public String findText(GameTile[] tiles, TileRegistry registry, int lineWidth) {
		String result = "";
		int lines = tiles.length / lineWidth;
		for (int line = 0; line < lines; line++) {
			String lineStr = "";
			for (int i = 0; i < lineWidth; i++) {
				String tileVal = registry.textLookup(tiles[i + line * lineWidth]);
				if (tileVal != null) {
					lineStr += tileVal;
				}
			}
			if (!lineStr.trim().equals("")) {
				result += lineStr + "\n";
			}
		}
		return result;
	}
	
}
