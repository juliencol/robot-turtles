package deck;

/**
 * Type de donn�e repr�sentant une Carte.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public abstract class Card {
	private String color;
	
	/**
	 * Cr�� une carte de la couleur donn�e
	 * @param color, type cha�ne de caract�res repr�sentant la couleur de la carte
	 */
	public Card(String color) {
		this.color = color;
	}
		
	/**
	 * Fournit la couleur de la carte
	 * @return la couleur
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * Fournit la couleur de la carte sous forme de cha�ne de caract�res
	 */
	public String toString() {
		return color+"_card";
	}
}
