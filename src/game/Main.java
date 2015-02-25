package game;

import transport.CommandParser;
import transport.LeHeartsHTTPClient;
import ui.Window;

import javax.swing.SwingUtilities;

public class Main {

	private static Table table;
	private static GameController game;
	private static CommandParser parser;
	private static LeHeartsHTTPClient client;
	private static Window window;
	private static final String serverIP = "52.10.214.35";
	private static final int serverPort = 9001;

	public static void main(String[] args) throws Exception {
		
		table = new Table();
		game = new GameController(table);
		parser = new CommandParser(game);		
		client = new LeHeartsHTTPClient(parser, serverIP, serverPort);
		game.setHttpClient(client);
		
		// We need to use invoke and wait because many things rely on the
		// GameCanvas not being null
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				window = new Window(table, game);
			}
		});
		
		client.play();
	}

}
