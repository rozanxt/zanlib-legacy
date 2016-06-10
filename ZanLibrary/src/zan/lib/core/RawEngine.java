package zan.lib.core;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;

public abstract class RawEngine {

	private boolean initialized = false;
	public boolean isInitialized() {return initialized;}

	public void run() {
		if (initialized) {
			System.err.println("Warning: Engine already initialized");
			return;
		}
		try {
			GLFWErrorCallback.createPrint(System.err).set();
			if (!glfwInit()) throw new IllegalStateException("Error: Unable to initialize GLFW");
			init();
			initialized = true;
			loop();
			exit();
		} finally {
			glfwTerminate();
			glfwSetErrorCallback(null).free();
			initialized = false;
		}
	}

	protected abstract void init();
	protected abstract void loop();
	protected abstract void exit();

}
