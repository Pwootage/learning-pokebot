package com.pwootage.pokebot.video;

import java.util.Arrays;

public class GameTile {
	public static final int		WIDTH	= 1;
	public static final int		HEIGHT	= 1;
	private int[]				tileData;
	private transient boolean	cachedHash;
	private transient int		hash;
	
	public GameTile(int[] tileData) {
		if (tileData.length > 64) {
			throw new IllegalArgumentException("Expecting 64 pixels (8x8)");
		}
		this.tileData = tileData;
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
	
	public int getPixel(int i) {
		return tileData[i];
	}
	
}
