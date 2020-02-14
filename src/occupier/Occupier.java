package occupier;

/**
 * Type de donnee representant un Occupant.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public abstract class Occupier implements iOccupier{
	private Coordinate coordinate;

	/**
	 * Cree un occupant a la position donnee
	 * @param coordinate, la position de l'occupant sur le plateau
	 */
	public Occupier(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Fournit la position de l'occupant sur le plateau
	 * @return la position de l'occupant sur le plateau
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * Modifie la position de l'occupant
	 * @param coordinate, la nouvelle position de l'occupant
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

}
