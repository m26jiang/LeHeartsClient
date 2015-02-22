package ui;

import game.GameController;
import game.Table;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private GameCanvas gameCanvas;

	public Window(Table table, GameController gameController) {
		this.setTitle("LeHearts");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameCanvas = new GameCanvas(table, gameController);
        this.setContentPane(gameCanvas);
		this.setVisible(true);
	}

	public GameCanvas getGameCanvas() {
		return this.gameCanvas;
	}	
}
