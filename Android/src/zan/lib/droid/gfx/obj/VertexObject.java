package zan.lib.droid.gfx.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.opengl.GLES20;
import static android.opengl.GLES20.*;
import static zan.lib.droid.gfx.shader.ShaderProgram.BYTES_PER_FLOAT;
import static zan.lib.droid.gfx.shader.ShaderProgram.BYTES_PER_INT;
import zan.lib.droid.gfx.shader.DefaultShader;

public class VertexObject {

	public static final int GL_POINTS = GLES20.GL_POINTS;
	public static final int GL_LINE_STRIP = GLES20.GL_LINE_STRIP;
	public static final int GL_LINE_LOOP = GLES20.GL_LINE_LOOP;
	public static final int GL_LINES = GLES20.GL_LINES;
	public static final int GL_TRIANGLE_STRIP = GLES20.GL_TRIANGLE_STRIP;
	public static final int GL_TRIANGLE_FAN = GLES20.GL_TRIANGLE_FAN;
	public static final int GL_TRIANGLES = GLES20.GL_TRIANGLES;

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

		if (numCoords > 0) {
			sp.setPositionPointer(numCoords, numData, coordOffset);
			sp.enablePositionPointer();
		} else sp.disablePositionPointer();
		if (numNormals > 0) {
			sp.setNormalPointer(numNormals, numData, normalOffset);
			sp.enableNormalPointer();
		} else sp.disableNormalPointer();
		if (numColors > 0) {
			sp.setColorPointer(numColors, numData, colorOffset);
			sp.enableColorPointer();
		} else sp.disableColorPointer();
		if (numTexCoords > 0) {
			sp.setTexCoordPointer(numTexCoords, numData, texCoordOffset);
			sp.enableTexCoordPointer();
		} else sp.disableTexCoordPointer();

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
		final int[] vB = new int[1];
		glGenBuffers(vB.length, vB, 0);
		vertexBuffer = vB[0];
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_FLOAT, buffer, GL_STATIC_DRAW);
		return vertexBuffer;
	}
	public int createIBO(IntBuffer buffer) {
		clearIndexBuffer();
		numVertices = buffer.capacity();
		final int[] iB = new int[1];
		glGenBuffers(iB.length, iB, 0);
		indexBuffer = iB[0];
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_INT, buffer, GL_STATIC_DRAW);
		return indexBuffer;
	}

	private void clearVertexBuffer() {
		final int[] dB = new int[1];
		dB[0] = vertexBuffer;
		glDeleteBuffers(dB.length, dB, 0);
		vertexBuffer = 0;
	}
	private void clearIndexBuffer() {
		final int[] dB = new int[1];
		dB[0] = indexBuffer;
		glDeleteBuffers(dB.length, dB, 0);
		indexBuffer = 0;
	}

}
