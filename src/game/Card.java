package game;

public class Card {
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public int getValue() {
		return rank.getValue();
	}
	
	@SuppressWarnings("incomplete-switch")
	public int getScore() {
		switch(suit) {
		case HEART:
			return rank.getScore();
		case SPADE:
			if(rank == Rank.QUEEN) {
				return -100;
			}
			break;
		case DIAMOND:
			if(rank == Rank.JACK) {
				return 100;
			}
		}
		return 0;
	}
}