package game;

import java.util.Observable;

public class Table extends Observable {
	private Card [] cards;
	public Player [] players;
	
	public Table() {
		cards = new Card[4];
		players = new Player[4];
		Player player1 = new Player(this, new Hand());
		Player player2 = new Player(this, new Hand());
		Player player3 = new Player(this, new Hand());
		Player player4 = new Player(this, new Hand());
		players[0] = player1;
		players[1] = player2;
		players[2] = player3;
		players[3] = player4;
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public void placeCard(int playerId, Card card) {
		cards[playerId] = card;
		// TODO: Some HTTP/Socket request will be made here to the server
		this.notifyObs();
	}
}
