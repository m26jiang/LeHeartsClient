package game;

import java.util.Observable;

public class Table extends Observable {
	private Card [] cards;
	public Player player;
	
	private int playerId;
	
	public Table() {
		cards = new Card[4];
		player = new Player(new Hand(), new Hand());
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public void playCard(int playerId, Card card) {
		if (this.playerId == playerId) {
			player.playCard(card);
		}
		cards[playerId] = card;
		// TODO: Some HTTP/Socket request will be made here to the server
		this.notifyObs();
	}
	
	public void dealCard(int playerId, Card card) {
		
	}
}