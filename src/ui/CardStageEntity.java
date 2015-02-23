package ui;

import game.Card;
import game.Table;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class CardStageEntity implements Observer {

	private CardEntity[] cards;
	private Point[] cardLocations;
	private Map<Card, CardEntity> cardEntityMap;
	private Table table;
	private static int NUM_PLAYERS = 4;
	private static int CARD_WIDTH = 71;
	private static int CARD_HEIGHT = 96;

	public CardStageEntity(Table table, Map<Card, CardEntity> cardEntityMap,
			int panelWidth, int panelHeight) {
		this.table = table;
		this.cardEntityMap = cardEntityMap;
		this.cards = new CardEntity[NUM_PLAYERS];
		this.table.addObserver(this);
		initCardLocations(panelWidth, panelHeight);
	}

	public void initCardLocations(int panelWidth, int panelHeight) {
		cardLocations = new Point[NUM_PLAYERS];
		cardLocations[0] = new Point(
				(panelWidth / 2) - (CARD_WIDTH / 2),
				(panelHeight / 2));
		cardLocations[1] = new Point(
				(panelWidth / 2) + (CARD_WIDTH / 2),
				(panelHeight / 2) - (CARD_HEIGHT / 2));
		cardLocations[2] = new Point(
				(panelWidth / 2) - (CARD_WIDTH / 2),
				(panelHeight / 2) - CARD_HEIGHT);
		cardLocations[3] = new Point(
				(panelWidth / 2) - 3 * (CARD_WIDTH / 2),
				(panelHeight / 2) - (CARD_HEIGHT / 2));
	}

	public void draw(Graphics2D g2d) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == null) {
				continue;
			}
			cards[i].draw(g2d);
		}
	}

	public void update() {
		Card[] tableCards = table.getCards();
		for (int i = 0; i < tableCards.length; i++) {
			cards[i] = null;
			
			CardEntity cardEntity = cardEntityMap.get(tableCards[i]);
			if (cardEntity == null) {
				continue;
			}
			cardEntity.setX((int) cardLocations[i].getX());
			cardEntity.setY((int) cardLocations[i].getY());
			cardEntity.setVisible(true);
			cards[i] = cardEntity;
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
//		System.out.println("Updating Staged Cards");
//		Card[] tableCards = table.getCards();
//		for (int i = 0; i < tableCards.length; i++) {
//			cards[i] = null;
//			if (tableCards[i] != null) {
//				System.out.println("Table[" + i + "]: " + tableCards[i].toString());
//			}			
//			
//			CardEntity cardEntity = cardEntityMap.get(tableCards[i]);
//			if (cardEntity == null) {
//				continue;
//			}
//			cardEntity.setX((int) cardLocations[i].getX());
//			cardEntity.setY((int) cardLocations[i].getY());
//			cardEntity.setVisible(true);
//			cards[i] = cardEntity;
//		}
	}
}


