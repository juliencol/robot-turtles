package occupier;

/**
 * Type de donnee representant une Orientation.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public enum Orientation {
	NORTH ("north"),
	SOUTH ("south"),
	EAST ("east"),
	WEST ("west");

	private String orientation = "";

	/**
	 * D?finit une orientation
	 * @param orientation, l'orientation souhaitee
	 */
	Orientation(String orientation){
		this.orientation = orientation;
	}

	/**
	 * Fournit l'orientation sous forme de cha?ne de caracteres
	 */
	public String toString() {
		return orientation;
	}
}
