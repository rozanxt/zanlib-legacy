package zan.lib.gfx;

import zan.lib.util.Utility;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	
	private int programID;
	
	private int colorID;
	
	public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
		programID = 0;
		colorID = 0;
		loadProgram(vertexShaderPath, fragmentShaderPath);
	}
	
	public void bind() {glUseProgram(programID);}
	public void unbind() {glUseProgram(0);}
	
	public void destroy() {
		glDeleteShader(programID);
		programID = 0;
	}
	
	public void setColor(float r, float g, float b, float a) {
		glUniform4f(colorID, r, g, b, a);
	}
	
	private boolean loadProgram(String vertexShaderPath, String fragmentShaderPath) {
		programID = glCreateProgram();
		loadShaderFile(vertexShaderPath, GL_VERTEX_SHADER);
		loadShaderFile(fragmentShaderPath, GL_FRAGMENT_SHADER);
		glLinkProgram(programID);
		
		if (glGetProgrami(programID, GL_LINK_STATUS) != 1) {
			System.err.println("Error linking GLSL program " + programID + ":\n" + glGetProgramInfoLog(programID));
			destroy();
			return false;
		}
		
		colorID = glGetUniformLocation(programID, "color");
		if (colorID == -1) System.err.println("color" + " is not a valid GLSL program variable!");
		
		return true;
	}
	
	private boolean loadShaderFile(String path, int shaderType) {
		int shader = glCreateShader(shaderType);
		glShaderSource(shader, Utility.readFileAsString(path));
		glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE) {
			System.err.println("Unable to compile GLSL shader " + shader + ":\n" + glGetShaderInfoLog(shader));	
			return false;
		}
		
		glAttachShader(programID, shader);
		glDeleteShader(shader);
		return true;
	}
	
	public int getProgramID() {return programID;}
	
}
