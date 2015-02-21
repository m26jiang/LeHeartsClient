package transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LeHeartsHTTPClient {

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private CommandParser parser;
	private String outputBuffer;

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
				response = input.readLine();
				
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
		} finally {
			socket.close();
		}
	}
	
	public void setBuffer(String buf) {
		outputBuffer = buf;
	}
}