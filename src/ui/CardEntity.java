package ui;

import game.Card;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class CardEntity {

	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isSelected;
	private boolean isVisible;
	private boolean isFaceDown;
	private BufferedImage image;
	private Card cardValue;
	
	private static final int CARD_SELECT_Y_MOVE = 20;

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
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void select() {
		isSelected = !isSelected;
		
		if (isSelected) {
			y -= CARD_SELECT_Y_MOVE;
		} else {
			y += CARD_SELECT_Y_MOVE;
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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

	public boolean collides(int x, int y) {
		if (x >= this.x && x <= (this.x + width) && y >= this.y
				&& y <= (this.y + height)) {
			return true;
		} else {
			return false;
		}
	}
}
