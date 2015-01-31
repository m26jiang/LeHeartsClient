package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import game.Table;

public class GameCanvas extends JPanel implements KeyListener, MouseListener, Observer {

	private CardImageHolder cardImageHolder;
	private Table table;
	
	public GameCanvas(Table table) {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(new Color(0x00, 0x8a, 0x2e));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.table = table;
		table.addObserver(this);
		initImages();
	}
	
	private void initImages() {
		cardImageHolder = new CardImageHolder();
		
//		TODO: Clean up this random test code.
//		Card c1 = new Card(Suit.C, Rank.ACE);
//		ce1 = new CardEntity(c1, cardImageHolder.getImage(c1));
//		ce1.setX(100); ce1.setY(100); ce1.setVisible(true);
//		
//		Card c2 = new Card(Suit.C, Rank.TWO);		
//		ce2 = new CardEntity(c2, cardImageHolder.getImage(c2));
//		ce2.setX(120); ce2.setY(90); ce2.setVisible(true);
//		
//		System.out.println(c1.hashCode());
//		System.out.println(c2.hashCode());
		
//		repaint();
	}
	
	public void draw(Graphics2D g2d) {

//		TODO: Clean up this random test code.
//		if (cardImageHolder.getImage(new Card(Suit.S, Rank.TWO)) == null) {
//			System.out.println("You fucked up!");
//		}
//		
//		ce1.draw(g2d);
//		ce2.draw(g2d);
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
