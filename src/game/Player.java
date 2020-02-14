package game;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import deck.Card;
import deck.Deck;
import deck.ObstacleCard;

import occupier.ObstacleType;
import occupier.Turtle;

/**
 * Type de donn?e repr?sentant un Joueur.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class Player {
	private Turtle turtle;
	private Hand hand;
	private Deck deck;
	private List<ObstacleCard> obstaclesDeck;
	private List<Card> fullProgram;
	private List<Card> currentProgram;
	private List<Card> discardedCards;
	
	/**
	 * Cree un joueur, il possedera une tortue, un deck, une main ainsi que des obstacles
	 * @param turtle, type Turtle qui represente la tortue du joueur, present sur le plateau
	 */
	public Player(Turtle turtle) {
		this.turtle = turtle;
		this.hand = new Hand();
		this.deck = new Deck();
		this.fullProgram = new ArrayList<Card>();
		this.currentProgram = new ArrayList<Card>();
		this.discardedCards = new ArrayList<Card>();
		Collections.shuffle(this.deck.getCards());
		drawHand();
		initObstacles();
	}
	
	/**
	 * Pioche des cartes dans le deck jusqu'a avoir 5 cartes dans la main
	 */
	public void drawHand() {
		while (this.hand.getCards().size() < 5) {
			this.hand.addCard(this.deck.getCards().get(this.deck.getCards().size()-1));
			this.deck.getCards().remove(this.deck.getCards().size()-1);
			if (this.deck.getCards().isEmpty()) {
				for (int i = 0; i < this.discardedCards.size() ; i++) {
					this.deck.getCards().add(this.discardedCards.get(i));
				}
				this.discardedCards.clear();
				Collections.shuffle(this.deck.getCards());
			}
		}
	}
	
	/**
	 * Distribue 20 murs de pierre, 12 murs de glace et 4 caisses de bois
	 */
	private void initObstacles() {
		obstaclesDeck = new ArrayList<ObstacleCard>();
		for (int i = 0; i < 5; i++) {
			if (i < 2) {
				obstaclesDeck.add(new ObstacleCard(ObstacleType.STONEWALL));
			}
			else if (i < 5) {
				obstaclesDeck.add(new ObstacleCard(ObstacleType.ICEWALL));
			}
		}
	}
	
	/**
	 * Fournit les diff?rents obstacles du joueur
	 * @return la liste d'obstacles
	 */
	public List<ObstacleCard> getObstacles(){
		return obstaclesDeck;
	}
	
	/**
	 * Fournit la tortue du joueur
	 * @return la tortue
	 */
	public Turtle getTurtle() {
		return turtle;
	}

	/**
	 * Fournit la main du joueur
	 * @return la main
	 */
	public Hand getHand() {
		return hand;
	}
	
	/**
	 * Fournit le programme complet du joueur
	 * @return le programme complet 
	 */
	public List<Card> getFullProgram(){
		return fullProgram;
	}
	
	/**
	 * Fournit le programme que le joueur vient de completer
	 * @return le programme que le joueur vient de completer
	 */
	public List<Card> getCurrentProgram(){
		return currentProgram;
	}
	
	/**
	 * Fournit la pile de defausse
	 * @return la pile de defausse
	 */
	public List<Card> getDiscardedCards(){
		return discardedCards;
	}
	
	/**
	 * Fournit le deck
	 * @return le deck
	 */
	public Deck getDeck() {
		return deck;
	}
}
