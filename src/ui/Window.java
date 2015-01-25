package ui;

import javax.swing.JFrame;

public class Window extends JFrame {

	public Window() {
		this.setTitle("LeHearts");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new GameCanvas());
		this.setVisible(true);
	}
}
