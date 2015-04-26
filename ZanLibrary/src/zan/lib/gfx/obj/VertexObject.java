package zan.lib.gfx.obj;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import zan.lib.gfx.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexObject {
	
	protected int vertexBuffer;
	protected int indexBuffer;
	
	protected int numVertices;
	protected int numCoords;
	protected int indexOffset;
	protected int drawMode;
	
	public VertexObject() {
		vertexBuffer = -1;
		indexBuffer = -1;
		numVertices = 0;
		numCoords = 3;
		indexOffset = 0;
		drawMode = GL_TRIANGLES;
	}
	public VertexObject(float[] vertices, int[] indices) {
		this();
		createVBO(vertices);
		createIBO(indices);
	}
	
	public void destroy() {
		glDeleteBuffers(vertexBuffer);
		glDeleteBuffers(indexBuffer);
		vertexBuffer = -1;
		indexBuffer = -1;
	}
	
	public void setNumVertices(int vertices) {numVertices = vertices;}
	public void setNumCoords(int coords) {numCoords = coords;}
	public void setIndexOffset(int offset) {indexOffset = offset;}
	public void setDrawMode(int mode) {drawMode = mode;}
	
	public int createVBO(float[] vertices) {
		if (vertexBuffer != -1) glDeleteBuffers(vertexBuffer);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
		buffer.put(vertices);
		buffer.flip();
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		return vertexBuffer;
	}
	public int createIBO(int[] indices) {
		if (indexBuffer != -1) glDeleteBuffers(indexBuffer);
		setNumVertices(indices.length);
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();	
		indexBuffer = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		return indexBuffer;
	}
	
	public int getVertexBuffer() {return vertexBuffer;}
	public int getIndexBuffer() {return indexBuffer;}
	
	public void render(ShaderProgram sp) {
		sp.bind();
		sp.bindProjection();
		sp.bindModelView();
		sp.bindColor();
		sp.enableVertexPointer();
			glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
			sp.setVertexPointer(numCoords, 0, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
			glDrawElements(drawMode, numVertices, GL_UNSIGNED_INT, indexOffset);
		sp.disableVertexPointer();
	}
	
}
