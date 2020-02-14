package occupier;

/**
 * Type de donnee representant une Couleur.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public enum Color{
    BLUE ("blue"), 
    RED ("red"), 
    GREEN ("green"), 
    PINK ("pink");

	private String color = "";
	
	/**
	 * Definit la couleur saisie
	 * @param color, type chaine de caracteres qui represente la couleur
	 */
	Color(String color){
		this.color = color;
	}
	
	/**
	 * Fournit la couleur sous forme de cha?ne de caract?res
	 */
	public String toString() {
		return color;
	}

}