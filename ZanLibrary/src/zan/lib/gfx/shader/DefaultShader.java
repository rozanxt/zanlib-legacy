package zan.lib.gfx.shader;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.BufferUtils;

import zan.lib.util.math.Mat44D;
import zan.lib.util.math.Vec4D;

public class DefaultShader extends MatrixShader {

	protected int vertexPosID = -1;
	protected int vertexNormalID = -1;
	protected int vertexColorID = -1;
	protected int texCoordID = -1;
	protected int texUnitID = -1;
	protected int enableTextureID = -1;
	protected int enableColorID = -1;
	protected int tintColorID = -1;
	protected int modelViewMatrixID = -1;
	protected int projectionMatrixID = -1;

	protected boolean enableBlend = false;
	protected boolean enableCullFace = false;
	protected boolean enableDepthTest = false;

	protected int blendSrc = GL_SRC_ALPHA;
	protected int blendDst = GL_ONE_MINUS_SRC_ALPHA;

	protected Vec4D clearColor = new Vec4D(0.0, 0.0, 0.0, 1.0);

	protected FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public void enableBlend(boolean blend) {
		enableBlend = blend;
		if (enableBlend) {
			glEnable(GL_BLEND);
			glBlendFunc(blendSrc, blendDst);
		} else glDisable(GL_BLEND);
	}
	public void enableCullFace(boolean cullFace) {
		enableCullFace = cullFace;
		if (enableCullFace) glEnable(GL_CULL_FACE);
		else glDisable(GL_CULL_FACE);
	}
	public void enableDepthTest(boolean depthTest) {
		enableDepthTest = depthTest;
		if (enableDepthTest) glEnable(GL_DEPTH_TEST);
		else glDisable(GL_DEPTH_TEST);
	}

	public void setBlendFunc(int src, int dst) {
		blendSrc = src;
		blendDst = dst;
		glBlendFunc(blendSrc, blendDst);
	}

	public void setClearColor(double r, double g, double b, double a) {
		clearColor.setComponents(r, g, b, a);
		glClearColor((float)r, (float)g, (float)b, (float)a);
	}

	public void setColor(double r, double g, double b, double a) {
		glUniform4f(tintColorID, (float)r, (float)g, (float)b, (float)a);
	}

	public void bindTexture(int texture) {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture);
		glUniform1i(texUnitID, 0);
	}

	public void bindModelView() {
		Mat44D modelViewMatrix = getModelViewMatrix();
		matrixBuffer.clear();
		for (int i=0;i<16;i++) matrixBuffer.put((float)modelViewMatrix.get(i));
		matrixBuffer.flip();
		glUniformMatrix4fv(modelViewMatrixID, false, matrixBuffer);
	}
	public void bindProjection() {
		matrixBuffer.clear();
		for (int i=0;i<16;i++) matrixBuffer.put((float)projectionMatrix.get(i));
		matrixBuffer.flip();
		glUniformMatrix4fv(projectionMatrixID, false, matrixBuffer);
	}
	public void bindMatrix() {
		bindModelView();
		bindProjection();
	}

	public void bindBuffer(int vertexBuffer, int indexBuffer) {
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	}

	public void enablePositionPointer() {enableVertexPointer(vertexPosID);}
	public void disablePositionPointer() {disableVertexPointer(vertexPosID);}
	public void setPositionPointer(int size, int stride, int offset) {setVertexPointer(vertexPosID, size, stride, offset);}

	public void enableNormalPointer() {enableVertexPointer(vertexNormalID);}
	public void disableNormalPointer() {disableVertexPointer(vertexNormalID);}
	public void setNormalPointer(int size, int stride, int offset) {setVertexPointer(vertexNormalID, size, stride, offset);}

	public void enableColorPointer() {enableVertexPointer(vertexColorID); glUniform1i(enableColorID, 0);}
	public void disableColorPointer() {disableVertexPointer(vertexColorID); glUniform1i(enableColorID, 1);}
	public void setColorPointer(int size, int stride, int offset) {setVertexPointer(vertexColorID, size, stride, offset);}

	public void enableTexCoordPointer() {enableVertexPointer(texCoordID); glUniform1i(enableTextureID, 1);}
	public void disableTexCoordPointer() {disableVertexPointer(texCoordID); glUniform1i(enableTextureID, 0);}
	public void setTexCoordPointer(int size, int stride, int offset) {setVertexPointer(texCoordID, size, stride, offset);}

	public void drawElements(int drawMode, int numVertices, int indexOffset) {glDrawElements(drawMode, numVertices, GL_UNSIGNED_INT, indexOffset);}

	public boolean isPositionPointerAvailable() {return (vertexPosID != -1);}
	public boolean isNormalPointerAvailable() {return (vertexNormalID != -1);}
	public boolean isColorPointerAvailable() {return (vertexColorID != -1);}
	public boolean isTexCoordPointerAvailable() {return (texCoordID != -1);}


	@Override
	public void bindState() {
		bind();
		glClearColor((float)clearColor.get(0), (float)clearColor.get(1), (float)clearColor.get(2), (float)clearColor.get(3));
		setColor(1.0, 1.0, 1.0, 1.0);
		enableBlend(enableBlend);
		enableCullFace(enableCullFace);
		enableDepthTest(enableDepthTest);
		unbind();
	}

	@Override
	public void loadProgram() {
		String vertexShaderSource = ""
				+ "uniform mat4 modelViewMatrix;"
				+ "uniform mat4 projectionMatrix;"
				+ "attribute vec3 vertexPos;"
				+ "attribute vec4 vertexColor;"
				+ "attribute vec2 texCoord;"
				+ "varying vec4 fragColor;"
				+ "varying vec2 fragTexCoord;"
				+ "void main() {"
				+ "fragColor = vertexColor;"
				+ "fragTexCoord = texCoord;"
				+ "gl_Position = projectionMatrix * modelViewMatrix * vec4(vertexPos, 1.0);"
				+ "}";
		String fragmentShaderSource = ""
				+ "uniform bool enableColor;"
				+ "uniform bool enableTexture;"
				+ "uniform sampler2D texUnit;"
				+ "uniform vec4 tintColor;"
				+ "varying vec4 fragColor;"
				+ "varying vec2 fragTexCoord;"
				+ "void main() {"
				+ "vec4 color = fragColor;"
				+ "if (enableColor) color = tintColor;"
				+ "if (enableTexture) color = color * texture2D(texUnit, fragTexCoord);"
				+ "gl_FragColor = color;"
				+ "}";
		loadProgram(vertexShaderSource, fragmentShaderSource);
	}

	@Override
	protected void fetchLocations() {
		vertexPosID = fetchAttribLocation("vertexPos");
		// vertexNormalID = fetchAttribLocation("vertexNormal");
		vertexColorID = fetchAttribLocation("vertexColor");
		texCoordID = fetchAttribLocation("texCoord");
		texUnitID = fetchUniformLocation("texUnit");
		enableTextureID = fetchUniformLocation("enableTexture");
		enableColorID = fetchUniformLocation("enableColor");
		tintColorID = fetchUniformLocation("tintColor");
		modelViewMatrixID = fetchUniformLocation("modelViewMatrix");
		projectionMatrixID = fetchUniformLocation("projectionMatrix");
	}

}
