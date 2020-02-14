package deck;

import occupier.ObstacleType;

/**
 * Type de donnée représentant une Carte obstacle.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class ObstacleCard {
	private ObstacleType obstacleType;
	
	/**
	 * Créé l'obstacle dont le type n'est pas défini
	 */
	public ObstacleCard() {
		
	}
	
	/**
	 * Créé l'obstacle définit par le type donné
	 * @param obstacleType, représente le type d'obstacle
	 */
	public ObstacleCard(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }
	
	/**
	 * Fournit le type d'obstacle sous forme de chaîne de caractères
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
