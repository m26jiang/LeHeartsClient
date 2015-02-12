package game;

public class GameController {
	private Table table;
	private int playerId;
	
	private final int NUM_PLAYERS = 4;
	
	public GameController(Table table) {
		this.table = table;
	}
	
	public void setPlayerId(int id) {
		this.playerId = id;
	}
	
	public void playCard(int serverId, Card card) {
		int playerId = serverIdToClientId(serverId);
		table.playCard(playerId, card);
	}
	
	public void playersTurn() {
		table.setPlayerTurn(playerId, true);
	}
	
	public void playersTurn(int serverId) {
		int playerId = serverIdToClientId(serverId);
		table.setPlayerTurn(playerId, true);
	}
	
	public void endTurn() {
		table.setPlayerTurn(playerId, false);
	}
	
	public void endTurn(int serverId) {
		int playerId = serverIdToClientId(serverId);
		table.setPlayerTurn(playerId, false);
	}

	public void dealCard(Card card) {
		table.dealCard(card);
	}
	
	private int serverIdToClientId(int serverPlayerId) {
		return ((serverPlayerId + playerId) % NUM_PLAYERS)+1;
	}
}