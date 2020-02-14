package deck;

import java.util.ArrayList;

/**
 * Type de donnee representant un Deck.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class Deck {
	private ArrayList<Card> deck;
	
	/**
	 * Cree un deck contenant 18 cartes bleues, 12 cartes jaunes, 8 cartes violettes et 3 cartes laser
	 */
	public Deck() {
		this.deck = new ArrayList<Card>();
		for (int i = 0; i < 37; i++) {
			if (i < 18) {
				this.deck.add(new BlueCard());
			}
			else if (i < 26) {
				this.deck.add(new YellowCard());
			}
			else if (i < 34) {
				this.deck.add(new PurpleCard());
			}
			else {
				this.deck.add(new LaserCard());
			}
		}
	}
	
	/**
	 * Fournit le deck 
	 * @return le deck
	 */
	public ArrayList<Card> getCards(){
		return this.deck;
	}
	
	/**
	 * 
	 * @param deck
	 */
	public void setDeck(ArrayList<Card> deck){
		this.deck = deck;
	}

}
