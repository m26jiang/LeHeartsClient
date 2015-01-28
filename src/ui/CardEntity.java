package ui;

import game.Card;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CardEntity {
	
	private int x;
	private int y;
	private boolean isVisible;
	private boolean isFaceDown;
	private BufferedImage image;
	private Card cardValue;
	
	public CardEntity() {
		this.x = 0;
		this.y = 0;
		this.isVisible = false;
		this.isFaceDown = false;
		this.cardValue = null;
	}

	public CardEntity(Card cardValue, BufferedImage image) {
		this.x = 0;
		this.y = 0;
		this.isVisible = false;
		this.isFaceDown = false;
		this.cardValue = cardValue;
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isFaceDown() {
		return isFaceDown;
	}

	public void setFaceDown(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Card getCardValue() {
		return cardValue;
	}

	public void setCardValue(Card cardValue) {
		this.cardValue = cardValue;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, null);
	}
}
