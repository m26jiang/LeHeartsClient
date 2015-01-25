package game;

import transport.CommandParser;
import transport.LeHeartsHTTPClient;

public class Main {

	public static void main(String[] args) throws Exception {
		Table table = new Table();
		GameController game = new GameController(table);
		CommandParser parser = new CommandParser(game);
		LeHeartsHTTPClient client = new LeHeartsHTTPClient(parser, "54.149.107.164", 9001);
		client.play();
	}

}
