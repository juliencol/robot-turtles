package game;

import occupier.Occupier;

/**
 * Type de donnee representant une Case.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Case {
	private Occupier occupier;
	
	/**
	 * Cree une case contenant un occupant
	 * @param occupier, occupant contenu dans la case
	 */
	public Case(Occupier occupier) {
		this.occupier = occupier;
	}
	
	/**
	 * Fournit l'occupant
	 * @return l'occupant
	 */
	public Occupier getOccupier() {
		return occupier;
	}

	/**
	 * Modifie l'occupant se trouvant dans la case par celui sp�cifi�
	 * @param occupier, le nouvelle occupant
	 */
	public void setOccupier(Occupier occupier) {
		this.occupier = occupier;
	}
	
	
}
