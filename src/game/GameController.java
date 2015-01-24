package game;

public class GameController {
	private Table table;
	
	public GameController(Table table) {
		this.table = table;
	}
	
	public void playerMove(int playerId, Card card) {
		table.players[playerId].playCard(card);
		table.placeCard(playerId, card);
	}
}