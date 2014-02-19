package com.pwootage.pokebot.video;

import java.util.Arrays;

import com.pwootage.pokebot.video.text.TextChar;

public class GameTile implements Comparable<GameTile> {
	public static final int		WIDTH	= 8;
	public static final int		HEIGHT	= 8;
	private int[]				tileData;
	private transient boolean	cachedHash;
	private transient int		hash;
	
	public GameTile(int[] tileData) {
		if (tileData.length > WIDTH * HEIGHT) {
			throw new IllegalArgumentException("Expecting 64 pixels (8x8)");
		}
		this.tileData = tileData;
	}
	
	public int getPixel(int i) {
		return tileData[i];
	}
	
	public int[] getPixels() {
		return tileData;
	}
	
	@Override
	public int hashCode() {
		if (cachedHash) {
			return hash;
		} else {
			final int prime = 31;
			int result = 1;
			for (int i = 0; i < tileData.length; i++) {
				result = prime * result + tileData[i];
			}
			hash = result;
			cachedHash = true;
			return hash;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GameTile other = (GameTile) obj;
		if (!Arrays.equals(tileData, other.tileData)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		String res = "Hash: " + hashCode() + "\n";
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			int rgba = tileData[i];
			int r = (rgba >> 8 & 0xFF);
			int g = (rgba >> 16 & 0xFF);
			int b = (rgba >> 24 & 0xFF);
			int sum = r + g + b;
			if (i % WIDTH == 0) {
				res += "\n";
			}
			if (sum < TextChar.HALF_GRAY) {
				res += "■";
			} else {
				res += " ";
			}
		}
		return res;
	}
	
	@Override
	public int compareTo(GameTile o) {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			if (tileData[i] == o.tileData[i]) {
				continue;
			} else if (tileData[i] < o.tileData[i]) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}
	
}
