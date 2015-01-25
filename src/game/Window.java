package game;

import javax.swing.JFrame;

public class Window extends JFrame {

	private Window() {
		this.setTitle("LeHearts");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);		
	}
	
}
