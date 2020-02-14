package deck;

/**
 * Type de donnée représentant une Carte.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public abstract class Card {
	private String color;
	
	/**
	 * Créé une carte de la couleur donnée
	 * @param color, type chaîne de caractères représentant la couleur de la carte
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
	 * Fournit la couleur de la carte sous forme de chaîne de caractères
	 */
	public String toString() {
		return color+"_card";
	}
}
