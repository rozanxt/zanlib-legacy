package zan.lib.core;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class CoreEngine {
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	
	private long window;
	
	private String TITLE = "Project Title";
	
	private int SCREEN_WIDTH = 800;
	private int SCREEN_HEIGHT = 600;
	
	private int VISIBLE = GL_FALSE;
	private int RESIZEABLE = GL_FALSE;
	
	public void run() {
		try {
			init();
			loop();
			
			glfwDestroyWindow(window);
			keyCallback.release();
		} finally {
			glfwTerminate();
			errorCallback.release();
		}
	}
	
	private void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		
		if (glfwInit() != GL_TRUE) throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, VISIBLE);
		glfwWindowHint(GLFW_RESIZABLE, RESIZEABLE);
		
		window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, NULL, NULL);
		if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");
		
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, GL_TRUE);
				
				// Input
				
			}
		});
		
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - SCREEN_WIDTH) / 2, (GLFWvidmode.height(vidmode) - SCREEN_HEIGHT) / 2);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}
	
	private void loop() {
		GLContext.createFromCurrent();
		
		glClearColor(0f, 0f, 0f, 0f);
		
		while (glfwWindowShouldClose(window) == GL_FALSE) {
			
			// Update
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			// Render
			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
	
	public static void main(String[] args) {
		new CoreEngine().run();
	}
	
}
