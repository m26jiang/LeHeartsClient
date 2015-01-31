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
	private int rotation;
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
		this.rotation = 0;
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
		this.rotation = 0;
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
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void draw(Graphics2D g2d) {
		AffineTransform tx = AffineTransform.getQuadrantRotateInstance(
				rotation, 0, image.getHeight());
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
		g2d.drawImage(op.filter(image, null), x, y, null);
	}
}
