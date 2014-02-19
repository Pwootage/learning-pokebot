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

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwootage.lwjgl.utils.geometry.GeoLib;
import com.pwootage.lwjgl.utils.math.Vector2;
import com.pwootage.lwjgl.utils.textures.Texture;
import com.pwootage.pokebot.video.GameFrame;
import com.pwootage.pokebot.video.GameTile;

/**
 * @author Pwootage
 *
 */
public class PokebotUI extends JFrame {
	private static final Logger	logger		= LoggerFactory.getLogger(PokebotUI.class);
	
	private Canvas				canvas;
	private boolean				running;
	private GameFrame			gf;
	private ArrayList<GameTile>	uniqueTiles	= new ArrayList<>();
	private AtomicBoolean		newFrame	= new AtomicBoolean(false);
	private Texture				frameTex;
	private Texture				tilesTex;
	
	public PokebotUI() {
		setSize(160 * 2, 144 * 2);
		canvas = new Canvas();
		add(canvas, "Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init() throws LWJGLException {
		Display.setParent(canvas);
		PixelFormat pf = new PixelFormat().withStencilBits(8);
		Display.create(pf);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
	}
	
	public void setCurrentFrame(GameFrame gf) {
		this.gf = gf;
		newFrame.set(true);
	}
	
	public void mainRenderLoop() {
		try {
			init();
			running = true;
			while (running) {
				if (!Display.isCurrent()) {
					Display.makeCurrent();
				}
				render();
				Display.sync(60);
				Display.update();
				Display.processMessages();
			}
		} catch (LWJGLException e) {
			logger.error("Exception in render loop", e);
			running = false;
			System.exit(1);
		}
	}
	
	private void render() {
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GLU.gluOrtho2D(0, 1, 0, 1);
		
		glMatrixMode(GL11.GL_MODELVIEW);
		glLoadIdentity();
		updateTextureIfRequired();
		if (frameTex != null) {
			drawFrame();
		}
		if (tilesTex != null) {
			drawTilesTex();
		}
	}
	
	private void drawFrame() {
		frameTex.glBindTexture();
		Vector2 blPos = new Vector2(0f, 0.0f);
		Vector2 blTex = new Vector2(0, 1);
		Vector2 trPos = new Vector2(0.5f, 1.0f);
		Vector2 trTex = new Vector2(1, 0);
		glBegin(GL_QUADS);
		glColor4f(1, 1, 1, 1);
		GeoLib.drawQuad(blPos, trPos, blTex, trTex);
		glEnd();
	}
	
	private void drawTilesTex() {
		tilesTex.glBindTexture();
		Vector2 blPos = new Vector2(0.5f, 0.0f);
		Vector2 blTex = new Vector2(0, 1);
		Vector2 trPos = new Vector2(1f, 1f);
		Vector2 trTex = new Vector2(1, 0);
		glBegin(GL_QUADS);
		glColor4f(1, 1, 1, 1);
		GeoLib.drawQuad(blPos, trPos, blTex, trTex);
		glEnd();
	}
	
	private void updateTextureIfRequired() {
		if (newFrame.get()) {
			if (frameTex == null) {
				frameTex = new Texture(gf.getPixelArray(), GameFrame.WIDTH, GameFrame.HEIGHT);
			} else {
				frameTex.update(gf.getPixelArray(), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
			}
			int w = 256;
			int h = 256;
			int tilesW = w / GameTile.WIDTH;
			int tilesH = h / GameTile.HEIGHT;
			int totalTiles = tilesW * tilesH;
			if (tilesTex == null) {
				tilesTex = new Texture(new int[w * h], w, h);
			}
			int ind = 0;
			for (GameTile t : uniqueTiles) {
				if (ind >= totalTiles) {
					break;
				}
				int updTileX = ind % tilesW;
				int updTileY = ind / tilesH;
				int updX = updTileX * GameTile.WIDTH;
				int updY = updTileY * GameTile.HEIGHT;
				tilesTex.update(t.getPixels(), updX, updY, GameTile.WIDTH, GameTile.HEIGHT);
				ind++;
			}
			newFrame.set(false);
		}
	}
	
	public void updateUniqueTiles(Set<GameTile> uniqueTiles) {
		this.uniqueTiles = new ArrayList<>(uniqueTiles);
	}
}
