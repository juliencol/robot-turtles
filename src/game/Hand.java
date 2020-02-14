package game;
import java.util.ArrayList;

import deck.Card;

public class Hand {
	private ArrayList<Card> hand;
	
	public Hand() {
		this.hand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getCards() {
		return hand;
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
	}

}
