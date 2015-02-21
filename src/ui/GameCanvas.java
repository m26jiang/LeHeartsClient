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
import game.Hand;
import game.Player;
import game.Rank;
import game.Suit;
import game.Table;

public class GameCanvas extends JPanel implements KeyListener, MouseListener, Observer {

	private CardImageHolder cardImageHolder;
	private Map<Card, CardEntity> cardEntityMap;
	private Table table;
	private Card[] playerHand;
	private final static int BASE_X = 250, BASE_Y = 400;
	
	private final static long secInNanosec = 1000000000L;
	private final static long millisecInNanosec = 1000000L;
	private final static int GAME_FPS = 30;
	private final static long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;	
	private final static int GAME_THREAD_SLEEP_MIN = 10;
	private final static int CARD_X_SPACING = 20;
	
	public GameCanvas(Table table) {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(new Color(0x00, 0x8a, 0x2e));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.table = table;
		this.cardEntityMap = new HashMap<Card, CardEntity>();
		this.playerHand = new Card[13];
		this.playerHand = this.table.players[0].getHand().getCards().toArray(playerHand);
		table.addObserver(this);
		initImages();
		
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
			} catch (InterruptedException ex) { }
		}
	}
	
	private void initImages() {		
		cardImageHolder = new CardImageHolder();
	}
	
	public void draw(Graphics2D g2d) {
		synchronized(this) {
			Iterator<Card> iter = table.players[0].getHand().getCards().iterator();
			int cardCount = 0;
			while (iter.hasNext()) {
				Card card = iter.next();
				CardEntity cardEntity = cardEntityMap.get(card);
				if (cardEntity != null) {
					cardEntity.setX(BASE_X + 20 * cardCount);
					cardEntity.setY(BASE_Y);
					cardEntity.draw(g2d);
				} else {
					BufferedImage img  = cardImageHolder.getImage(card);
					if (img != null) {
						CardEntity newCardEntity = new CardEntity(card, img);
						cardEntityMap.put(card, newCardEntity);
						newCardEntity.setX(BASE_X + CARD_X_SPACING * cardCount);
						newCardEntity.setY(BASE_Y);
						newCardEntity.setVisible(true);	
						newCardEntity.draw(g2d);
					}
				}
				cardCount++;
			}
		}
	}	
	
	/** This method is called by the repaint method. */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		draw(g2d);
	}
	@Override
	public void keyPressed(KeyEvent e) { }	
	@Override
	public void keyReleased(KeyEvent e) { }
	@Override
	public void keyTyped(KeyEvent e) { }
	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { }
	@Override
	public void mouseReleased(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
