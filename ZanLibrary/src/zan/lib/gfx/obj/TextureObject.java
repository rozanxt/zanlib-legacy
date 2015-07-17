package zan.lib.gfx.obj;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureInfo;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class TextureObject extends VertexObject {
	
	protected TextureInfo texture;
	
	public TextureObject(TextureInfo texture) {
		super();
		setTexture(texture);
	}
	public TextureObject(TextureInfo texture, float[] vertices, int[] indices) {
		super(vertices, indices);
		setTexture(texture);
	}
	
	public void setTexture(TextureInfo texture) {this.texture = texture;}
	public TextureInfo getTexture() {return texture;}
	
	@Override
	public void render(ShaderProgram sp) {
		sp.bind();
		sp.bindProjection();
		sp.bindModelView();
		sp.bindColor();
		sp.enableTexture(true);
		glBindTexture(GL_TEXTURE_2D, texture.id);
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
