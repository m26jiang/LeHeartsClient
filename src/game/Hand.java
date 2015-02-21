package game;

import java.util.Collections;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

public class Hand extends Observable {

	private Set<Card> cards;
	
	public Hand() {
		cards = Collections.synchronizedSet(new TreeSet<Card>());
	}
	
	public void insertCard(Card card) {
		cards.add(card);
		notifyObs();
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
		notifyObs();
	}
	
	public Set<Card> getCards() {
		return this.cards;
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
}
