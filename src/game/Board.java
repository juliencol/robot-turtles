package game;

import java.util.ArrayList;
import java.util.List;
import occupier.Occupier;

import occupier.Coordinate;

/**
 * Type de donnee representant un Plateau.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Board {
	private Case[][] cases;
	private ArrayList<Coordinate> occupiedPositions;

	public final static int LENGTH_HEIGHT = 8;
	public final static int LENGTH_WIDTH = 8;

	/**
	 * Cree un plateau
	 */
	public Board() {
		this.cases = new Case[LENGTH_HEIGHT][LENGTH_WIDTH];
		this.occupiedPositions = new ArrayList<Coordinate>();
	}

	/**
	 * Fournit toutes les cases du plateau
	 * @return la matrice de cases
	 */
	public Case[][] getCases() {
		return cases;
	}

	/**
	 * Fournit la liste des coordonnees occupees
	 * @return la liste des coordonnees occupees
	 */
	public ArrayList<Coordinate> getOccupiedPosition() {
		return occupiedPositions;
	}

	/**
	 * Modifie la liste des coordonnees occup?es
	 * @param possiblePosition, liste des coordonnees occupees
	 */
	public void setOccupiedPosition(ArrayList<Coordinate> occupiedPosition) {
		this.occupiedPositions = occupiedPosition;
	}

	/**
	 * Teste si les coordonnees donn?es sont disponibles ou pas
	 * @param coordinate, un couple de coordonnees x,y
	 * @return true si les coordonn?es saisies sont occupees sinon false
	 */
	public boolean containsInstance(Coordinate coordinate) {
		for (Coordinate occupied : occupiedPositions) {
			if (coordinate.equals(occupied)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Met a jour les positions occupes du plateau
	 */
	public void takenCases(List<Player> players, List<Occupier> occupiers) {
		setOccupiedPosition(new ArrayList<Coordinate>());

		for (Player player : players) {
			getOccupiedPosition().add(new Coordinate(player.getTurtle().getCoordX(),player.getTurtle().getCoordY()));
		}

		for (Occupier occupier : occupiers) {
			getOccupiedPosition().add(new Coordinate(occupier.getCoordinate().getX(), occupier.getCoordinate().getY()));
		}
	}

	/**
	 * Chaque occupant est rentre dans la case qui lui est associe
	 * @param occupiers, liste des occupants sur la plateau
	 * @param players, liste des joueurs
	 */
	public void refreshCases(List<Occupier> occupiers, List<Player> players) {
		cases = new Case[LENGTH_HEIGHT][LENGTH_WIDTH];
		for (Player player : players) {
			cases[player.getTurtle().getCoordX()][player.getTurtle().getCoordY()] = new Case(player.getTurtle());
		}
		for (Occupier occupier : occupiers) {
			cases[occupier.getCoordinate().getX()][occupier.getCoordinate().getY()] = new Case(occupier);
		}
	}
}
