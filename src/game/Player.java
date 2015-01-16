package game;

import java.util.Observable;
import java.util.Observer;

public class Player extends Observable implements Observer {
	// TODO: Add more stuff...
	//       What things will the Player class be responsible for?
	
	private String userId; // TODO: Used for future features / maybe display
	private int playerId;
	private boolean isPlayersTurn;
	private Hand hand;
	private Table table;
	
	public Player(Table table, Hand...hand) {
		this.table = table;
		if(hand.length == 1) this.hand = hand[0];
		isPlayersTurn = false;
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	public void notifyObs() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setPlayerTurn(boolean isTurn) {
		isPlayersTurn = isTurn;
		notifyObs();
	}
	
	public boolean isPlayerTurn() {
		return this.isPlayersTurn;
	}
	
	public void playCard(Card card) {
		this.hand.removeCard(card);
		this.table.placeCard(this.playerId, card);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
