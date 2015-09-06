package zan.lib.droid.gfx.shader;

import android.util.Log;

import static android.opengl.GLES20.*;

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

	protected int fetchAttribLocation(String variable) {
		int location = glGetAttribLocation(programID, variable);
		if (location == -1) Log.e("ShaderProgram", variable + " is not a valid GLSL program variable!");
		return location;
	}
	protected int fetchUniformLocation(String variable) {
		int location = glGetUniformLocation(programID, variable);
		if (location == -1) Log.e("ShaderProgram", variable + " is not a valid GLSL program variable!");
		return location;
	}

	protected boolean loadProgram(String vertexShader, String fragmentShader) {
		programID = glCreateProgram();
		loadShader(vertexShader, GL_VERTEX_SHADER);
		loadShader(fragmentShader, GL_FRAGMENT_SHADER);
		glLinkProgram(programID);

		final int[] linkStatus = new int[1];
		glGetProgramiv(programID, GL_LINK_STATUS, linkStatus, 0);
		if (linkStatus[0] != 1) {
			Log.e("ShaderProgram", "Error linking GLSL program " + programID + ":\n" + glGetProgramInfoLog(programID));
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

		final int[] compileStatus = new int[1];
		glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus, 0);
		if (compileStatus[0] != GL_TRUE) {
			Log.e("ShaderProgram", "Unable to compile GLSL shader " + shader + ":\n" + glGetShaderInfoLog(shader));
			return false;
		}

		glAttachShader(programID, shader);
		glDeleteShader(shader);
		return true;
	}

}
