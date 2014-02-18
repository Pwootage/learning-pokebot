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

import com.pwootage.pokebot.video.GameFrame;

/**
 * This class represents the main center of Pokebot; the place where everything comes together.
 * 
 * @author Pwootage
 *
 */
public class Pokebot {
	private PokebotUI	ui;
	
	public Pokebot(PokebotUI ui) {
		this.ui = ui;
	}
	
	public void processFrame(GameFrame gf) {
		ui.setCurrentFrame(gf);
	}
	
}
