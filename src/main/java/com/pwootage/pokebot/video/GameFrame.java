package com.pwootage.pokebot.video;

import java.nio.ByteBuffer;

public class GameFrame {
	public static final int	WIDTH	= 160;
	public static final int	HEIGHT	= 144;
	private int[]			rgba;
	
	public GameFrame(byte[] data, int off, int len) {
		ByteBuffer buff = ByteBuffer.wrap(data, off, len);
		rgba = new int[WIDTH * HEIGHT];
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			rgba[i] = buff.getInt();
		}
	}
	
	public int[] getPixelArray() {
		return rgba;
	}
	
	public int getPixel(int x, int y) {
		return rgba[y * WIDTH + x];
	}
}
