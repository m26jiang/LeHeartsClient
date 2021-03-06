package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Card;
import game.GameController;
import game.Rank;
import game.Suit;
import game.Table;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel implements KeyListener, MouseListener {

	private CardImageHolder cardImageHolder;
	private CardStackEntity playerCards;
	private CardStackEntity[] collectedCards;
	private CardStageEntity cardStage;
	private GameController gameController;
	private Map<Card, CardEntity> cardEntityMap;
	private Table table;
	private Font uiFont;
	private Point[] playerNameLocations;
	private boolean nameRequestSent;
	private String playerName;
	
	// TODO: Try to clean up all these unnecessary constants
	private final static int GAME_WIDTH = 800;
	private final static int GAME_HEIGHT = 600;
	private final static int CARD_WIDTH = 71;
	private final static int CARD_HEIGHT = 96;
	private final static int PLAYER_1_X = 250, PLAYER_1_Y = GAME_HEIGHT - CARD_HEIGHT - 50;
	private final static int PLAYER_2_X = GAME_WIDTH - CARD_WIDTH - 50, PLAYER_2_Y = 50;
	private final static int PLAYER_3_X = 250, PLAYER_3_Y = 50;
	private final static int PLAYER_4_X = 50, PLAYER_4_Y = 50;
	private final static long SEC_IN_NANOSEC = 1000000000L;
	private final static long MILLISEC_IN_NANOSEC = 1000000L;
	private final static int GAME_FPS = 30;
	private final static long GAME_UPDATE_PERIOD = SEC_IN_NANOSEC / GAME_FPS;
	private final static int GAME_THREAD_SLEEP_MIN = 10;
	
	public GameCanvas(Table table, GameController gameController) {

		initPanel();

		this.table = table;
		this.gameController = gameController;
		this.cardEntityMap = new HashMap<Card, CardEntity>();
		this.nameRequestSent = false;

		initImages();
		initEntities();

		playerName = JOptionPane.showInputDialog("Enter your name:");
		
		Thread gameThread = new Thread() {
			@Override
			public void run() {
				gameLoop();
			}
		};

		gameThread.start();
	}

	private void initPanel() {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(new Color(0x00, 0x8a, 0x2e));
		this.addKeyListener(this);
		this.addMouseListener(this);

		playerNameLocations = new Point[4];
		playerNameLocations[0] = new Point(GAME_WIDTH / 2, GAME_HEIGHT - 40);
		playerNameLocations[1] = new Point(GAME_WIDTH - 200, 20);
		playerNameLocations[2] = new Point(GAME_WIDTH / 2, 20);
		playerNameLocations[3] = new Point(20, 20);

		uiFont = new Font("Arial", Font.PLAIN, 16);
	}

	/**
	 * In specific intervals of (GAME_UPDATE_PERIOD) the game logic is updated
	 * and the Canvas will be redrawn.
	 */
	private void gameLoop() {
		long beginTime, timeTaken, timeLeft;

		while (true) {
			beginTime = System.nanoTime();

			playerCards.update();
			collectedCards[0].update();
			collectedCards[1].update();
			collectedCards[2].update();
			collectedCards[3].update();
			cardStage.update();

			/**
			 * Apparently I have to wait for the server to deal all of the
			 * cards in order to be able to request a change of name...
			 * 
			 * Why do you do this Chung?
			 */
			if (!nameRequestSent
					&& !table.players[0].getHand().getCards().isEmpty()) {				
				nameRequestSent = true;
				gameController.requestName(playerName);
			}

			repaint();

			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / MILLISEC_IN_NANOSEC;

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

	private void initEntities() {
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
		
		this.playerCards = new CardStackEntity(
				table.players[0].getHand(),
				cardEntityMap, 
				PLAYER_1_X, 
				PLAYER_1_Y);
		
		playerCards.setClickable(true);
		collectedCards = new CardStackEntity[4];
		playerCards.setXSpacing(20);
		playerCards.setGameController(gameController);

		this.collectedCards[0] = new CardStackEntity(
				table.players[0].getCollect(), 
				cardEntityMap, 
				50, 
				PLAYER_1_Y - 50 - CARD_HEIGHT);
		collectedCards[0].setXSpacing(20);

		this.collectedCards[1] = new CardStackEntity(
				table.players[1].getCollect(), 
				cardEntityMap, 
				PLAYER_2_X,
				PLAYER_2_Y);
		collectedCards[1].setYSpacing(20);

		this.collectedCards[2] = new CardStackEntity(
				table.players[2].getCollect(),
				cardEntityMap, 
				PLAYER_3_X,
				PLAYER_3_Y);
		collectedCards[2].setXSpacing(20);

		this.collectedCards[3] = new CardStackEntity(
				table.players[3].getCollect(),
				cardEntityMap,
				PLAYER_4_X,
				PLAYER_4_Y);
		collectedCards[3].setYSpacing(20);

		this.cardStage = new CardStageEntity(
				table, 
				cardEntityMap, 
				GAME_WIDTH, 
				GAME_HEIGHT);
	}

	public void draw(Graphics2D g2d) {
		playerCards.draw(g2d);
		collectedCards[0].draw(g2d);
		collectedCards[1].draw(g2d);
		collectedCards[2].draw(g2d);
		collectedCards[3].draw(g2d);
		cardStage.draw(g2d);

		g2d.setColor(Color.BLACK);
		g2d.setFont(uiFont);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		for (int i = 0; i < table.players.length; i++) {
			if (table.players[i].getUserId() != null) {
				g2d.drawString(table.players[i].getUserId(),
						(int) playerNameLocations[i].getX(),
						(int) playerNameLocations[i].getY());
			}
		}

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
}
