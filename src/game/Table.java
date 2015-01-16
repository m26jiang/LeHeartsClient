package game;

import java.util.Observable;

public class Table extends Observable {
	private Card [] cards;
	
	public Table() {
		cards = new Card[4];
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public void placeCard(int playerId, Card card) {
		cards[playerId] = card;
		this.notifyObs();
	}
}
