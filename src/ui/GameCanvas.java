package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JPanel;

import game.Card;
import game.Hand;
import game.Rank;
import game.Suit;
import game.Table;

public class GameCanvas extends JPanel implements KeyListener, MouseListener, Observer {

	private CardImageHolder cardImageHolder;
	private Table table;
	private ArrayList<CardEntity> cards;
	private Card[] playerHand;
	private final static int BASE_X = 250, BASE_Y = 500;
	
	private final static long secInNanosec = 1000000000L;
	private final static long millisecInNanosec = 1000000L;
	private final static int GAME_FPS = 30;
	private final static long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;	
	private final static int GAME_THREAD_SLEEP_MIN = 10;
	
	public GameCanvas(Table table) {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(new Color(0x00, 0x8a, 0x2e));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.table = table;
		this.cards = new ArrayList<CardEntity>();
		this.playerHand = new Card[13];
		this.playerHand = this.table.players[0].getHand().getCards().toArray(playerHand);
		table.addObserver(this);
		initImages();
		
		gameLoop();
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
		
		// TODO: Consider doing this without the cardlist of entities
		// Get all cards from player's hand and throw it into card entities

		for (int i = 0; i < playerHand.length; i++) {
			if (playerHand[i] == null) {
				continue;
			} else if (playerHand[i].getSuit() != null && playerHand[i].getRank() != null){
				Card newCard = new Card(playerHand[i].getSuit(), playerHand[i].getRank());
				CardEntity newEntity = new CardEntity(newCard, cardImageHolder.getImage(newCard));
				this.cards.add(newEntity);
			}
		}
		
		// This loop just sets the position and visibility
		for (int i = 0; i < cards.size(); i++) {
			this.cards.get(i).setX(BASE_X + (i * 20));
			this.cards.get(i).setY(BASE_Y);
			this.cards.get(i).setVisible(true);
		}
		
		repaint();
	}
	
	public void draw(Graphics2D g2d) {		

//		TODO: Clean up this random test code.
		if (cardImageHolder.getImage(new Card(Suit.S, Rank.TWO)) == null) {
			System.out.println("You fucked up!");
		}

		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).draw(g2d);
		}
	}
	
	/** This method is called by the repaint method. */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		draw(g2d);
	}
	
	private void setYCoordinates(int index) {
		
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getY() != BASE_Y && i != index) {
				// Resets position if something else is selected
				cards.get(i).setY(BASE_Y);
			} else if (i == index  && cards.get(i).getY() != BASE_Y) {
				// PLAY CARD SELECTED HERE? MOVE TO TABLE AND DO REQUEST TO SERVER
			} else if (i == index && cards.get(i).getY() == BASE_Y) {
				// move card up to let user know it's been selected
				cards.get(index).setY(BASE_Y - 10);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) { }	
	@Override
	public void keyReleased(KeyEvent e) { }
	@Override
	public void keyTyped(KeyEvent e) { }
	@Override
	public void mouseClicked(MouseEvent e) { 
		if (e.getX() > BASE_X && e.getX() < BASE_X + cards.size() * 20 && e.getY() > BASE_Y && e.getY() < BASE_Y + 100) {
			setYCoordinates((e.getX() - BASE_X)  / 20);
		} else if (e.getX() > BASE_X + cards.size() * 20 && e.getX() < BASE_X + cards.size() * 20 + 80 && e.getY() > BASE_Y && e.getY() < BASE_Y + 100) {
			setYCoordinates(cards.size() - 1);
		}
		repaint();
	}
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
		// TODO Auto-generated method stub
		System.out.println("Object has changed: " + o.getClass().getName());
		repaint();
	}
}
