package game;

public class Player {
	// TODO: Add more stuff...
	//       What things will the Player class be reponsible for?
	
	private boolean isPlayersTurn;
	private Hand hand;
	
	public Player() {
		hand = new Hand();
		isPlayersTurn = false;
	}
	
	public void setPlayerTurn() {
		isPlayersTurn = true;
	}
	
	public void clearPlayerTurn() {
		isPlayersTurn = false;
	}
}
