package ui;

import game.Card;
import game.Table;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class CardStageEntity implements Observer {

	private ArrayList<CardEntity> cards;
	private Point [] cardLocations;
	private Map<Card, CardEntity> cardEntityMap;
	private Table table;
	private static int NUM_PLAYERS = 4;
	private static int CARD_WIDTH = 71;
	private static int CARD_HEIGHT = 96;

	public CardStageEntity(Table table, Map<Card, CardEntity> cardEntityMap, int panelWidth, int panelHeight) {
		this.table = table;
		this.cardEntityMap = cardEntityMap;
		this.table.addObserver(this);
		this.cards = new ArrayList<CardEntity>();
	}

	public void initCardLocations(int panelWidth, int panelHeight) {
		cardLocations = new Point[NUM_PLAYERS];		
		cardLocations[0].setLocation((panelWidth / 2) - (CARD_WIDTH / 2), (panelHeight / 2) + (CARD_HEIGHT / 2));
		cardLocations[1].setLocation((panelWidth / 2) + 3 * (CARD_WIDTH / 2), (panelHeight / 2) - (CARD_HEIGHT / 2));
		cardLocations[2].setLocation((panelWidth / 2) - (CARD_WIDTH / 2), (panelHeight / 2) - 3 * (CARD_HEIGHT / 2));
		cardLocations[3].setLocation((panelWidth / 2) - 3 * (CARD_WIDTH / 2), (panelHeight / 2) - (CARD_HEIGHT / 2));
	}
	
	public void draw(Graphics2D g2d) {
		if (cards == null) {
			return;
		}
		
		Iterator<CardEntity> it = cards.iterator();
		while (it.hasNext()) {
			it.next().draw(g2d);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Iterator<CardEntity> it = cards.iterator();	
		while (it.hasNext()) {
			it.next().setVisible(false);
		}		
		cards.clear();
		Card [] tableCards = table.getCards();		
		for (int i = 0; i < tableCards.length; i++) {
			CardEntity cardEntity = cardEntityMap.get(tableCards[i]);
			if (cardEntity == null) {
				continue;
			}
			cardEntity.setX((int)cardLocations[i].getX());
			cardEntity.setY((int)cardLocations[i].getY());
			cardEntity.setVisible(true);
			cards.add(cardEntity);
		}
	}
}
