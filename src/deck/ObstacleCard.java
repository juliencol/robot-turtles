package deck;

import occupier.ObstacleType;

/**
 * Type de donn�e repr�sentant une Carte obstacle.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class ObstacleCard {
	private ObstacleType obstacleType;
	
	/**
	 * Cr�� l'obstacle dont le type n'est pas d�fini
	 */
	public ObstacleCard() {
		
	}
	
	/**
	 * Cr�� l'obstacle d�finit par le type donn�
	 * @param obstacleType, repr�sente le type d'obstacle
	 */
	public ObstacleCard(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }
	
	/**
	 * Fournit le type d'obstacle sous forme de cha�ne de caract�res
	 */
	public String toString() {
		return obstacleType.toString();
	}

	/**
	 * Fournit le type d'obstacle
	 * @return le type d'obstacle
	 */
	public ObstacleType getObstacleType() {
		return obstacleType;
	}
}
