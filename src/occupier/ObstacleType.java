package occupier;

/**
 * Type de donnee representant un Obstacle.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public enum ObstacleType {
    ICEWALL("ice_wall"), 
    STONEWALL("stone_wall"), 
    WOODENBOX("wooden_box");
	
	private String obstacleType = "";
	
	/**
	 * D?finit le type d'obstacle donne
	 * @param obstacleType, le type d'obstacle
	 */
	ObstacleType(String obstacleType){
		this.obstacleType = obstacleType;
	}
	
	/**
	 * Fournit le type d'obstacle sous forme de cha?ne de caracteres
	 */
	public String toString() {
		return obstacleType;
	}
}
