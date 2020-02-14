package occupier;

/**
 * Type de donnee representant une Coordonnee.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class Coordinate {
	private int x;
	private int y;
	
	/**
	 * Cree une couple de coordonnees avec ceux saisies
	 * @param x, type entier representant l'abscisse
	 * @param y, type entier representant l'ordonnee
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Fournit l'abscisse
	 * @return l'abscisse
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Modifie l'abscisse
	 * @param x, type entier representant la nouvelle abscisse
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Fournit l'ordonnee
	 * @return l'ordonnee
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Modifie l'ordonnee
	 * @param y, type entier representant la nouvelle ordonnee
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Coordinate)) {
	        return false;
	    }

	    Coordinate that = (Coordinate) other;

	    // Custom equality check here.
	    return this.x==that.x
	        && this.y==that.y;
	}
	
}
