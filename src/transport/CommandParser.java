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
		if (op.equals("YOUR_TURN")) {
			System.out.println("Server is requesting move!");
			game.playersTurn();
		} else if (op.equals("VALID_MOVE")) {
		}
		if (op.equals("WELCOME")) {
			int playerId = Integer.parseInt(ops[1]);
		} else if (op.equals("MESSAGE")) {
			System.out.println(ops[1]);
		} else if (op.equals("WELCOME")) {

		} else if (op.equals("PLAYER_MOVED")) {
			String [] temp = ops[1].split(" : ");
			int playerId = Integer.parseInt(temp[0]) - 1;
			int rankStr = Integer.parseInt(temp[1]);
			String suitStr = temp[2];
			Rank rank = Rank.fromInt(rankStr);
			Suit suit = Suit.valueOf(suitStr);
			Card card = new Card(suit, rank);
			game.playerMove(playerId, card);
		} else if (op.equals("WIN")) {

		} else if (op.equals("LOSE")) {

		} else if (op.equals("DEALING")) {

		} else if (op.equals("CARD")) {

		}
		return true;
	}
}
