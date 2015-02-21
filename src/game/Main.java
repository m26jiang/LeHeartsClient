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
	private static final String serverIP = "54.149.107.164";
	private static final int serverPort = 9001;
	
	public static void main(String[] args) throws Exception {
		table = new Table();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Window(table);				
			}
		});
		
		game = new GameController(table);
		parser = new CommandParser(game);
		client = new LeHeartsHTTPClient(parser, serverIP, serverPort);
		
		client.play();
	}

}
