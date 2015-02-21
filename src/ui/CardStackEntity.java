package ui;

import game.Card;
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
	
	private final static int CARD_X_SPACING = 20;

	public CardStackEntity(Hand hand, Map<Card, CardEntity> cardEntityMap,
			int x, int y) {
		this.cards = new ArrayList<CardEntity>();
		this.cardEntityMap = cardEntityMap;
		this.hand = hand;
		this.x = x;
		this.y = y;
		hand.addObserver(this);
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
		synchronized(cards) {
			int x = e.getX();
			int y = e.getY();
			boolean cardSelected = false;
			
			System.out.println("MouseEvent: X: " + x + " Y: " + y);
			
			for (int index = cards.size() - 1; index >= 0; index--) {
				if (cards.get(index).isSelected()) {
					cardSelected = true;
					break;
				}				
			}
			
			for (int index = cards.size() - 1; index >= 0; index--) {
				CardEntity cardEntity = cards.get(index);
				if (cardEntity.collides(x, y) && cardEntity.isVisible()) {
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

	@Override
	public void update(Observable o, Object arg) {		
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
					cardEntity.setX(x + CARD_X_SPACING * cardCount);
					cardEntity.setY(y);
					cardEntity.setVisible(true);
				}
				cardCount++;
			}
		}
	}

}
