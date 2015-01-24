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
	private BufferedReader stdIn;
	private PrintWriter output;
	private GameController game;

	public LeHeartsHTTPClient(GameController game, String hostName, int portNumber) throws IOException {
		this.game = game;
		socket = new Socket(hostName, portNumber);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		output = new PrintWriter(socket.getOutputStream(), true);
	}

	public static void main(String[] args) throws Exception {
		Table table = new Table();
		GameController game = new GameController(table);
		LeHeartsHTTPClient client = new LeHeartsHTTPClient(game, "54.149.107.164", 9001);
		client.play();
	}

	public void play() throws Exception {
		String response;
		try {   
			while (true) {
				response = input.readLine();

				if (response == null) {
					continue;
				}

				System.out.println(response);
				String userString = parseCommand(response);
				if (!userString.isEmpty()) {
					output.println(userString);
					if (userString.equals("QUIT")) {
						break;
					}
				}
			}
		} finally {
			socket.close();
		}
	}

	public String parseCommand(String s) throws Exception {
		String ret = "";
		String [] ops = s.split(" ", 2);
		System.out.println(s);
		String op = ops[0];
		if (op.equals("YOUR_TURN")) {
			System.out.println("Server is requesting move!");
			ret = stdIn.readLine();
			return ret;
		} else if (op.equals("VALID_MOVE")) {
			return ret;
		}
		String param = ops[1];
		if (op.equals("WELCOME")) {
			int playerId = Integer.parseInt(ops[1]);
		} else if (op.equals("MESSAGE")) {
			
		} else if (op.equals("WELCOME")) {

		} else if (op.equals("PLAYER_MOVED")) {
			String [] temp = param.split(" : ");
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
		return ret;
	}

}