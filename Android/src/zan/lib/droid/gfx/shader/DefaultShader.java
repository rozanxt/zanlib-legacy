package zan.lib.droid.gfx.shader;

import static android.opengl.GLES20.*;

import zan.lib.droid.util.math.Mat44F;
import zan.lib.droid.util.math.Vec4F;

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

	protected Vec4F clearColor = new Vec4F(0f, 0f, 0f, 1f);

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

	public void setClearColor(float r, float g, float b, float a) {
		clearColor.setComponents(r, g, b, a);
		glClearColor(r, g, b, a);
	}

	public void setColor(float r, float g, float b, float a) {
		glUniform4f(tintColorID, r, g, b, a);
	}

	public void bindTexture(int texture) {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture);
		glUniform1i(texUnitID, 0);
	}

	public void bindModelView() {
		Mat44F modelViewMatrix = getModelViewMatrix();
		float[] floatBuffer = new float[16];
		for (int i=0;i<16;i++) floatBuffer[i] = modelViewMatrix.get(i);
		glUniformMatrix4fv(modelViewMatrixID, 1, false, floatBuffer, 0);
	}
	public void bindProjection() {
		float[] floatBuffer = new float[16];
		for (int i=0;i<16;i++) floatBuffer[i] = projectionMatrix.get(i);
		glUniformMatrix4fv(projectionMatrixID, 1, false, floatBuffer, 0);
	}
	public void bindMatrix() {
		bindModelView();
		bindProjection();
	}

	public void bindBuffer(int vertexBuffer, int indexBuffer) {
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	}

	public void enablePositionPointer() {if (vertexPosID != -1) enableVertexPointer(vertexPosID);}
	public void disablePositionPointer() {if (vertexPosID != -1) disableVertexPointer(vertexPosID);}
	public void setPositionPointer(int size, int stride, int offset) {if (vertexPosID != -1) setVertexPointer(vertexPosID, size, stride, offset);}

	public void enableNormalPointer() {if (vertexNormalID != -1) enableVertexPointer(vertexNormalID);}
	public void disableNormalPointer() {if (vertexNormalID != -1) disableVertexPointer(vertexNormalID);}
	public void setNormalPointer(int size, int stride, int offset) {if (vertexNormalID != -1) setVertexPointer(vertexNormalID, size, stride, offset);}

	public void enableColorPointer() {if (vertexColorID != -1) enableVertexPointer(vertexColorID); glUniform1i(enableColorID, 0);}
	public void disableColorPointer() {if (vertexColorID != -1) disableVertexPointer(vertexColorID); glUniform1i(enableColorID, 1);}
	public void setColorPointer(int size, int stride, int offset) {if (vertexColorID != -1) setVertexPointer(vertexColorID, size, stride, offset);}

	public void enableTexCoordPointer() {if (texCoordID != -1) enableVertexPointer(texCoordID); glUniform1i(enableTextureID, 1);}
	public void disableTexCoordPointer() {if (texCoordID != -1) disableVertexPointer(texCoordID); glUniform1i(enableTextureID, 0);}
	public void setTexCoordPointer(int size, int stride, int offset) {if (texCoordID != -1) setVertexPointer(texCoordID, size, stride, offset);}

	public void drawElements(int drawMode, int numVertices, int indexOffset) {glDrawElements(drawMode, numVertices, GL_UNSIGNED_INT, indexOffset);}

	@Override
	public void bindState() {
		bind();
		glClearColor(clearColor.get(0), clearColor.get(1), clearColor.get(2), clearColor.get(3));
		setColor(1f, 1f, 1f, 1f);
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
		String fragmentShaderSource = "precision mediump float;"
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
