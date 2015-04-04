package zan.lib.gfx;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexObject {
	
	private ShaderProgram shaderProgram;
	
	private int vertexBuffer;
	private int indexBuffer;
	
	public VertexObject() {
		shaderProgram = null;
		vertexBuffer = 0;
		indexBuffer = 0;
	}
	
	public void setShaderProgram(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}
	
	public void createVBO(FloatBuffer vertices) {
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
	}
	
	public void createIBO(IntBuffer indices) {
		indexBuffer = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
	}
	
	public void render(double ip) {
		shaderProgram.bind();
		shaderProgram.setColor(1f, 0f, 0f, 1f);
		glEnableClientState(GL_VERTEX_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
			glVertexPointer(2, GL_FLOAT, 0, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
			glDrawElements(GL_QUADS, 4, GL_UNSIGNED_INT, 0);
		glDisableClientState(GL_VERTEX_ARRAY);
		shaderProgram.unbind();
	}
	
	public ShaderProgram getShaderProgram() {return shaderProgram;}
	
}
