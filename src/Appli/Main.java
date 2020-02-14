package Appli;

import org.lwjgl.glfw.GLFW;
import game.Game;
import interface_LWJGL.Input;
import interface_LWJGL.Window;


public class Main implements Runnable {
	
	public Thread thread;
	public static Window window;
	public static boolean exit;
	
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 760;
	
	public void start() {
		thread = new Thread(this,"thread");
		thread.start();
	}
	
	public static void init() {
		exit = false;
		window = new Window(WIDTH, HEIGHT, "Robot Turtle");
		window.SetBackgroundColor(256, 256, 256);
		window.create();

	}
	
	@Override
	public void run() {
		init();
		while((!window.shouldClose() && exit == false)) {
			if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
				exit = true;
			}
			update();
			render();
			if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
				window.setFullScreen(!window.isFullScreen());
			}
		}
		window.destroy();
	}

	private void render() {
		window.swapBuffers();
	}

	private void update() {
		window.updateScreen();
		if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (Input.getMouseX() >= (8*window.getWidth()/20) && Input.getMouseX() <= (12*window.getWidth()/20) )) {
			if(window.getWhichWindow().equals("home")) {
				if(Input.getMouseY() >= (14.6*window.getHeight()/20) && Input.getMouseY() <= (16.1*window.getHeight()/20)) {
					window.setWhichWindow("menu");
					window.updateScreen();
				}
				else if(Input.getMouseY() >= (17*window.getHeight()/20) && Input.getMouseY() <= (18.5*window.getHeight()/20)) {
					exit = true;
				}
			}
			else if(window.getWhichWindow().equals("menu")) {
				Game game;
				if(Input.getMouseY() >= (2*window.getHeight()/20) && Input.getMouseY() <= (4*window.getHeight()/20)) {
					window.setWhichWindow("inGame");
					game = new Game(window,2);
					window.swapBuffers();
					while((!window.shouldClose() && exit == false && game.isVictory() == false)) {
						game.launchGame();
					}
				}
				else if(Input.getMouseY() >= (5*window.getHeight()/20) && Input.getMouseY() <= (7*window.getHeight()/20)) {
					window.setWhichWindow("inGame");
					game = new Game(window,3);
					window.swapBuffers();
					while((!window.shouldClose() && exit == false) && game.isVictory() == false) {
						game.launchGame();
					}
				}
				else if(Input.getMouseY() >= (8*window.getHeight()/20) && Input.getMouseY() <= (10*window.getHeight()/20)) {
					window.setWhichWindow("inGame");
					game = new Game(window,4);
					window.swapBuffers();
					while((!window.shouldClose() && exit == false) && game.isVictory() == false) {
						game.launchGame();
					}
				}
				
				try {
					Thread.sleep(2000);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				window.setWhichWindow("home");
				window.updateScreen();
			}
		}
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
