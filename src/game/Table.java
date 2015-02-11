package game;

import java.util.Observable;

public class Table extends Observable {
	private Card [] cards;
	public Player [] players;
	
	public Table() {
		cards = new Card[4];
		players = new Player[4];
		players[0] = new HumanPlayer();
		players[1] = new NetworkPlayer();
		players[2] = new NetworkPlayer();
		players[3] = new NetworkPlayer();
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public void playCard(int playerId, Card card) {
		players[playerId].playCard(card);
		cards[playerId] = card;
		// TODO: Some HTTP/Socket request will be made here to the server
		this.notifyObs();
	}
	
	public void dealCard(Card card) {
		players[0].addCard(card);
		this.notifyObs();
	}
	
	public void setPlayerTurn(int playerId, boolean isPlayerTurn) {
		players[playerId-1].setPlayerTurn(isPlayerTurn);
	}
	
	public boolean isPlayerTurn(int playerId) {
		return players[playerId].isPlayerTurn();
	}
}