package ui;

import game.Card;
import game.Rank;
import game.Suit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class CardImageHolder {
	
	private static final File IMAGE_DIRECTORY = new File("resource/cards");
	
	private static final String[] EXTENSIONS = new String[] {
		"png"
	};
	
	private Map<Card, BufferedImage> cardImageMap;
	
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return true;
				}
			}
			return false;
		}
	};
	
	public CardImageHolder() {
		loadCardImages();
	}
	
	private void loadCardImages() {
		cardImageMap = new HashMap<Card, BufferedImage>();
		if (IMAGE_DIRECTORY.isDirectory()) {
			for (final File f : IMAGE_DIRECTORY.listFiles(IMAGE_FILTER)) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(f);
					Card card = getCardFromFileName(f.getName());
					if (card != null) {
						cardImageMap.put(card, img);
					}					
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	private Card getCardFromFileName(String fileName) {		
		
		/** Shortest possible name should be: "xx.png" */
		if (fileName.length() < 6) {
			return null;
		}
		
		Card card = null;
		Suit suit = null;
		Rank rank = null;
		
		char posOne = fileName.charAt(0);
		char posTwo = fileName.charAt(1);
		
		switch (posOne) {
		case 'b':
			break;
		case 'c':
			suit = Suit.C;
			break;
		case 'h':
			suit = Suit.H;
			break;
		case 'd':
			suit = Suit.D;
			break;
		case 's':
			suit = Suit.S;
			break;
		}
		
		switch (posTwo) {
		case 'j':
			rank = Rank.JACK;
			break;
		case 'q':
			rank = Rank.QUEEN;
			break;
		case 'k':
			rank = Rank.KING;			
		}
		
		if (posTwo >= '2' && posTwo <= '9') {
			rank = Rank.fromInt(posTwo - '0');
		}
		
		if (posTwo == '1') {
			if (fileName.charAt(2) == '0') {
				rank = Rank.TEN;
			} else {
				rank = Rank.ACE;
			}
		}
		
		card = new Card(suit, rank);
		
		return card;
	}
	
	public BufferedImage getImage(Card card) {
		return cardImageMap.get(card);
	}
}
