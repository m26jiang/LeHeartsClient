package transport;

import game.Card;
import game.GameController;
import game.Rank;
import game.Suit;
import game.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LeHeartsHTTPClient {

	private Socket socket;
	private BufferedReader input;
//	private BufferedReader stdIn;
//	private PrintWriter output;
	private CommandParser parser;

	public LeHeartsHTTPClient(CommandParser parser, String hostName, int portNumber) throws IOException {
		this.parser = parser;
		socket = new Socket(hostName, portNumber);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		stdIn = new BufferedReader(new InputStreamReader(System.in));
//		output = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void play() throws Exception {
		String response;
		try {   
			while (true) {
				response = input.readLine();

				if (response == null) {
					continue;
				}

				if (!parser.parseCommand(response)) {
					break;
				}
			}
		} finally {
			socket.close();
		}
	}
}