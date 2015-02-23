package transport;

import game.Card;
import game.GameController;
import game.Rank;
import game.Suit;

public class CommandParser {
	private GameController game;

	public CommandParser(GameController game) {
		this.game = game;
	}
	
	public boolean parseCommand(String command) {
		String [] ops = command.split(" ", 2);
		System.out.println(command);
		String op = ops[0];
		if (op.equals("WELCOME")) {
			int playerId = Integer.parseInt(ops[1]);
			game.setPlayerId(playerId);
		} else if (op.equals("YOUR_TURN")) {
			game.playersTurn();
		} else if (op.equals("INVALID_MOVE")) {
			game.invalidMove();
		} else if (op.equals("VALID_MOVE")) {
			game.endTurn();
		} else if (op.equals("MESSAGE")) {
			System.out.println(ops[1]);
		} else if (op.equals("PLAYER_MOVED")) {
			String [] temp = ops[1].split(" : ");
			int playerId = Integer.parseInt(temp[0]) - 1;
			String suitStr = temp[2];
			Suit suit = Suit.valueOf(suitStr);
			int rankStr = Integer.parseInt(temp[1]);
			if(suit == Suit.H) rankStr += 2; // Accommodate for Jokers in Hearts
			Rank rank = Rank.fromInt(rankStr);
			Card card = new Card(suit, rank);
			game.playCard(playerId, card);
		} else if (op.equals("WIN")) {

		} else if (op.equals("LOSE")) {

		} else if (op.equals("DEALING")) {

		} else if (op.equals("CARD")) {
			String [] temp = ops[1].split(" : ");
			String suitStr = temp[1];
			Suit suit = Suit.valueOf(suitStr);
			int rankStr = Integer.parseInt(temp[0]);
			if(suit == Suit.H) rankStr += 2; // Accommodate for Jokers in Hearts
			Rank rank = Rank.fromInt(rankStr);
			Card card = new Card(suit, rank);
			game.dealCard(card);
		}		
		return true;
	}
}
