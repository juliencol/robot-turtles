package interface_LWJGL;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import game.Player;
import occupier.Occupier;

import java.util.List;

/**
 * Type de donnee representant une Fenetre.
 *
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 *
 * @version 1.0
 */
public class Window {

	private int width;
	private int height;
	private String title;
	private long window;
	private Input input;
	private float backgroundR, backgroundG, backgroundB;
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized;
	private boolean isFullScreen;
	private int[] windowPosX;
	private int[] windowPosY;
	private String whichWindow;

	/**
	 * Cr?? une fen?tre graphique selon une taille et un titre donne
	 * @param width, la largeur de la fenetre en pixel
	 * @param height, la hauteur de la fenetre en pixel
	 * @param title, le titre de la fenetre
	 */
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		windowPosX = new int[1];
		windowPosY = new int[1];
		whichWindow = "home";
	}

	/**
	 * Initialise la fenetre et l'affiche
	 */
	public void create() {
		if(!GLFW.glfwInit()) {
			System.err.println("ERROR : GLFW was not initialized");
			return;
		}

		input = new Input();
		window = GLFW.glfwCreateWindow(width, height, title, isFullScreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

		if(window == 0) {
			System.err.println("ERROR : window was not created");
			return;
		}

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

		windowPosX[0] = (videoMode.width() - width) / 2;
		windowPosY[0] = (videoMode.height() - height) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);

		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		createCallbacks();

		GLFW.glfwShowWindow(window);
		GLFW.glfwSwapInterval(1);
	}

	/**
	 * Met a jour l'affichage de la fenetre
	 */
	public void updateScreen() {
		clearWindow();

		if(whichWindow.equals("home")) {
			home();
			GLFW.glfwWaitEvents();
		}

		else if(whichWindow.equals("menu")) {
			menu();
			GLFW.glfwWaitEvents();
		}
	}

	/**
	 * Modifie sur quelle fenetre se trouve le joueur
	 * @param whichWindow, la nouvelle fen?tre
	 */
	public void setWhichWindow(String whichWindow) {
		this.whichWindow = whichWindow;
	}

	/**
	 * Fournit sur quelle fenetre se trouve le joueur
	 * @return la fen?tre actuelle
	 */
	public String getWhichWindow() {
		return whichWindow;
	}

	/**
	 * Donne la main a la fenetre
	 */
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}

	/**
	 * Attend la reception d'une entr�e
	 */
	public void waitEvents() {
		GLFW.glfwWaitEvents();
	}

	/**
	 * V�rifie si la fen�tre est sur le point de se fermer
	 * @return true si va fen�tre va se fermer sinon false
	 */
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}

	/**
	 * Modifie la couleur de fond
	 * @param r, rouge
	 * @param g, vert
	 * @param b, bleu
	 */
	public void SetBackgroundColor(float r, float g, float b) {
		backgroundB = b;
		backgroundG = g;
		backgroundR = r;
	}

	/**
	 * Fournit la largeur
	 * @return la largeur
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Fournit la hauteur
	 * @return la hauteur
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Passe la fen?tre en mode pleine ecran ou en mode fenetre
	 * @param isFullScreen, variable qui determine si la fenetre doit etre en pleine ecran ou non
	 */
	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
		isResized = true;

		if(isFullScreen) {
			GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
		}

		else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
		}
	}

	/**
	 * Fournit la fenetre est en pleine ?cran
	 * @return true si la fen?tre est en pleine ecran sinon false
	 */
	public boolean isFullScreen() {
		return isFullScreen;
	}
	
	/**
	 * Met fin � la fen�tre graphique
	 */
	public void destroy() {
		input.destroy();
		sizeCallback.free();
		GLFW.glfwWindowShouldClose(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}
	
	/**
	 * Nettoie la fen�tre graphique
	 */
	private void clearWindow() {
		resizeWindow();

		GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ACCUM_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}
	
	private void resizeWindow() {
		if(isResized) {
			GL11.glViewport(0, 0, width, height);
			isResized = false;
		}
	}
	
	/**
	 * Initialise la capture d'entr?e et celle de la taille de la fen?tre
	 */
	private void createCallbacks() {
		sizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int newWidth, int newHeight) {
				width = newWidth;
				height = newHeight;
				isResized = true;
			}
		};

		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
	}
	
	/**
	 * Affiche la fenetre d'accueil
	 */
	private void home() {
		new Texture("game_title").draw(-0.5f, 1f, 0.5f, -0.28f);
		new Texture("turtle_purple").draw(-1f, 1f, -0.7f, 0.33f);
		new Texture("turtle_red").draw(0.7f, 1f, 1f, 0.33f);
		new Texture("turtle_green").draw(-1f, -0.33f, -0.7f, -1f);
		new Texture("turtle_blue").draw(0.7f, -0.33f, 1f, -1f);
		new Texture("btn_play").draw(-0.2f, -0.46f, 0.2f, -0.61f);//516<x<768  554,8<y<611,8
		new Texture("btn_exit").draw(-0.2f, -0.7f, 0.2f, -0.85f);//516<x<768  666<y<723
	}

	/**
	 * Affiche la fenetre de menu
	 */
	private void menu() {
		new Texture("btn_2players").draw(-0.2f, 0.8f, 0.2f, 0.6f);
		new Texture("btn_3players").draw(-0.2f, 0.5f, 0.2f, 0.3f);
		new Texture("btn_4players").draw(-0.2f, 0.2f, 0.2f, 0f);
		new Texture("background").draw(-1f, 1f, 1f, -1f);
	}

	/**
	 * Appelle touttes les diff�rentes fonction d'affichage pour faire appara�tre le jeu en sa globalit�
	 * @param players, listes des joueurs
	 * @param occupiers, listes des tuiles sur le jeu
	 * @param whichPlayer, joueur courant
	 * @param phase, phase de d�roulement dans laquel le joueur se trouve
	 */
	public void placeGame(List<Player> players, List<Occupier> occupiers, int whichPlayer, String phase) {

		clearWindow();

		for (Player player : players) {
			placeTile(player.getTurtle().getColorString()+"_"+player.getTurtle().getOrientationString(),player.getTurtle().getCoordX(),player.getTurtle().getCoordY());
		}

		for (Occupier occupier : occupiers) {
			placeTile(occupier.toString(), occupier.getCoordinate().getX(), occupier.getCoordinate().getY());
		}

		placeBoard();

		if (!(phase.equals("choose_wall") || phase.equals("place_wall"))) {
			placeAction(phase);
		}

		if (phase.equals("in_progress")){
			placeFullProgramCard(players,whichPlayer);
		}

		if (phase.equals("choose_wall") || phase.equals("place_wall")) {
			placePlayerWall(players,whichPlayer);
		}

		else {
			placePlayerCard(players, whichPlayer);
			placeActualProgramCard(players, whichPlayer);
		}

		placePhaseMessage(phase);

	}

	/**
	 * Affiche les tortues sur le plateau
	 * @param tileName, la tortue d'une couleur specifique
	 * @param x, la position sur l'axe des abscisses
	 * @param y, la position sur l'axe des ordonnees
	 */
	private void placeTile(String tileName, int x,int y) {
		new Texture(tileName).draw(-1+x*0.1371f, 1-y*0.212f, -1+(x+1)*0.1371f, 1-(y+1)*0.212f);
	}

	/**
	 * Affiche les messages d'informations
	 * @param phase, la phase de jeu dans lequel le joueur se trouve
	 */
	private void placePhaseMessage(String phase) {
		new Texture(phase).draw(0.25f, 0.2f , 0.95f, 0.05f);
	}

	/**
	 * Affiche la main du joueur courant
	 * @param players,  la liste des joueurs
	 * @param whichPlayer, le joueur courant
	 */
	private void placePlayerCard(List<Player> players, int whichPlayer) {
		float offset = 0.3f;

		for (int i=0 ; i < players.get(whichPlayer).getHand().getCards().size() ; i++) {

			if (i<2) {
				new Texture(players.get(whichPlayer).getHand().getCards().get(i).toString()).draw(i*offset+0.3f,0f,i*offset+0.5f,-0.45f);
			}

			else {
				new Texture(players.get(whichPlayer).getHand().getCards().get(i).toString()).draw((i-2)*offset+0.15f,-0.5f,(i-2)*offset+0.35f,-0.95f);
			}
		}
	}

	/**
	 * Affiche le programme que le joueur est actuellement entrain de completer
	 * @param players,  la liste des joueurs
	 * @param whichPlayer, le joueur courant
	 */
	public void placeActualProgramCard(List<Player> players, int whichPlayer) {
		float offset = 0.16f;

		for (int i=0 ; i < players.get(whichPlayer).getCurrentProgram().size() ; i++) {
			new Texture(players.get(whichPlayer).getCurrentProgram().get(i).toString()).draw(i*offset+0.155f,0.8f,i*offset+0.305f, 0.5f);
		}
	}

	/**
	 * Affiche le programme complet du joueur
	 * @param players,  la liste des joueurs
	 * @param whichPlayer, le joueur courant
	 */
	public void placeFullProgramCard(List<Player> players, int whichPlayer) {
		float offset = 0.1f;

		for (int i=0 ; i < players.get(whichPlayer).getFullProgram().size() ; i++) {

			if (i<9) {
				new Texture(players.get(whichPlayer).getFullProgram().get(i).toString()).draw(i*offset+0.1f,0.99f,i*offset+0.2f, 0.79f);
			}

			else if (i<18) {
				new Texture(players.get(whichPlayer).getFullProgram().get(i).toString()).draw((i-9)*offset+0.1f,0.79f,(i-9)*offset+0.2f, 0.59f);
			}

			else if (i<27) {
				new Texture(players.get(whichPlayer).getFullProgram().get(i).toString()).draw((i-18)*offset+0.1f,0.59f,(i-18)*offset+0.2f, 0.39f);
			}

			else if (i<36) {
				new Texture(players.get(whichPlayer).getFullProgram().get(i).toString()).draw((i-27)*offset+0.1f,0.39f,(i-27)*offset+0.2f, 0.19f);
			}
		}
	}

	/**
	 * Affiche tous les murs du joueur
	 * @param players,  la liste des joueurs
	 * @param whichPlayer, le joueur courant
	 */
	private void placePlayerWall(List<Player> players, int whichPlayer) {
		float offset = 0.3f;

		for (int i=0 ; i < players.get(whichPlayer).getObstacles().size() ; i++) {

			if (i<2) {
				new Texture(players.get(whichPlayer).getObstacles().get(i).toString()).draw(i*offset+0.3f,0f,i*offset+0.5f,-0.35f);
			}

			else {
				new Texture(players.get(whichPlayer).getObstacles().get(i).toString()).draw((i-2)*offset+0.15f, -0.5f,(i-2)*offset+0.35f, -0.85f);
			}
		}
	}

	public void placeSelectedWall(String tileName){
		new Texture(tileName).draw(0.430f, 0.8f, 0.630f, 0.45f);
	}	

	/**
	 * Affiche le deck du joueur
	 * @param players,  la liste des joueurs
	 * @param whichPlayer, le joueur courant
	 */
	// For Debug Purpose Only, do not use otherwise
	private void placeDeck(List<Player> players, int whichPlayer) {
		float offset = 0.1f;

		for (int i=0 ; i < players.get(whichPlayer).getDeck().getCards().size() ; i++) {

			if (i<9) {
				new Texture(players.get(whichPlayer).getDeck().getCards().get(i).toString()).draw(i*offset+0.1f,0.99f,i*offset+0.2f, 0.79f);
			}

			else if (i<18) {
				new Texture(players.get(whichPlayer).getDeck().getCards().get(i).toString()).draw((i-9)*offset+0.1f,0.79f,(i-9)*offset+0.2f, 0.59f);
			}

			else if (i<27) {
				new Texture(players.get(whichPlayer).getDeck().getCards().get(i).toString()).draw((i-18)*offset+0.1f,0.59f,(i-18)*offset+0.2f, 0.39f);
			}

			else if (i<36) {
				new Texture(players.get(whichPlayer).getDeck().getCards().get(i).toString()).draw((i-27)*offset+0.1f,0.39f,(i-27)*offset+0.2f, 0.19f);
			}
		}
	}

	/**
	 * Affiche le plateau de jeu
	 */
	private void placeBoard() {
		new Texture("board-68").draw(-1f, 1f, 0.1f, -0.7f);
	}

	/**
	 * Affiche les boutons d'action dans la fenetre de jeu
	 * @param phase, determine la phase de jeu
	 */
	private void placeAction(String phase){
		if(phase.contains("player")) {
			new Texture("complete").draw(-0.95f, -0.75f, -0.68f, -0.95f);
			new Texture("build").draw(-0.58f, -0.75f, -0.31f, -0.95f);
			new Texture("execute").draw(-0.21f, -0.75f, 0.06f, -0.95f);
		}

		else if(phase.equals("discard")){
			new Texture("yes_discard").draw(-0.8f, -0.75f, -0.5f, -0.95f);
			new Texture("not_discard").draw(-0.4f, -0.75f, -0.1f, -0.95f);
		}

		else if(phase.equals("choose_cards")){
			new Texture("next").draw(-0.65f, -0.75f, -0.1f, -0.95f);
		}
	}

}
