package occupier;

/**
 * Type de donnee representant une Tortue.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Turtle extends Occupier {
    private Color color;
    private Orientation orientation;
    private Coordinate startingPosition;

    /**
     * Cr?? une tortue selon l'orientation et la couleur donn?e ainsi qu'a la position saisie
     * @param coordinate, position de la tortue sur le plateau
     * @param color, couleur de la tortue
     * @param orientation, orientation de la tortue
     */
    public Turtle(Coordinate coordinate, Color color, Orientation orientation, Coordinate startingPosition) {
        super(coordinate);
        this.color = color;
        this.orientation = orientation;
        this.startingPosition = startingPosition;
    }

    /**
     * Fournit la couleur sous forme de cha?ne de caracteres
     * @return la couleur
     */
    public String getColorString() {
        return color.toString();
    }

    public Color getColor() {
        return color;
    }

    public ObstacleType getObstacleType() {
        return null;
    }

    /**
     * Fournit l'orientation sous forme de cha?ne de caracteres
     * @return l'orientation
     */
    public String getOrientationString() {
        return orientation.toString();
    }

    /**
     * Fournit l'orientation
     * @return l'orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }


    /**
     * Modifie l'orientation de la tortue
     * @param orientation, la nouvelle orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Fournit l'abscisse de la tortue
     * @return l'abscisse
     */
    public int getCoordX(){
        return super.getCoordinate().getX();
    }

    /**
     * Fournit l'ordonne de la tortue
     * @return l'ordonne
     */
    public int getCoordY(){
        return super.getCoordinate().getY();
    }

    /**
     * Modifie l'abscisse de la tortue
     * @param x, type entier representant le nouvel abscisse
     */
    public void setCoordX(int x) {
        super.getCoordinate().setX(x);
    }

    /**
     * Modifie l'ordonn?e de la tortue
     * @param y, type entier representant la nouvelle ordonnee
     */
    public void setCoordY(int y) {
        super.getCoordinate().setY(y);
    }

    /**
     * Fournit la position de depart sur l'axe des abscisses
     * @return l'abscisse de depart
     */
    public int getStartingPositionX() {
        return startingPosition.getX();
    }

    /**
     * Fournit la position de depart sur l'axe des ordonnees
     * @return l'ordonnee de depart
     */

    public int getStartingPositionY() {
        return startingPosition.getY();
    }

    public Coordinate getStartingPosition(){
        return startingPosition;
    }

}
