package game;

import transport.CommandParser;
import transport.LeHeartsHTTPClient;
import ui.Window;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) throws Exception {
		Table table = new Table();
		GameController game = new GameController(table);
		CommandParser parser = new CommandParser(game);
		LeHeartsHTTPClient client = new LeHeartsHTTPClient(parser, "54.149.107.164", 9001);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Window();
			}
		});
		
		client.play();
	}

}
