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
		this.playerId = serverId;
	}
	
	/** Method to update model when another player plays a card */
	public void playCard(int serverId, Card card) {
		int playerId = serverIdToClientId(serverId);
		table.playCard(playerId, card);
	}
	
	/** Method for player to attempt to play a card */
	public boolean playCard(Card card) {
		if (cardPending != null) {
			return false;
		}
		cardPending = card;
		client.setBuffer("MOVE " + card.toString());
		return true;
	}
	
	public void playersTurn() {
		table.setPlayerTurn(0, true);
	}
	
	public void endTurn() {
		table.setPlayerTurn(0, false);
		table.playCard(0, cardPending);
		cardPending = null;
	}

	public void dealCard(Card card) {
		table.dealCard(card);
	}
	
	private int serverIdToClientId(int serverPlayerId) {
		return ((serverPlayerId - playerId + NUM_PLAYERS) % NUM_PLAYERS);
	}

	public void setHttpClient(LeHeartsHTTPClient client) {
		this.client = client;
	}

	public void invalidMove() {
		cardPending = null;
	}

	public void playerCollect(int serverId, Card card) {
		int playerId = serverIdToClientId(serverId);
		table.playerCollect(playerId, card);
	}

	public void roundEnded() {
		table.roundEnded();
	}
	
	public void setUserId(int serverId, String userId) {
		int playerId = serverIdToClientId(serverId);
		table.players[playerId].setUserId(userId);
	}

	public void requestName(String name) {
		client.requestName(name);		
	}
}