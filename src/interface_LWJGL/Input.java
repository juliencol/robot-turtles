package interface_LWJGL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Type de donnee repr?sentant une Entree.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class Input {
	private GLFWKeyCallback keyboard;
	private GLFWCursorPosCallback mouseMove;
	private GLFWMouseButtonCallback mouseButtons;
	
	private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private static double mouseX;
	private static double mouseY;
	
	/**
	 * Initialise les callbacks pour le clavier et la souris
	 */
	public Input() {
		keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW.GLFW_RELEASE);
			}
		};
		
		mouseMove = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				mouseX = xpos;
				mouseY = ypos;
				
			}
		};
		
		mouseButtons = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				buttons[button] = (action != GLFW.GLFW_RELEASE);
				
			}
		};
	}

	/**
	 * Fournit le callback du clavier
	 * @return callback du clavier
	 */
	public GLFWKeyCallback getKeyboardCallback() {
		return keyboard;
	}

	/**
	 * Fournit le callback de la position de la souris
	 * @return callback de la position de la souris
	 */
	public GLFWCursorPosCallback getMouseMoveCallback() {
		return mouseMove;
	}

	/**
	 * Fournit le callback du clique de la souris
	 * @return callback du clique de la souris
	 */
	public GLFWMouseButtonCallback getMouseButtonsCallback() {
		return mouseButtons;
	}

	/**
	 * Fournit l'abscisse la souris sur la fen?tre
	 * @return l'abscisse
	 */
	public static double getMouseX() {
		return mouseX;
	}

	/**
	 * Fournit l'ordonnee la souris sur la fen?tre
	 * @return l'ordonnee
	 */
	public static double getMouseY() {
		return mouseY;
	}

	/**
	 * Fournit la touche du clavier definit par l'indice
	 * @param key, l'indice de la touche saisie
	 * @return la touche du clavier saisie
	 */
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
	
	/**
	 * Fournit le bouton de la souris sur lequel le joueur a appuye
	 * @param button, l'indice du bouton de la souris
	 * @return le bouton de la souris
	 */
	public static boolean isButtonDown(int button) {
		return buttons[button];
	}
	
	/**
	 * Supprime la capture des differentes entrees
	 */
	public void destroy() {
		keyboard.free();
		mouseButtons.free();
		mouseMove.free();
	}
	
}
