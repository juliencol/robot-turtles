package occupier;

/**
 * Type de donn?e representant une Joyau.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Jewel extends Occupier {
    private Color color;
    private int neighbour;

    /**
     * Cree un joyau
     * @param coordinate, represente la position du joyau sur le plateau
     * @param color, represente la couleur du joyau
     */
    public Jewel(Coordinate coordinate, Color color) {
        super(coordinate);
        this.color = color;
        this.neighbour = 0;
    }

    /**
     * Fournit la couleur du joyau
     * @return la couleur
     */
    public Color getColor() {
        return color;
    }

    /**
     * Fournit la couleur du joyau sous forme de cha?ne de caracteres
     */
    public String toString() {
        return color.toString()+"_jewel";
    }

    /**
     * Fournit le type d'obstacle
     */
    public ObstacleType getObstacleType() {
        return null;
    }

    /**
     * Fournit le nombre d'occupant au contact
     * @return le nombre d'occupant au contact
     */
	public int getNeighbour() {
		return neighbour;
	}

	/**
	 * Incrémente le nombre d'occupant au contact
	 */
	public void addNeighbour() {
		this.neighbour++;
	}

}

