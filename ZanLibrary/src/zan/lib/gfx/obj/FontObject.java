package zan.lib.gfx.obj;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

import java.util.ArrayList;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureManager;
import zan.lib.gfx.text.CharInfo;
import zan.lib.gfx.text.FontInfo;

public class FontObject extends TextureObject {
	
	public FontObject(FontInfo fontInfo, ArrayList<CharInfo> charInfo) {
		super(TextureManager.loadTexture(fontInfo.name, fontInfo.filename));
		
		int[] indices = new int[16*16*4];
		for (int i=0;i<16*16*4;i++) indices[i] = i;
		
		float[] vertices = new float[16*16*4*4];
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				float tileWidth = (1f/16f);
				float tileHeight = (1f/16f);
				
				// Vertex Coords
				vertices[16*16*i+16*j+4*0+0] = 0f;
				vertices[16*16*i+16*j+4*0+1] = 0f;
				vertices[16*16*i+16*j+4*1+0] = 1f;
				vertices[16*16*i+16*j+4*1+1] = 0f;
				vertices[16*16*i+16*j+4*2+0] = 1f;
				vertices[16*16*i+16*j+4*2+1] = 1f;
				vertices[16*16*i+16*j+4*3+0] = 0f;
				vertices[16*16*i+16*j+4*3+1] = 1f;
				
				// Texture Coords
				vertices[16*16*i+16*j+4*0+2] = j*tileWidth;
				vertices[16*16*i+16*j+4*0+3] = (i+1)*tileHeight;
				vertices[16*16*i+16*j+4*1+2] = (j+1)*tileWidth;
				vertices[16*16*i+16*j+4*1+3] = (i+1)*tileHeight;
				vertices[16*16*i+16*j+4*2+2] = (j+1)*tileWidth;
				vertices[16*16*i+16*j+4*2+3] = i*tileHeight;
				vertices[16*16*i+16*j+4*3+2] = j*tileWidth;
				vertices[16*16*i+16*j+4*3+3] = i*tileHeight;
			}
		}
		
		createVBO(vertices);
		createIBO(indices);
		setNumVertices(4);
		setNumCoords(2);
		setDrawMode(GL_TRIANGLE_FAN);
	}
	
	public void render(ShaderProgram sp, int ch) {
		setIndexOffset(16*ch);
		render(sp);
	}
	
}
