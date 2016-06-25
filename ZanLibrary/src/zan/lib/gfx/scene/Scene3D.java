package zan.lib.gfx.scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import zan.lib.gfx.shader.ShaderProgram;
import zan.lib.math.linalg.Mat44D;

public class Scene3D extends DefaultScene {

	protected ShaderProgram shader;

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

	@Override
	public void create() {
		String vertexShaderSource = "#version 110\n"
				+ "uniform mat4 modelViewMatrix;"
				+ "uniform mat4 projectionMatrix;"
				+ "attribute vec3 vertexPos;"
				+ "attribute vec3 vertexNormal;"
				+ "attribute vec4 vertexColor;"
				+ "attribute vec2 texCoord;"
				+ "varying vec3 fragPos;"
				+ "varying vec3 fragNormal;"
				+ "varying vec4 fragColor;"
				+ "varying vec2 fragTexCoord;"
				+ "void main() {"
				+ "fragPos = vec3(modelViewMatrix * vec4(vertexPos, 1.0));"
				+ "fragNormal = vec3(modelViewMatrix * vec4(vertexNormal, 0.0));"
				+ "fragColor = vertexColor;"
				+ "fragTexCoord = texCoord;"
				+ "gl_Position = projectionMatrix * modelViewMatrix * vec4(vertexPos, 1.0);"
				+ "}";
		String fragmentShaderSource = "#version 110\n"
				+ "uniform bool enableColor;"
				+ "uniform bool enableTexture;"
				+ "uniform sampler2D texUnit;"
				+ "uniform vec4 tintColor;"
				+ "varying vec3 fragPos;"
				+ "varying vec3 fragNormal;"
				+ "varying vec4 fragColor;"
				+ "varying vec2 fragTexCoord;"
				+ "void main() {"
				+ "vec3 lightPos = vec3(0.0, 0.0, 0.0);"
				+ "float lightDist = length(lightPos - fragPos);"
				+ "vec3 lightVector = normalize(lightPos - fragPos);"
				+ "float diffuse = max(dot(fragNormal, lightVector), 0.0);"
				+ "diffuse = diffuse * (1.0 / (1.0 + (0.05 * lightDist * lightDist)));"
				+ "diffuse = diffuse + 0.3;"
				+ "vec4 color = fragColor;"
				+ "if (enableColor) color = tintColor;"
				+ "if (enableTexture) color = color * texture2D(texUnit, fragTexCoord);"
				+ "gl_FragColor = color * diffuse;"
				+ "}";
		shader = new ShaderProgram();
		shader.loadProgram(vertexShaderSource, fragmentShaderSource);

		vertexPosID = shader.fetchAttribLocation("vertexPos");
		vertexNormalID = shader.fetchAttribLocation("vertexNormal");
		vertexColorID = shader.fetchAttribLocation("vertexColor");
		texCoordID = shader.fetchAttribLocation("texCoord");
		texUnitID = shader.fetchUniformLocation("texUnit");
		enableTextureID = shader.fetchUniformLocation("enableTexture");
		enableColorID = shader.fetchUniformLocation("enableColor");
		tintColorID = shader.fetchUniformLocation("tintColor");
		modelViewMatrixID = shader.fetchUniformLocation("modelViewMatrix");
		projectionMatrixID = shader.fetchUniformLocation("projectionMatrix");
	}

	@Override
	public void destroy() {
		shader.destroy();
	}

	@Override
	public void begin() {
		shader.bind();
		super.begin();
	}

	@Override
	public void end() {
		shader.unbind();
	}

	@Override
	public void setColor(double r, double g, double b, double a) {
		glUniform4f(tintColorID, (float)r, (float)g, (float)b, (float)a);
	}

	@Override
	public void bindTexture(int texture) {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture);
		glUniform1i(texUnitID, 0);
	}

	@Override
	public void bindModelView() {
		Mat44D modelViewMatrix = getModelViewMatrix().transpose();
		matrixBuffer.clear();
		for (int i=0;i<16;i++) matrixBuffer.put((float)modelViewMatrix.get(i));
		matrixBuffer.flip();
		glUniformMatrix4fv(modelViewMatrixID, false, matrixBuffer);
	}
	@Override
	public void bindProjection() {
		Mat44D projectMatrix = projectionMatrix.transpose();
		matrixBuffer.clear();
		for (int i=0;i<16;i++) matrixBuffer.put((float)projectMatrix.get(i));
		matrixBuffer.flip();
		glUniformMatrix4fv(projectionMatrixID, false, matrixBuffer);
	}

	@Override
	public void enablePositionPointer() {if (vertexPosID != -1) shader.enableVertexPointer(vertexPosID);}
	@Override
	public void disablePositionPointer() {if (vertexPosID != -1) shader.disableVertexPointer(vertexPosID);}
	@Override
	public void setPositionPointer(int size, int stride, int offset) {if (vertexPosID != -1) shader.setVertexPointer(vertexPosID, size, stride, offset);}

	@Override
	public void enableNormalPointer() {if (vertexNormalID != -1) shader.enableVertexPointer(vertexNormalID);}
	@Override
	public void disableNormalPointer() {if (vertexNormalID != -1) shader.disableVertexPointer(vertexNormalID);}
	@Override
	public void setNormalPointer(int size, int stride, int offset) {if (vertexNormalID != -1) shader.setVertexPointer(vertexNormalID, size, stride, offset);}

	@Override
	public void enableColorPointer() {if (vertexColorID != -1) shader.enableVertexPointer(vertexColorID); glUniform1i(enableColorID, 0);}
	@Override
	public void disableColorPointer() {if (vertexColorID != -1) shader.disableVertexPointer(vertexColorID); glUniform1i(enableColorID, 1);}
	@Override
	public void setColorPointer(int size, int stride, int offset) {if (vertexColorID != -1) shader.setVertexPointer(vertexColorID, size, stride, offset);}

	@Override
	public void enableTexCoordPointer() {if (texCoordID != -1) shader.enableVertexPointer(texCoordID); glUniform1i(enableTextureID, 1);}
	@Override
	public void disableTexCoordPointer() {if (texCoordID != -1) shader.disableVertexPointer(texCoordID); glUniform1i(enableTextureID, 0);}
	@Override
	public void setTexCoordPointer(int size, int stride, int offset) {if (texCoordID != -1) shader.setVertexPointer(texCoordID, size, stride, offset);}

}
