package game;

public class Card implements Comparable<Card> {
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
		case H:
			return rank.getScore();
		case S:
			if(rank == Rank.QUEEN) {
				return -100;
			}
			break;
		case D:
			if(rank == Rank.JACK) {
				return 100;
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return rank.toString() + " : " + suit.toString();
	}

	@Override
	public int compareTo(Card o) {
	    int suitComp = suit.compareTo(o.suit);
		return (suitComp == 0) ? rank.compareTo(o.rank) : suitComp;
	}

	/** Generated automatically by eclipse */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	/** Generated automatically by eclipse */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	
	
}