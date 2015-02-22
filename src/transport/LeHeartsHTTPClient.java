package transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ui.GameCanvas;

public class LeHeartsHTTPClient {

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private CommandParser parser;
	private String outputBuffer;
	private GameCanvas gameCanvas;

	public LeHeartsHTTPClient(CommandParser parser, String hostName, int portNumber) throws IOException {
		this.parser = parser;
		socket = new Socket(hostName, portNumber);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void play() throws Exception {
		String response;
		try {   
			while (true) {
				if (input.ready()) {
					response = input.readLine();
					if (gameCanvas != null) {
						gameCanvas.outputText(response);
					}					
				} else {
					response = null;
				}
				
				if (outputBuffer != null) {
					output.println(outputBuffer);
					outputBuffer = null;
				}

				if (response == null) {
					continue;
				}

				if (!parser.parseCommand(response)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
	
	public void setGameCanvas(GameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}
	
	public void setBuffer(String buf) {
		if (gameCanvas != null) {
			gameCanvas.outputText(buf);
		}
		outputBuffer = buf;
	}
}