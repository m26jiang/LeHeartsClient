package game;

import java.util.Set;
import java.util.TreeSet;

public class Hand {

	private Set<Card> cards;
	
	public Hand() {
		cards = new TreeSet<Card>();
	}
	
	public void insertCard(Card card) {
		cards.add(card);
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public Set<Card> getCards() {
		return this.cards;
	}
}
