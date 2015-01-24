package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameCanvas extends JPanel implements KeyListener, MouseListener {

	public GameCanvas() {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.black);
		this.addKeyListener(this);
		this.addMouseListener(this);
	}
	
	public void draw(Graphics2D g2d) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
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
}
