package occupier;

/**
 * Type de donnee representant un Obstacle.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Obstacle extends Occupier {
    private ObstacleType obstacleType;

    /**
     * Cr?? un obstacle
     * @param coordinate, represente la position de l'obstacle sur le plateau
     * @param obstacleType, represente le type de l'obstacle
     */
    public Obstacle(Coordinate coordinate,ObstacleType obstacleType) {
        super(coordinate);
        this.obstacleType = obstacleType;
    }

    /**
     * Fournit le type d'obstacle
     * @return le type d'obstacle
     */
    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    /**
     * Fournit le type d'obstacle sous forme de cha?ne de caracteres
     */
    public String toString() {
        return obstacleType.toString();
    }

    public Color getColor() {
        return null;
    }
}

