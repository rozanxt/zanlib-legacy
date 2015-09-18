package zan.lib.gfx.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL15.*;
import static zan.lib.gfx.shader.ShaderProgram.BYTES_PER_FLOAT;
import static zan.lib.gfx.shader.ShaderProgram.BYTES_PER_INT;
import zan.lib.gfx.shader.DefaultShader;

public class VertexObject {

	public static final int GL_POINTS = GL11.GL_POINTS;
	public static final int GL_LINE_STRIP = GL11.GL_LINE_STRIP;
	public static final int GL_LINE_LOOP = GL11.GL_LINE_LOOP;
	public static final int GL_LINES = GL11.GL_LINES;
	public static final int GL_TRIANGLE_STRIP = GL11.GL_TRIANGLE_STRIP;
	public static final int GL_TRIANGLE_FAN = GL11.GL_TRIANGLE_FAN;
	public static final int GL_TRIANGLES = GL11.GL_TRIANGLES;

	protected int vertexBuffer = 0;
	protected int indexBuffer = 0;

	protected int numCoords = 3;
	protected int numNormals = 0;
	protected int numColors = 0;
	protected int numTexCoords = 0;
	protected int numData = numCoords + numNormals + numColors + numTexCoords;
	protected int coordOffset = 0;
	protected int normalOffset = coordOffset + numCoords;
	protected int colorOffset = normalOffset + numNormals;
	protected int texCoordOffset = colorOffset + numColors;

	protected int numVertices = 0;
	protected int indexOffset = 0;
	protected int drawMode = GL_TRIANGLES;

	public VertexObject() {}
	public VertexObject(float[] vertices, int[] indices) {
		createVBO(vertices);
		createIBO(indices);
	}
	public VertexObject(float[] vertices, int[] indices, int coords, int normals, int colors, int texcoords) {
		this(vertices, indices);
		setAttributes(coords, normals, colors, texcoords);
	}
	public VertexObject(float[] vertices, int[] indices, int coords, int normals, int colors, int texcoords, int mode) {
		this(vertices, indices, coords, normals, colors, texcoords);
		setDrawMode(mode);
	}

	public void destroy() {
		clearVertexBuffer();
		clearIndexBuffer();
	}

	public void setAttributes(int coords, int normals, int colors, int texcoords) {
		numCoords = coords;
		numNormals = normals;
		numColors = colors;
		numTexCoords = texcoords;
		numData = numCoords + numNormals + numColors + numTexCoords;
		coordOffset = 0;
		normalOffset = coordOffset + numCoords;
		colorOffset = normalOffset + numNormals;
		texCoordOffset = colorOffset + numColors;
	}

	public void setNumVertices(int num) {numVertices = num;}
	public void setIndexOffset(int offset) {indexOffset = offset;}
	public void setDrawMode(int mode) {drawMode = mode;}

	public void render(DefaultShader sp) {
		sp.bindMatrix();
		sp.bindBuffer(vertexBuffer, indexBuffer);

		if (sp.isPositionPointerAvailable()) {
			if (numCoords > 0) {
				sp.setPositionPointer(numCoords, numData, coordOffset);
				sp.enablePositionPointer();
			} else sp.disablePositionPointer();
		}
		if (sp.isNormalPointerAvailable()) {
			if (numNormals > 0) {
				sp.setNormalPointer(numNormals, numData, normalOffset);
				sp.enableNormalPointer();
			} else sp.disableNormalPointer();
		}
		if (sp.isColorPointerAvailable()) {
			if (numColors > 0) {
				sp.setColorPointer(numColors, numData, colorOffset);
				sp.enableColorPointer();
			} else sp.disableColorPointer();
		}
		if (sp.isTexCoordPointerAvailable()) {
			if (numTexCoords > 0) {
				sp.setTexCoordPointer(numTexCoords, numData, texCoordOffset);
				sp.enableTexCoordPointer();
			} else sp.disableTexCoordPointer();
		}

		sp.drawElements(drawMode, numVertices, indexOffset);
	}

	public int createVBO(float[] vertices) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(vertices);
		buffer.flip();
		return createVBO(buffer);
	}
	public int createIBO(int[] indices) {
		IntBuffer buffer = ByteBuffer.allocateDirect(indices.length * BYTES_PER_INT).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(indices);
		buffer.flip();
		return createIBO(buffer);
	}

	public int createVBO(FloatBuffer buffer) {
		clearVertexBuffer();
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		return vertexBuffer;
	}
	public int createIBO(IntBuffer buffer) {
		clearIndexBuffer();
		numVertices = buffer.capacity();
		indexBuffer = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		return indexBuffer;
	}

	private void clearVertexBuffer() {
		glDeleteBuffers(vertexBuffer);
		vertexBuffer = 0;
	}
	private void clearIndexBuffer() {
		glDeleteBuffers(indexBuffer);
		indexBuffer = 0;
	}

}
