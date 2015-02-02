package game;

public class HumanPlayer extends Player {
	private Hand hand;
	
	public HumanPlayer() {
		hand = new Hand();
	}

	@Override
	public void playCard(Card card) {
		this.hand.removeCard(card);
	}

	@Override
	public void addCard(Card card) {
		hand.insertCard(card);
	}
	
	@Override
	public Hand getHand() {
		return this.hand;
	}
}
