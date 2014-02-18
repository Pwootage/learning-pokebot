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
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Canvas;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwootage.lwjgl.utils.textures.Texture;
import com.pwootage.pokebot.video.GameFrame;

/**
 * @author Pwootage
 *
 */
public class PokebotUI extends JFrame {
	private static final Logger	logger		= LoggerFactory.getLogger(PokebotUI.class);
	
	private Canvas				canvas;
	private boolean				running;
	private GameFrame			gf;
	private AtomicBoolean		newFrame	= new AtomicBoolean(false);
	private Texture				frameTex;
	
	public PokebotUI() {
		setSize(160 * 2, 144 * 2);
		canvas = new Canvas();
		add(canvas, "Center");
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
	}
	
	private void drawFrame() {
		frameTex.glBindTexture();
		GL11.glPushMatrix();
		glBegin(GL_QUADS);
		glColor4f(1, 1, 1, 1);
		
		glTexCoord2f(0, 0);
		glVertex2f(0, 1);
		
		glTexCoord2f(1, 0);
		glVertex2f(1, 1);
		
		glTexCoord2f(1, 1);
		glVertex2f(1, 0);
		
		glTexCoord2f(0, 1);
		glVertex2f(0, 0);
		
		glEnd();
		GL11.glPopMatrix();
	}
	
	private void updateTextureIfRequired() {
		if (newFrame.get()) {
			if (frameTex == null) {
				frameTex = new Texture(gf.getPixelArray(), GameFrame.WIDTH, GameFrame.HEIGHT);
			} else {
				frameTex.update(gf.getPixelArray());
			}
			newFrame.set(false);
		}
	}
}
