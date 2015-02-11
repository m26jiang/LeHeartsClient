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
	private static int baseX = 300, baseY = 600;
	
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
	}
	
	private void initImages() {
		
		cardImageHolder = new CardImageHolder();
		
//		TODO: Clean up this random test code.
		// Get all cards from player's hand and throw it into cards

		for (int i = 0; i < playerHand.length; i++) {
			if (playerHand[i] == null) {
				continue;
			} else {
				Card newCard = new Card(playerHand[i].getSuit(), playerHand[i].getRank());
				CardEntity newEntity = new CardEntity(newCard, cardImageHolder.getImage(newCard));
				this.cards.add(newEntity);
			}
		}
		
		// sort them by suit? server or client...
		// This loop just sets the position and visibility
		for (int i = 0; i < cards.size(); i++) {
			this.cards.get(i).setX(baseX + (i * 20));
			this.cards.get(i).setY(baseY);
			this.cards.get(i).setVisible(true);
		}

//		System.out.println(c1.hashCode());
//		System.out.println(c2.hashCode());
		
		repaint();
	}
	
	public void draw(Graphics2D g2d) {

//		TODO: Clean up this random test code.
		if (cardImageHolder.getImage(new Card(Suit.S, Rank.TWO)) == null) {
			System.out.println("You fucked up!");
		}
		for (int i = 0; i < cards.size(); i++) {
			this.cards.get(i).draw(g2d);
		}
//		g2d.drawImage(cardImageHolder.getImage(new Card(Suit.S, Rank.TWO)), 0, 0, null);
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
		// TODO Auto-generated method stub
	}
}
