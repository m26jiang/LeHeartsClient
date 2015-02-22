package ui;

import game.GameController;
import game.Table;

import javax.swing.JFrame;

public class Window extends JFrame {

	public Window(Table table, GameController gameController) {
		this.setTitle("LeHearts");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new GameCanvas(table, gameController));
		this.setVisible(true);
	}
}
