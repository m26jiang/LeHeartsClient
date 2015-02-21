package game;

import transport.LeHeartsHTTPClient;

public class GameController {
	private Table table;
	private int playerId;
	private LeHeartsHTTPClient client;
	private Card cardPending;
	
	private final int NUM_PLAYERS = 4;
	
	public GameController(Table table) {
		this.table = table;
	}
	
	public void setPlayerId(int serverId) {
		int playerId = serverIdToClientId(serverId);
		this.playerId = playerId;
	}
	
	public void playCard(int serverId, Card card) {
		int playerId = serverIdToClientId(serverId);
		table.playCard(playerId, card);
	}
	
	public boolean playerCard(Card card) {
		if (cardPending != null) return false;
		client.setBuffer("MOVE " + card.toString());
		cardPending = null;
		return true;
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
		return ((serverPlayerId + playerId) % NUM_PLAYERS);
	}

	public void setHttpClient(LeHeartsHTTPClient client) {
		this.client = client;
	}
}