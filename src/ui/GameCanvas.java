package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JPanel;

import game.Card;
import game.GameController;
import game.Hand;
import game.Player;
import game.Rank;
import game.Suit;
import game.Table;

public class GameCanvas extends JPanel implements KeyListener, MouseListener,
		Observer {

	private CardImageHolder cardImageHolder;
	private CardStackEntity playerCards;
	private CardStackEntity[] collectedCards;
	private GameController gameController;
	private Map<Card, CardEntity> cardEntityMap;
	private Table table;
	private final static int BASE_X = 250, BASE_Y = 400;
	private final static int PLAYER_2_X = 50, PLAYER_2_Y = 50;
	private final static int PLAYER_3_X = 250, PLAYER_3_Y = 50;
	private final static int PLAYER_4_X = 500, PLAYER_4_Y = 50;

	private final static long secInNanosec = 1000000000L;
	private final static long millisecInNanosec = 1000000L;
	private final static int GAME_FPS = 30;
	private final static long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
	private final static int GAME_THREAD_SLEEP_MIN = 10;

	public GameCanvas(Table table, GameController gameController) {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(new Color(0x00, 0x8a, 0x2e));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.table = table;
		this.gameController = gameController;
		this.cardEntityMap = new HashMap<Card, CardEntity>();
		table.addObserver(this);
		initImages();
		initCardEntities();
		this.playerCards = new CardStackEntity(table.players[0].getHand(),
				cardEntityMap, BASE_X, BASE_Y);
		playerCards.setClickable(true);
		collectedCards = new CardStackEntity[4];
		playerCards.setXSpacing(20);
		playerCards.setGameController(gameController);

		this.collectedCards[0] = new CardStackEntity(
				table.players[0].getCollect(), cardEntityMap, BASE_X,
				BASE_Y - 100);
		collectedCards[0].setXSpacing(20);
		
		this.collectedCards[1] = new CardStackEntity(
				table.players[1].getCollect(), cardEntityMap, PLAYER_2_X,
				PLAYER_2_Y);
		collectedCards[0].setYSpacing(20);
		
		this.collectedCards[2] = new CardStackEntity(
				table.players[2].getCollect(), cardEntityMap, PLAYER_3_X,
				PLAYER_3_Y);
		collectedCards[0].setXSpacing(20);
		
		this.collectedCards[3] = new CardStackEntity(
				table.players[3].getCollect(), cardEntityMap, PLAYER_4_X,
				PLAYER_4_Y);
		collectedCards[0].setYSpacing(20);		

		Thread gameThread = new Thread() {
			@Override
			public void run() {
				gameLoop();
			}
		};

		gameThread.start();
	}

	/**
	 * In specific intervals of (GAME_UPDATE_PERIOD) the game logic is updated
	 * and the Canvas will be redrawn.
	 */
	private void gameLoop() {
		long beginTime, timeTaken, timeLeft;

		while (true) {
			beginTime = System.nanoTime();

			repaint();

			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / millisecInNanosec;

			// If the time is less than 10 milliseconds, then we will put the
			// the thread to sleep for 10 milliseconds so other threads have
			// have time to run.
			if (timeLeft < GAME_THREAD_SLEEP_MIN) {
				timeLeft = GAME_THREAD_SLEEP_MIN;
			}
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException ex) {
			}
		}
	}

	private void initImages() {
		cardImageHolder = new CardImageHolder();
	}

	private void initCardEntities() {
		for (int i = 0; i < Suit.values().length; i++) {
			Suit suit = Suit.values()[i];
			for (int j = 0; j < Rank.values().length; j++) {
				Rank rank = Rank.values()[j];
				BufferedImage img = null;
				Card card = null;

				if (suit != Suit.H) {
					if (rank != Rank.LJOKER && rank != Rank.SJOKER) {
						card = new Card(suit, rank);
						img = cardImageHolder.getImage(card);
					}
				}

				if (suit == Suit.H) {
					if (rank != Rank.TWO && rank != Rank.THREE) {
						card = new Card(suit, rank);
						img = cardImageHolder.getImage(card);
					}
				}

				if (img != null && card != null) {
					CardEntity cardEntity = new CardEntity(card, img);
					cardEntityMap.put(card, cardEntity);
				}
			}
		}
	}

	public void draw(Graphics2D g2d) {
		playerCards.draw(g2d);
	}

	/** This method is called by the repaint method. */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		draw(g2d);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		playerCards.getClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
