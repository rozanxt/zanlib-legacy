package zan.lib.gfx.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import zan.lib.gfx.Gfx;
import zan.lib.res.ResourceUtil;

public class ShaderProgram {

	private int programID = 0;

	public boolean loadProgramFromFile(String vertexShaderPath, String fragmentShaderPath) {
		return loadProgram(ResourceUtil.readFileAsString(vertexShaderPath), ResourceUtil.readFileAsString(fragmentShaderPath));
	}

	public boolean loadProgram(String vertexShader, String fragmentShader) {
		programID = glCreateProgram();
		loadShader(vertexShader, GL_VERTEX_SHADER);
		loadShader(fragmentShader, GL_FRAGMENT_SHADER);
		glLinkProgram(programID);

		if (glGetProgrami(programID, GL_LINK_STATUS) != 1) {
			System.err.println("Error linking GLSL program " + programID + ":\n" + glGetProgramInfoLog(programID));
			destroy();
			return false;
		}
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

	public void destroy() {
		glDeleteProgram(programID);
		programID = 0;
	}

	public void bind() {glUseProgram(programID);}
	public void unbind() {glUseProgram(0);}

	public void enableVertexPointer(int attrib) {glEnableVertexAttribArray(attrib);}
	public void disableVertexPointer(int attrib) {glDisableVertexAttribArray(attrib);}
	public void setVertexPointer(int attrib, int size, int stride, int offset) {glVertexAttribPointer(attrib, size, GL_FLOAT, false, stride * Gfx.BYTES_PER_FLOAT, offset * Gfx.BYTES_PER_FLOAT);}

	public int fetchAttribLocation(String variable) {
		int location = glGetAttribLocation(programID, variable);
		if (location == -1) System.err.println(variable + " is not a valid GLSL program variable!");
		return location;
	}
	public int fetchUniformLocation(String variable) {
		int location = glGetUniformLocation(programID, variable);
		if (location == -1) System.err.println(variable + " is not a valid GLSL program variable!");
		return location;
	}

	public int getProgramID() {return programID;}

}
