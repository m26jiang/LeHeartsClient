package game;

import java.util.Observable;
import java.util.Observer;

public abstract class Player extends Observable implements Observer {
	// TODO: Add more stuff...
	//       What things will the Player class be responsible for?
	
	private String userId; // TODO: Used for future features / maybe display
	private boolean isPlayersTurn;
	private Hand collect;
	
	public Player() {
		this.collect = new Hand();
		isPlayersTurn = false;
	}
	
	public abstract void playCard(Card card);
	public abstract void addCard(Card card);
	public abstract Hand getHand();

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Hand getCollect() {
		return this.collect;
	}
}
