package ui;

import game.Card;
import game.GameController;
import game.Hand;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class CardStackEntity implements Observer {

	private ArrayList<CardEntity> cards;
	private Map<Card, CardEntity> cardEntityMap;
	private Hand hand;
	private int x;
	private int y;
	private int xSpacing;
	private int ySpacing;
	private boolean isClickable;
	private GameController gameController;

	public CardStackEntity(Hand hand, Map<Card, CardEntity> cardEntityMap,
			int x, int y) {
		this.cards = new ArrayList<CardEntity>();
		this.cardEntityMap = cardEntityMap;
		this.hand = hand;
		this.x = x;
		this.y = y;
		this.xSpacing = 0;
		this.ySpacing = 0;
		hand.addObserver(this);
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void draw(Graphics2D g2d) {
		synchronized(cards) {
			Iterator<CardEntity> it = cards.iterator();
			while (it.hasNext()) {
				CardEntity cardEntity = it.next();
				cardEntity.draw(g2d);
			}
		}
	}

	public CardEntity getClicked(MouseEvent e) {
		if (!isClickable()) {
			return null;
		}
		
		synchronized(cards) {
			int x = e.getX();
			int y = e.getY();
			boolean cardSelected = false;
			
			for (int index = cards.size() - 1; index >= 0; index--) {
				if (cards.get(index).isSelected()) {
					cardSelected = true;
					break;
				}				
			}
			
			for (int index = cards.size() - 1; index >= 0; index--) {
				CardEntity cardEntity = cards.get(index);
				if (cardEntity.collides(x, y) && cardEntity.isVisible()) {
					if (e.getClickCount() == 2 && !e.isConsumed()) {
						e.consume();
						gameController.playCard(cardEntity.getCardValue());
						break;
					}
					if (cardSelected == false) {
						cardEntity.select();
						break;
					} else if (cardSelected == true && cardEntity.isSelected()) {
						cardEntity.select();
						break;
					}
				}
			}		
			
		}		
		return null;
	}

	public void update() {
		synchronized (cards) {
			Iterator<CardEntity> it = cards.iterator();
			while (it.hasNext()) {
				CardEntity cardEntity = it.next();
				cardEntity.setVisible(false);
			}
			cards.clear();
			Iterator<Card> it2 = hand.getCards().iterator();
			
			int cardCount = 0;
			while (it2.hasNext()) {
				Card card = it2.next();
				CardEntity cardEntity = cardEntityMap.get(card);
				if (cardEntity != null) {
					cards.add(cardEntity);
					cardEntity.setX(x + xSpacing * cardCount);
					cardEntity.setY(y + ySpacing * cardCount);
					cardEntity.setVisible(true);
				}
				cardCount++;
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {		
//		synchronized (cards) {
//			Iterator<CardEntity> it = cards.iterator();
//			while (it.hasNext()) {
//				CardEntity cardEntity = it.next();
//				cardEntity.setVisible(false);
//			}
//			cards.clear();
//			Iterator<Card> it2 = hand.getCards().iterator();
//			
//			int cardCount = 0;
//			while (it2.hasNext()) {
//				Card card = it2.next();
//				CardEntity cardEntity = cardEntityMap.get(card);
//				if (cardEntity != null) {
//					cards.add(cardEntity);
//					cardEntity.setX(x + xSpacing * cardCount);
//					cardEntity.setY(y + ySpacing * cardCount);
//					cardEntity.setVisible(true);
//				}
//				cardCount++;
//			}
//		}
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

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}

	public int getXSpacing() {
		return xSpacing;
	}

	public void setXSpacing(int xSpacing) {
		this.xSpacing = xSpacing;
	}

	public int getYSpacing() {
		return ySpacing;
	}

	public void setYSpacing(int ySpacing) {
		this.ySpacing = ySpacing;
	}

}
