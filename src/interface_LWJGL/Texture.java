package interface_LWJGL;

import org.lwjgl.opengl.GL11;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

/**
 * Type de donnee representant une Texture.
 * 
 * @author LAMY David, COLOMBAIN Julien et HU Dylan
 * 
 * @version 1.0
 */
public class Texture {
	private int id;
	private int width;
	private int height;
	
	/**
	 * Transforme l'image sous un format lisible par la librairie LWJGL
	 * @param filename, le nom du fichier image
	 */
	public Texture(String filename) {
		BufferedImage bi;
		
		try {
			bi = ImageIO.read(new File("./img/"+filename+".png"));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int[width * height * 4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			//create a new byte buffer which will hold our pixel data
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int i = 0; i < height; i++) {
				
				for (int j = 0; j < width; j++) {
					
					int pixel = pixels_raw[i * width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF)); // RED
					pixels.put((byte)((pixel >> 8) & 0xFF)); // GREEN
					pixels.put((byte)(pixel & 0xFF)); // BLUE
					pixels.put((byte)((pixel >> 24) & 0xFF)); // ALPHA

				}
			}
			
			//flip the buffer into "read mode" for OpenGL
			pixels.flip();
			
			//generate a texture handle or unique ID for this texture
			id = GL11.glGenTextures();
			
			//bind the texture
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			
			//set up our texture parameters
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			//upload our ByteBuffer to GL
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
			
		}
		
		catch(IOException e) {
			
		}
		
	}
	
	/**
	 * Dessine l'image sur la fenetre graphique a la position donnee
	 * @param xLeft, la position du cote gauche sur l'axe des abscisses
	 * @param yTop, la position du cote superieur sur l'axe des ordonnees
	 * @param xRight, la position du cote droit sur l'axe des abscisses
	 * @param yBottom, la position du cote inferieur sur l'axe des ordonnees
	 */
	public void draw (float xLeft, float yTop, float xRight, float yBottom) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(xLeft, yTop);// top-left width and height
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(xRight, yTop);// top right width and height
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(xRight, yBottom);//bottom-right width and height
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(xLeft, yBottom);//bottom-left width and height
		
		GL11.glEnd();
	}

}
