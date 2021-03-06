package game;

import java.util.HashMap;
import java.util.Map;

public enum Rank {
	TWO(2, 0),
	THREE(3, 0),
	FOUR(4, 0),
	FIVE(5, -10),
	SIX(6, -10),
	SEVEN(7, -10),
	EIGHT(8, -10),
	NINE(9, -10),
	TEN(10, -10),
	JACK(11, -20),
	QUEEN(12, -30),
	KING(13, -40),
	ACE(14, -50),
	SJOKER(15, -60),
	LJOKER(16, -70);
	
	private static final Map<Integer, Rank> intToTypeMap = new HashMap<Integer, Rank>();
	static {
	    for (Rank type : Rank.values()) {
	        intToTypeMap.put(type.value, type);
	    }
	}

	public static Rank fromInt(int i) {
		return intToTypeMap.get(Integer.valueOf(i));
	}
	
	private int value;
	private int score;
	
	public int getValue() {
		return this.value;
	}
	
	public int getScore() {
		return this.score;
	}
	
	Rank(int value, int score) {
		this.value = value;
		this.score = score;
	}
}