package zan.lib.gfx;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

public class TextureManager {
	private static final String LOGNAME = "TextureManager :: ";
	
	private static HashMap<String, Integer> textureStore;
	
	private static GraphicsConfiguration gc;
	private static final int BYTES_PER_PIXEL = 4;
	
	public static void init() {
		textureStore = new HashMap<String, Integer>();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
	}
	
	public static void destroy() {
		for (Map.Entry<String, Integer> entry : textureStore.entrySet()) glDeleteTextures(entry.getValue());
		textureStore.clear();
	}
	
	public static int loadTexture(String texture, String filename) {
		if (textureStore.containsKey(texture)) {
			System.err.println(LOGNAME + "Error loading texture:\n " + texture + " is already used");
			return getTextureID(texture);
		}
		
		int textureID = createTexture(filename);
		if (textureID != 0) textureStore.put(texture, new Integer(textureID));
		return textureID;
	}
	
	private static int createTexture(String filename) {
		try {
			BufferedImage im = ImageIO.read(new File(filename));
			
			int transparency = im.getColorModel().getTransparency();
			BufferedImage bi =  gc.createCompatibleImage(im.getWidth(), im.getHeight(), transparency);
			Graphics2D g2d = bi.createGraphics();
			g2d.setComposite(AlphaComposite.Src);
			
			g2d.drawImage(im, 0, 0, null);
			g2d.dispose();
			return genTexture(bi);
		} catch(IOException e) {
			System.err.println(LOGNAME + "Error loading texture for " + filename + ":\n " + e); 
			return 0;
		}
	}
	
	private static int genTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));	// Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));	// Green component
				buffer.put((byte) (pixel & 0xFF));			// Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));	// Alpha component. Only for RGBA
			}
		}
		
		buffer.flip();
		
		int textureID = glGenTextures();			// Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID);	// Bind texture ID
		
		// Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); //GL_LINEAR / GL_NEAREST
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		// Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		return textureID;
	}
	
	public static boolean unloadTexture(String texture) {
		if (isTextureLoaded(texture)) {
			glDeleteTextures(getTextureID(texture));
			textureStore.remove(texture);
			return true;
		}
		return false;
	}
	
	public static int getTextureID(String texture) {
		if (!isTextureLoaded(texture)) {
			System.err.println(LOGNAME + "No texture stored under " + texture);  
			return 0;
		}
		return textureStore.get(texture);
	}
	
	public static boolean isTextureLoaded(String texture) {
		if (textureStore.get(texture) == null || textureStore.get(texture) == 0) return false;
		return true;
	}
	
}
