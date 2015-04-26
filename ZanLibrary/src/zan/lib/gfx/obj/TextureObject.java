package zan.lib.gfx.obj;

import zan.lib.gfx.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class TextureObject extends VertexObject {
	
	protected int textureID;
	
	public TextureObject(int textureID) {
		super();
		setTextureID(textureID);
	}
	public TextureObject(int textureID, float[] vertices, int[] indices) {
		super(vertices, indices);
		setTextureID(textureID);
	}
	
	public void setTextureID(int textureID) {this.textureID = textureID;}
	public int getTextureID() {return textureID;}
	
	@Override
	public void render(ShaderProgram sp) {
		sp.bind();
		sp.bindProjection();
		sp.bindModelView();
		sp.bindColor();
		sp.enableTexture(true);
		glBindTexture(GL_TEXTURE_2D, textureID);
		sp.enableVertexPointer();
		sp.enableTexCoordPointer();
			glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
			sp.setVertexPointer(numCoords, numCoords+2, 0);
			sp.setTexCoordPointer(2, numCoords+2, numCoords);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
			glDrawElements(drawMode, numVertices, GL_UNSIGNED_INT, indexOffset);
		sp.disableVertexPointer();
		sp.disableTexCoordPointer();
	}
	
}
