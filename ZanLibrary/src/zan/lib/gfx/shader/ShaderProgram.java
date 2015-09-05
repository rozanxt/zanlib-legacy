package zan.lib.gfx.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

	public static final int BYTES_PER_FLOAT = 4;
	public static final int BYTES_PER_INT = 4;

	protected int programID = 0;

	public abstract void bindState();
	public abstract void loadProgram();
	protected abstract void fetchLocations();

	public void bind() {glUseProgram(programID);}
	public void unbind() {glUseProgram(0);}

	public void destroy() {
		glDeleteProgram(programID);
		programID = 0;
	}

	protected void enableVertexPointer(int attrib) {glEnableVertexAttribArray(attrib);}
	protected void disableVertexPointer(int attrib) {glDisableVertexAttribArray(attrib);}
	protected void setVertexPointer(int attrib, int size, int stride, int offset) {glVertexAttribPointer(attrib, size, GL_FLOAT, false, stride * BYTES_PER_FLOAT, offset * BYTES_PER_FLOAT);}

	protected void bindAttribLocation(int id, String variable) {glBindAttribLocation(programID, id, variable);}
	protected int fetchAttribLocation(String variable) {
		int location = glGetAttribLocation(programID, variable);
		if (location == -1) System.err.println(variable + " is not a valid GLSL program variable!");
		return location;
	}
	protected int fetchUniformLocation(String variable) {
		int location = glGetUniformLocation(programID, variable);
		if (location == -1) System.err.println(variable + " is not a valid GLSL program variable!");
		return location;
	}

	protected boolean loadProgram(String vertexShader, String fragmentShader) {
		programID = glCreateProgram();
		loadShader(vertexShader, GL_VERTEX_SHADER);
		loadShader(fragmentShader, GL_FRAGMENT_SHADER);
		glLinkProgram(programID);

		if (glGetProgrami(programID, GL_LINK_STATUS) != 1) {
			System.err.println("Error linking GLSL program " + programID + ":\n" + glGetProgramInfoLog(programID));
			destroy();
			return false;
		}

		fetchLocations();
		bindState();
		return true;
	}

	private boolean loadShader(String source, int type) {
		int shader = glCreateShader(type);
		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE) {
			System.err.println("Unable to compile GLSL shader " + shader + ":\n" + glGetShaderInfoLog(shader));
			return false;
		}

		glAttachShader(programID, shader);
		glDeleteShader(shader);
		return true;
	}

}
