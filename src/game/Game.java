package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import interface_LWJGL.Input;
import interface_LWJGL.Window;

import occupier.Color;
import occupier.Coordinate;
import occupier.Jewel;
import occupier.ObstacleType;
import occupier.Occupier;
import occupier.Orientation;
import occupier.Turtle;
import occupier.Obstacle;

import deck.ObstacleCard;

/**
 * Type de donnee representant un Jeu.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Game {
	private Board board;
	private List<Player> players;
	private List<Occupier> occupiers;
	private int turn;
	private boolean victory = false;
	private Window window;
	private String phase;
	private int whichPlayer;

	/**
	 * Cree et initialise un jeu ainsi que le nombre de joueur donne
	 * @param window, type window qui represente la fenetre graphique
	 * @param nbPlayers, type entier qui represente le nombre de joueur dans la partie
	 */
	public Game (Window window, int nbPlayers) {
		board = new Board();
		players = new ArrayList<Player>();
		occupiers = new ArrayList<Occupier>();
		turn = 0;
		this.window = window;
		phase = "";
		whichPlayer = 0;
		initPlayers(nbPlayers);
	}

	/**
	 *
	 */
	public void launchGame(){
		if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE) || window.shouldClose()) {
			window.destroy();
		}
		
		else if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
			window.setFullScreen(!window.isFullScreen());
			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();
		}

		else if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (17.5*window.getHeight()/20) && Input.getMouseY() <= (19.5*window.getHeight()/20))) {

			if(Input.getMouseX() >= (0.5*window.getWidth()/20) && Input.getMouseX() <= (3.2*window.getWidth()/20)) {
				completeProgram(whichPlayer);
			}

			else if(Input.getMouseX() >= (4.2*window.getWidth()/20) && Input.getMouseX() <= (6.9*window.getWidth()/20)) {
				buildWall(whichPlayer);
			}

			else if(Input.getMouseX() >= (7.9*window.getWidth()/20) && Input.getMouseX() <= (10.6*window.getWidth()/20)) {
				executeProgram(whichPlayer);
			}

		}
		
		if(victory) {
			return;
		}
		window.waitEvents();

	}
	
	/**
	 * Fournit si un joueur a gagne ou non
	 * @return true si un joueur a gagne sinon false
	 */
	public boolean isVictory() {
		return victory;
	}


	/**
	 * Initialise la tortue, le deck et la main de chaque joueur
	 * @param nbPlayers, type entier qui represente le nombre de joueur dans la partie
	 */
	private void initPlayers(int nbPlayers) {

		switch (nbPlayers) {
			case 2:

				players.add(new Player(new Turtle(new Coordinate(1,0), Color.RED, Orientation.SOUTH, new Coordinate(1,0))));
				players.add(new Player(new Turtle(new Coordinate(5,0), Color.BLUE, Orientation.SOUTH, new Coordinate(5,0))));
				occupiers.add(new Jewel(new Coordinate(3,7), Color.GREEN));
				
				for (int i = 0; i < 8 ; i++) {
					occupiers.add(new Obstacle(new Coordinate(7,i), ObstacleType.STONEWALL));
				}
				
				board.takenCases(players,occupiers);
				board.refreshCases(occupiers, players);
				
				phase = "player1";
				players.get(0).drawHand();
				window.placeGame(players, occupiers, whichPlayer, phase);
				break;

			case 3:

				players.add(new Player(new Turtle(new Coordinate(0,0), Color.RED, Orientation.SOUTH, new Coordinate(0,0))));
				players.add(new Player(new Turtle(new Coordinate(3,0), Color.BLUE, Orientation.SOUTH, new Coordinate(3,0))));
				players.add(new Player(new Turtle(new Coordinate(6,0), Color.PINK, Orientation.SOUTH, new Coordinate(6,0))));
				occupiers.add(new Jewel(new Coordinate(0,7), Color.PINK));
				occupiers.add(new Jewel(new Coordinate(3,7), Color.GREEN));
				occupiers.add(new Jewel(new Coordinate(6,7), Color.BLUE));
				
				board.takenCases(players,occupiers);
				board.refreshCases(occupiers, players);

				phase = "player1";
				players.get(0).drawHand();
				window.placeGame(players, occupiers, whichPlayer, phase);
				break;

			case 4:

				players.add(new Player(new Turtle(new Coordinate(0,0), Color.RED, Orientation.SOUTH, new Coordinate(0,0))));
				players.add(new Player(new Turtle(new Coordinate(2,0), Color.BLUE, Orientation.SOUTH, new Coordinate(2,0))));
				players.add(new Player(new Turtle(new Coordinate(5,0), Color.PINK, Orientation.SOUTH, new Coordinate(5,0))));
				players.add(new Player(new Turtle(new Coordinate(7,0), Color.GREEN, Orientation.SOUTH, new Coordinate(7,0))));
				occupiers.add(new Jewel(new Coordinate(1,7), Color.PINK));
				occupiers.add(new Jewel(new Coordinate(6,7), Color.BLUE));

				board.takenCases(players,occupiers);
				board.refreshCases(occupiers, players);
				
				phase = "player1";
				players.get(0).drawHand();
				window.placeGame(players, occupiers, whichPlayer, phase);
				break;
		}
	}

	/**
	 * Incremente le nombre de tour et passe la main au joueur suivant
	 */
	private void endTurn() {
		if(victory) {
			phase = "victory" + (whichPlayer + 1);
		}
		else {
			turn++;
			whichPlayer = turn % players.size();
			phase = "player" + (whichPlayer + 1);
			players.get(whichPlayer).drawHand();
		}
		
		window.placeGame(players, occupiers, whichPlayer, phase);
		window.swapBuffers();
		board.takenCases(players,occupiers);
		board.refreshCases(occupiers, players);

	}
	
	/** Cherche si un chemin est disponible d'une case XY au joyau
	 * @param start position de départ du chemin
	 */
	private boolean findPath(Coordinate start){
		List<Integer> toVisitCoordinatesX = new ArrayList<>();
		List<Integer> toVisitCoordinatesY = new ArrayList<>();
		int currentCoordinateX = start.getX();
		int currentCoordinateY = start.getY();
		toVisitCoordinatesX.add(currentCoordinateX);
		toVisitCoordinatesY.add(currentCoordinateY);
		
		// Tableau des coordonnes a visiter, si des coordonnees sont a visiter, la case correspondante prend la valeur true, false autrement
		boolean[][] toVisitCoordinates = new boolean[8][8];
		for ( int i = 0 ; i < toVisitCoordinates.length ; i++ )
			for ( int j = 0 ; j < toVisitCoordinates[i].length ; j++ ){
				toVisitCoordinates[i][j] = false;
			}

		do {
			toVisitCoordinatesX.remove(0);
			toVisitCoordinatesY.remove(0);
			// On ajoute a la liste des coordonnees a visiter, les coordonnees adjacentes non occupees par des murs de pierre
			if (currentCoordinateX+1 <= 7){
				if (!toVisitCoordinates[currentCoordinateX+1][currentCoordinateY]){
					if (board.containsInstance(new Coordinate(currentCoordinateX+1, currentCoordinateY))){ 
						// Verifie si la case est occupee
						if (board.getCases()[currentCoordinateX+1][currentCoordinateY].getOccupier() instanceof occupier.Obstacle){ 
							// Verifie si l'obstacle est un mur
							if (board.getCases()[currentCoordinateX+1][currentCoordinateY].getOccupier().getObstacleType().toString().equals("ice_wall")){
								// On ajoute les coordonnees aux coordonnees a visiter
								toVisitCoordinatesX.add(currentCoordinateX+1);
								toVisitCoordinatesY.add(currentCoordinateY);
								toVisitCoordinates[currentCoordinateX+1][currentCoordinateY] = true;
							}
						} else {
							toVisitCoordinatesX.add(currentCoordinateX+1);
							toVisitCoordinatesY.add(currentCoordinateY);
							toVisitCoordinates[currentCoordinateX+1][currentCoordinateY] = true;
						}
					} else {
						toVisitCoordinatesX.add(currentCoordinateX+1);
						toVisitCoordinatesY.add(currentCoordinateY);
						toVisitCoordinates[currentCoordinateX+1][currentCoordinateY] = true;
					}
				}	
			}

			if (currentCoordinateX-1 >= 0){
				if (!toVisitCoordinates[currentCoordinateX-1][currentCoordinateY]){
					if (board.containsInstance(new Coordinate(currentCoordinateX-1, currentCoordinateY))){
						if (board.getCases()[currentCoordinateX-1][currentCoordinateY].getOccupier() instanceof occupier.Obstacle){
							if (board.getCases()[currentCoordinateX-1][currentCoordinateY].getOccupier().getObstacleType().toString().equals("ice_wall")){
								toVisitCoordinatesX.add(currentCoordinateX-1);
								toVisitCoordinatesY.add(currentCoordinateY);
								toVisitCoordinates[currentCoordinateX-1][currentCoordinateY] = true;
							}
						} else {
							toVisitCoordinatesX.add(currentCoordinateX-1);
							toVisitCoordinatesY.add(currentCoordinateY);
							toVisitCoordinates[currentCoordinateX-1][currentCoordinateY] = true;
						}
					} else {
						toVisitCoordinatesX.add(currentCoordinateX-1);
						toVisitCoordinatesY.add(currentCoordinateY);
						toVisitCoordinates[currentCoordinateX-1][currentCoordinateY] = true;
					}
				}
			}

			if (currentCoordinateY+1 <= 7){
				if (!toVisitCoordinates[currentCoordinateX][currentCoordinateY+1]){
					if (board.containsInstance(new Coordinate(currentCoordinateX, currentCoordinateY+1))){
						if (board.getCases()[currentCoordinateX][currentCoordinateY+1].getOccupier() instanceof occupier.Obstacle){
							if (board.getCases()[currentCoordinateX][currentCoordinateY+1].getOccupier().getObstacleType().toString().equals("ice_wall")){
								toVisitCoordinatesX.add(currentCoordinateX);
								toVisitCoordinatesY.add(currentCoordinateY+1);
								toVisitCoordinates[currentCoordinateX][currentCoordinateY+1] = true;
							}
						} else {
							toVisitCoordinatesX.add(currentCoordinateX);
							toVisitCoordinatesY.add(currentCoordinateY+1);
							toVisitCoordinates[currentCoordinateX][currentCoordinateY+1] = true;
						}
					} else {
						toVisitCoordinatesX.add(currentCoordinateX);
						toVisitCoordinatesY.add(currentCoordinateY+1);
						toVisitCoordinates[currentCoordinateX][currentCoordinateY+1] = true;
					}
				}
			}
			
			if (currentCoordinateY-1 >= 0){
				if (!toVisitCoordinates[currentCoordinateX][currentCoordinateY-1]){
					System.out.println(board.containsInstance(new Coordinate(currentCoordinateX, currentCoordinateY-1)));
					if (board.containsInstance(new Coordinate(currentCoordinateX, currentCoordinateY-1))){
						if (board.getCases()[currentCoordinateX][currentCoordinateY-1].getOccupier() instanceof occupier.Obstacle){
							if (board.getCases()[currentCoordinateX][currentCoordinateY-1].getOccupier().getObstacleType().toString().equals("ice_wall")){
								toVisitCoordinatesX.add(currentCoordinateX);
								toVisitCoordinatesY.add(currentCoordinateY-1);
								toVisitCoordinates[currentCoordinateX][currentCoordinateY-1] = true;										
							}
						} else {
							toVisitCoordinatesX.add(currentCoordinateX);
							toVisitCoordinatesY.add(currentCoordinateY-1);
							toVisitCoordinates[currentCoordinateX][currentCoordinateY-1] = true;
						}
					} else {
						toVisitCoordinatesX.add(currentCoordinateX);
						toVisitCoordinatesY.add(currentCoordinateY-1);
						toVisitCoordinates[currentCoordinateX][currentCoordinateY-1] = true;
					}
				}
			}

			try{
				// On selectionne un mur a visiter dans toVisitcases
				currentCoordinateX = toVisitCoordinatesX.get(0);
				currentCoordinateY = toVisitCoordinatesY.get(0);
				// On verifie si un joyau appartient a la liste des coordonnees a visiter
				switch (players.size()){
					case 2 :
						if (toVisitCoordinates[3][7]) return true;
						break;
					case 3 :
						if (toVisitCoordinates[0][7] ||
							toVisitCoordinates[3][7] ||
							toVisitCoordinates[6][7] ) return true;
						break;
					case 4 :
						if (toVisitCoordinates[1][7] ||
							toVisitCoordinates[6][7]) return true;
						break;
				}
			} catch (Exception e){}
			
		} while(!toVisitCoordinatesX.isEmpty());
		return false;
	}

	private boolean isItBuildable(){
		for (Player player : players){
			if (!findPath(player.getTurtle().getCoordinate())) return false;
			if (!findPath(player.getTurtle().getStartingPosition())) return false;
		}
		return true;
	}

	/**
	 * Permet au joueur de completer son programme avec les cartes qu'il a en main
	 * @param whichPlayer, type entier qui represente le joueur courant
	 */
	private void completeProgram(int whichPlayer) {
		boolean fill = true;
		phase = "choose_cards";
		window.placeGame(players, occupiers, whichPlayer, phase);	// On indique qu'on passe a la completion du programme et affiche le bouton pour passer a la phase suivante
		window.swapBuffers();

		while (fill) {

			if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
				window.destroy();
			}

			else if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
				window.setFullScreen(!window.isFullScreen());
				window.placeGame(players, occupiers, whichPlayer, phase);
				window.swapBuffers();
			}

			if(players.get(whichPlayer).getHand().getCards().size() == 0) {

				for (int i =0; i < players.get(whichPlayer).getCurrentProgram().size();i++) {
					players.get(whichPlayer).getFullProgram().add(players.get(whichPlayer).getCurrentProgram().get(i));
				}

				players.get(whichPlayer).getCurrentProgram().clear();;
				fill = false;
			}

			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (17.5*window.getHeight()/20) && Input.getMouseY() <= (19.5*window.getHeight()/20))) {

				if(Input.getMouseX() >= (3.5*window.getWidth()/20) && Input.getMouseX() <= (9*window.getWidth()/20)) {

					for (int i =0; i < players.get(whichPlayer).getCurrentProgram().size();i++) {
						players.get(whichPlayer).getFullProgram().add(players.get(whichPlayer).getCurrentProgram().get(i));
					}
					players.get(whichPlayer).getCurrentProgram().clear();
					fill = false;
				}
			}

			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (10*window.getHeight()/20) && Input.getMouseY() <= (14.5*window.getHeight()/20))) {

				if(Input.getMouseX() >= (13*window.getWidth()/20) && Input.getMouseX() <= (15*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getCurrentProgram().add(players.get(whichPlayer).getHand().getCards().get(0));
						players.get(whichPlayer).getHand().getCards().remove(0);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (16*window.getWidth()/20) && Input.getMouseX() <= (19*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getCurrentProgram().add(players.get(whichPlayer).getHand().getCards().get(1));
						players.get(whichPlayer).getHand().getCards().remove(1);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

			}

			else if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (15*window.getHeight()/20) && Input.getMouseY() <= (19.5*window.getHeight()/20))) {

				if(Input.getMouseX() >= (11.5*window.getWidth()/20) && Input.getMouseX() <= (13.5*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getCurrentProgram().add(players.get(whichPlayer).getHand().getCards().get(2));
						players.get(whichPlayer).getHand().getCards().remove(2);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (14.5*window.getWidth()/20) && Input.getMouseX() <= (16.5*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getCurrentProgram().add(players.get(whichPlayer).getHand().getCards().get(3));
						players.get(whichPlayer).getHand().getCards().remove(3);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (17.5*window.getWidth()/20) && Input.getMouseX() <= (19.5*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getCurrentProgram().add(players.get(whichPlayer).getHand().getCards().get(4));
						players.get(whichPlayer).getHand().getCards().remove(4);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}

				}
			}

			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (2*window.getHeight()/20) && Input.getMouseY() <= (5*window.getHeight()/20))) {

				if(Input.getMouseX() >= (11.55*window.getWidth()/20) && Input.getMouseX() <= (13.05*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getHand().getCards().add(players.get(whichPlayer).getCurrentProgram().get(0));
						players.get(whichPlayer).getCurrentProgram().remove(0);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (13.15*window.getWidth()/20) && Input.getMouseX() <= (14.65*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getHand().getCards().add(players.get(whichPlayer).getCurrentProgram().get(1));
						players.get(whichPlayer).getCurrentProgram().remove(1);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (14.75*window.getWidth()/20) && Input.getMouseX() <= (16.25*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getHand().getCards().add(players.get(whichPlayer).getCurrentProgram().get(2));
						players.get(whichPlayer).getCurrentProgram().remove(2);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (16.35*window.getWidth()/20) && Input.getMouseX() <= (17.85*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getHand().getCards().add(players.get(whichPlayer).getCurrentProgram().get(3));
						players.get(whichPlayer).getCurrentProgram().remove(3);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}

				else if(Input.getMouseX() >= (17.95*window.getWidth()/20) && Input.getMouseX() <= (19.45*window.getWidth()/20)) {

					try {
						players.get(whichPlayer).getHand().getCards().add(players.get(whichPlayer).getCurrentProgram().get(4));
						players.get(whichPlayer).getCurrentProgram().remove(4);
						window.placeGame(players, occupiers, whichPlayer, phase);
						window.swapBuffers();
					}

					catch (Exception e) {
					}
				}
			}

			window.waitEvents();
		}

		if (players.get(whichPlayer).getHand().getCards().isEmpty()){
			players.get(whichPlayer).drawHand();
			endTurn();
		}

		else {
			discardCards(whichPlayer);
		}
	}

	/**
	 * Permet au joueur de defausser sa main a la fin et de repiocher 5 cartes ou non
	 * @param whichPlayer, type entier qui represente le joueur courant
	 */
	private void discardCards(int whichPlayer) {
		boolean discard = true;
		phase = "discard";
		window.placeGame(players, occupiers, whichPlayer, phase);
		window.swapBuffers();

		while(discard) {

			if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
				window.destroy();
			}

			else if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
				window.setFullScreen(!window.isFullScreen());
				window.placeGame(players, occupiers, whichPlayer, phase);
				window.swapBuffers();
			}

			if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (17.5*window.getHeight()/20) && Input.getMouseY() <= (19.5*window.getHeight()/20))) {

				if(Input.getMouseX() >= (2*window.getWidth()/20) && Input.getMouseX() <= (5*window.getWidth()/20)) {

					for (int i = 0; i < players.get(whichPlayer).getHand().getCards().size(); i++) {
						players.get(whichPlayer).getDiscardedCards().add(players.get(whichPlayer).getHand().getCards().get(i));
					}

					players.get(whichPlayer).getHand().getCards().clear();
					discard = false;
					endTurn();

				} else if(Input.getMouseX() >= (6*window.getWidth()/20) && Input.getMouseX() <= (9*window.getWidth()/20)) {
					discard = false;
					endTurn();
				}
			}

			window.waitEvents();
		}
	}

	/**
	 * Permet au joueur de placer un obstacle sur le plateau
	 * @param whichPlayer, type entier qui represente le joueur courant
	 */
	private void buildWall(int whichPlayer) {
		if(players.get(whichPlayer).getObstacles().isEmpty()) {
			phase = "empty_player_wall";
			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();
		} else {

			boolean build = true;
			ObstacleCard selectedWall = new ObstacleCard();
			int selectedWallIndex = -1;

			phase = "choose_wall";
			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();
			
			while(build){
				if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
					window.destroy();
				}

				else if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
					window.setFullScreen(!window.isFullScreen());
					window.placeGame(players, occupiers, whichPlayer, phase);
					window.swapBuffers();
				}

				if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (10*window.getHeight()/20) && Input.getMouseY() <= (13.5*window.getHeight()/20))) {

					if(Input.getMouseX() >= (13*window.getWidth()/20) && Input.getMouseX() <= (15*window.getWidth()/20)) {

						try {
							phase = "place_wall";
							selectedWall = players.get(whichPlayer).getObstacles().get(0);
							selectedWallIndex = 0;	
							window.placeGame(players, occupiers, whichPlayer, phase);
							window.placeSelectedWall(selectedWall.toString());
							window.swapBuffers();
						}

						catch (Exception e) {
						}
					}

					else if(Input.getMouseX() >= (16*window.getWidth()/20) && Input.getMouseX() <= (18*window.getWidth()/20)) {

						try {
							phase = "place_wall";
							selectedWall = players.get(whichPlayer).getObstacles().get(1);
							selectedWallIndex = 1;
							window.placeGame(players, occupiers, whichPlayer, phase);
							window.placeSelectedWall(selectedWall.toString());
							window.swapBuffers();
						}

						catch (Exception e) {
						}
					}


				}

				else if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= (15*window.getHeight()/20) && Input.getMouseY() <= (18.5*window.getHeight()/20))) {

					if(Input.getMouseX() >= (11.5*window.getWidth()/20) && Input.getMouseX() <= (13.5*window.getWidth()/20)) {

						try {
							phase = "place_wall";
							selectedWall = players.get(whichPlayer).getObstacles().get(2);
							selectedWallIndex = 2;	
							window.placeGame(players, occupiers, whichPlayer, phase);
							window.placeSelectedWall(selectedWall.toString());
							window.swapBuffers();
						}

						catch (Exception e) {
						}
					}

					else if(Input.getMouseX() >= (14.5*window.getWidth()/20) && Input.getMouseX() <= (16.5*window.getWidth()/20)) {

						try {
							phase = "place_wall";
							selectedWall = players.get(whichPlayer).getObstacles().get(3);
							selectedWallIndex = 3;				
							window.placeGame(players, occupiers, whichPlayer, phase);
							window.placeSelectedWall(selectedWall.toString());
							window.swapBuffers();
						}

						catch (Exception e) {
						}
					}

					else if(Input.getMouseX() >= (17.5*window.getWidth()/20) && Input.getMouseX() <= (19.5*window.getWidth()/20)) {

						try {
							phase = "place_wall";
							selectedWall = players.get(whichPlayer).getObstacles().get(4);
							selectedWallIndex = 4;			
							window.placeGame(players, occupiers, whichPlayer, phase);
							window.placeSelectedWall(selectedWall.toString());
							window.swapBuffers();
						}

						catch (Exception e) {
						}
					}
				}

				if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseY() >= 0 && Input.getMouseY() <= (17*window.getHeight()/20))) {

					if(Input.getMouseX() >= 0 && Input.getMouseX() <= (11*window.getWidth()/20)) {
						int clickedX = (int)(Input.getMouseX()/87.744);
						int clickedY = (int)(Input.getMouseY()/80.56);

						if((clickedX <= 7 && clickedX >= 0) && (clickedY <=7 && clickedY >= 0)){
						
							if (selectedWall.getObstacleType() != null && selectedWallIndex != -1) {
								
								if (!board.containsInstance(new Coordinate(clickedX, clickedY))){
								
									if (selectedWall.toString().equals("ice_wall")) {
										occupiers.add(new Obstacle(new Coordinate(clickedX,clickedY), ObstacleType.ICEWALL));
										players.get(whichPlayer).getObstacles().remove(selectedWallIndex);
										build = false;
										
										window.placeGame(players, occupiers, whichPlayer, phase);
										window.swapBuffers();
									} 
									
									else if (selectedWall.toString().equals("stone_wall")){
										/*On place le mur
										** On verifie que la construction du mur n'obstrue pas le chemin
										** S'il obstrue le chemin, on le retire
										*/ 
										occupiers.add(new Obstacle(new Coordinate(clickedX,clickedY), ObstacleType.STONEWALL));
										board.takenCases(players,occupiers);
										board.refreshCases(occupiers, players);
										if (isItBuildable()){
											players.get(whichPlayer).getObstacles().remove(selectedWallIndex);
											build = false;
											window.placeGame(players, occupiers, whichPlayer, phase);
											window.swapBuffers();
										} else {
											occupiers.remove(occupiers.size()-1);
											board.takenCases(players,occupiers);
											board.refreshCases(occupiers, players);
										}
									}
								}
							}
						}
					}
				}

				window.waitEvents();
				
			}
			discardCards(whichPlayer);
		}
	}


	/**
	 * Execute le programme de cartes du joueur courant
	 * @param whichPlayer, type entier qui represente le joueur courant
	 */
	private void executeProgram(int whichPlayer) {
		int x;
		int y;
		
		if(players.get(whichPlayer).getFullProgram().isEmpty()) {
			phase = "empty_player_program";
			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();
		}

		else {
			phase = "in_progress";
			ArrayList<Orientation> directions = new ArrayList<>(Arrays.asList(Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST));
			int facedDirection = directions.indexOf(players.get(whichPlayer).getTurtle().getOrientation());

			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();

			while (! players.get(whichPlayer).getFullProgram().isEmpty()) {
				x = players.get(whichPlayer).getTurtle().getCoordX();
				y = players.get(whichPlayer).getTurtle().getCoordY();

				switch (players.get(whichPlayer).getFullProgram().get(0).getColor()) {

					case "purple" :
						facedDirection = (facedDirection + 1) % 4;
						players.get(whichPlayer).getTurtle().setOrientation(directions.get(facedDirection));
						break;

					case "yellow" :
						facedDirection = (facedDirection + 3) % 4;
						players.get(whichPlayer).getTurtle().setOrientation(directions.get(facedDirection));
						break;

					case "blue" :
						if (facedDirection == 0) {
							if (players.get(whichPlayer).getTurtle().getCoordY() - 1 < 0) {
								backToStart(whichPlayer);
								facedDirection = 2;
							} else {
								facedDirection = obstacleEncountered(x, y-1, directions, facedDirection, whichPlayer);
							}

						}

						else if (facedDirection == 1) {
							if (players.get(whichPlayer).getTurtle().getCoordX() + 1 > 7) {
								backToStart(whichPlayer);
								facedDirection = 2;
							} else {
								facedDirection = obstacleEncountered(x+1, y, directions, facedDirection, whichPlayer);
							}

						}

						else if (facedDirection == 2) {
							if (players.get(whichPlayer).getTurtle().getCoordY() + 1 > 7) {
								backToStart(whichPlayer);
								facedDirection = 2;
							} else {
								facedDirection = obstacleEncountered(x, y+1, directions, facedDirection, whichPlayer);
							}

						}

						else if (facedDirection == 3) {
							if (players.get(whichPlayer).getTurtle().getCoordX() - 1 < 0) {
								backToStart(whichPlayer);
								facedDirection = 2;
							} else {
								facedDirection = obstacleEncountered(x-1, y, directions, facedDirection, whichPlayer);
							}

						}
						break;

					case "laser" :
						fireLaser(whichPlayer);
						break;

					}

				players.get(whichPlayer).getFullProgram().remove(0);
				board.takenCases(players,occupiers);
				window.placeGame(players, occupiers, whichPlayer, phase);
				window.swapBuffers();

				if (facedDirection == -1) break;
			}

			phase = "end_program";
			window.placeGame(players, occupiers, whichPlayer, phase);
			window.swapBuffers();
			endTurn();
		}
	}

	/**
	 * Tire le laser selon l'orientation de la tortue
	 * @param whichPlayer, le joueur courant
	 */
	private void fireLaser(int whichPlayer) {
		int x = players.get(whichPlayer).getTurtle().getCoordX();
		int y = players.get(whichPlayer).getTurtle().getCoordY();

		switch (players.get(whichPlayer).getTurtle().getOrientationString()) {
			case "north" :
				for (int i = (y - 1) ; i >= 0 ; i--) {
					if (targetHit(x,i) == true) break;
				}
				break;
			case "east" :
				for (int i = (x + 1) ; i <= 7 ; i++) {
					if (targetHit(i,y) == true) break;
				}
				break;
			case "south" :
				for (int i = (y + 1) ; i <= 7 ; i++) {
					if (targetHit(x,i) == true) break;
				}
				break;
			case "west" :
				for (int i = (x - 1) ; i >= 0 ; i--) {
					if (targetHit(i,y) == true) break;
				}
				break;
		}
	}

	/**
	 * Renvoie la tortue a sa position de d?part s'il sort du plateau
	 * @param whichPlayer, type entier qui represente le joueur courant
	 */
	private void backToStart(int whichPlayer) {
		if (board.containsInstance(players.get(whichPlayer).getTurtle().getStartingPosition())){
			if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier() instanceof occupier.Turtle) {
				if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier().getColor().toString() == "red") {
					players.get(0).getTurtle().setCoordX(players.get(0).getTurtle().getStartingPositionX());
					players.get(0).getTurtle().setCoordY(players.get(0).getTurtle().getStartingPositionY());
					players.get(0).getTurtle().setOrientation(Orientation.SOUTH);
				} else if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier().getColor().toString() == "blue"){
					players.get(1).getTurtle().setCoordX(players.get(1).getTurtle().getStartingPositionX());
					players.get(1).getTurtle().setCoordY(players.get(1).getTurtle().getStartingPositionY());
					players.get(1).getTurtle().setOrientation(Orientation.SOUTH);
				} else if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier().getColor().toString() == "pink") {
					players.get(2).getTurtle().setCoordX(players.get(2).getTurtle().getStartingPositionX());
					players.get(2).getTurtle().setCoordY(players.get(2).getTurtle().getStartingPositionY());
					players.get(2).getTurtle().setOrientation(Orientation.SOUTH);
				} else if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier().getColor().toString() == "green") {
					players.get(3).getTurtle().setCoordX(players.get(3).getTurtle().getStartingPositionX());
					players.get(3).getTurtle().setCoordY(players.get(3).getTurtle().getStartingPositionY());
					players.get(3).getTurtle().setOrientation(Orientation.SOUTH);
				}
			} else if (board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier() instanceof occupier.Obstacle) {
				occupiers.remove(board.getCases()[players.get(whichPlayer).getTurtle().getStartingPositionX()][players.get(whichPlayer).getTurtle().getStartingPositionY()].getOccupier());
			}
		}

		players.get(whichPlayer).getTurtle().setCoordX(players.get(whichPlayer).getTurtle().getStartingPositionX());
		players.get(whichPlayer).getTurtle().setCoordY(players.get(whichPlayer).getTurtle().getStartingPositionY());
		players.get(whichPlayer).getTurtle().setOrientation(Orientation.SOUTH);
		window.placeGame(players, occupiers, whichPlayer, phase);
		window.swapBuffers();
	}

	/**
	 * Vï¿½rifie si le laser touche un obstacle et change son effet selon l'obstacle
	 * @param x, l'abscisse
	 * @param y, l'ordonnï¿½e
	 * @return true si le laser touche quelque chose sinon false
	 */
	private boolean targetHit(int x, int y) {
		if (board.containsInstance(new Coordinate(x,y))){
			if (board.getCases()[x][y].getOccupier() instanceof occupier.Turtle) {

				if (board.getCases()[x][y].getOccupier().getColor().toString() == "red") {
					backToStart(0);
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "blue") {
					backToStart(1);
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "pink") {
					backToStart(2);
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "green") {
					backToStart(3);
				}
				return true;

			} else if (board.getCases()[x][y].getOccupier() instanceof occupier.Obstacle) {

				if (board.getCases()[x][y].getOccupier().getObstacleType().toString() == "ice_wall") {
					occupiers.remove(board.getCases()[x][y].getOccupier());
				}
				return true;

			} else if (board.getCases()[x][y].getOccupier() instanceof occupier.Jewel) {
				backToStart(whichPlayer);
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifie les collisions avec les differents obstacles possible et applique l'effet selon l'obstacle rencontre
	 * @param x, l'abscisse
	 * @param y, l'ordonnee
	 * @param directions, liste des differentes orientation
	 * @param facedDirection, l'indice de l'orientation de la tortue dans la liste
	 * @param whichPlayer, le joueur courant
	 */

	public int obstacleEncountered(int x, int y, List<Orientation> directions, int facedDirection, int whichPlayer) {
		if (board.containsInstance(new Coordinate(x,y))){
			if (board.getCases()[x][y].getOccupier() instanceof occupier.Turtle) {
				// La tortue du joueur actuel se retourne
				facedDirection = (facedDirection + 2) % 4;
				players.get(whichPlayer).getTurtle().setOrientation(directions.get(facedDirection));
				
				// La tortue du joueur rencontrï¿½ se retourne aussi
				if (board.getCases()[x][y].getOccupier().getColor().toString() == "red") {
					
					int otherFacedDirection = directions.indexOf(players.get(0).getTurtle().getOrientation());
					otherFacedDirection = (otherFacedDirection + 2) % 4;
					players.get(0).getTurtle().setOrientation(directions.get(otherFacedDirection));
					
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "blue") {
					
					int otherFacedDirection = directions.indexOf(players.get(1).getTurtle().getOrientation());
					otherFacedDirection = (otherFacedDirection + 2) % 4;
					players.get(1).getTurtle().setOrientation(directions.get(otherFacedDirection));
					
					
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "pink") {
					
					int otherFacedDirection = directions.indexOf(players.get(2).getTurtle().getOrientation());
					otherFacedDirection = (otherFacedDirection + 2) % 4;
					players.get(2).getTurtle().setOrientation(directions.get(otherFacedDirection));
					
				} else if (board.getCases()[x][y].getOccupier().getColor().toString() == "green") {
					
					int otherFacedDirection = directions.indexOf(players.get(3).getTurtle().getOrientation());
					otherFacedDirection = (otherFacedDirection + 2) % 4;
					players.get(3).getTurtle().setOrientation(directions.get(otherFacedDirection));
					
				}

			} else if (board.getCases()[x][y].getOccupier() instanceof occupier.Obstacle) {

				facedDirection = (facedDirection + 2) % 4;
				players.get(whichPlayer).getTurtle().setOrientation(directions.get(facedDirection));

			} else if (board.getCases()[x][y].getOccupier() instanceof occupier.Jewel) {
				occupiers.remove(board.getCases()[x][y].getOccupier());
				players.get(whichPlayer).getTurtle().setCoordinate(new Coordinate(x,y));
				facedDirection = -1;
				victory = true;
			}
			
		} else {
			players.get(whichPlayer).getTurtle().setCoordinate(new Coordinate(x,y));
		}
		return facedDirection;
	}

}
