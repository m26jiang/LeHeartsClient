package game;

import java.util.Observable;

public class Table extends Observable {
	private Card [] cards;
	public Player [] players;
	private boolean shouldClearTable = false;
	
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
		if (shouldClearTable) {
			clearTable();
		}
		players[playerId].playCard(card);
		cards[playerId] = card;
		this.notifyObs();
	}
	
	public void dealCard(Card card) {
		players[0].addCard(card);
		this.notifyObs();
	}
	
	public void setPlayerTurn(int playerId, boolean isPlayerTurn) {
		players[playerId].setPlayerTurn(isPlayerTurn);
		this.notifyObs();
	}
	
	public boolean isPlayerTurn(int playerId) {
		return players[playerId].isPlayerTurn();
	}
	
	public Card[] getCards() {
		return cards;
	}

	public void playerCollect(int playerId, Card card) {
		players[playerId].collect(card);
	}

	public void roundEnded() {
		shouldClearTable = true;
	}
	
	private void clearTable() {
		cards[0] = null;
		cards[1] = null;
		cards[2] = null;
		cards[3] = null;
		shouldClearTable = false;
	}
}