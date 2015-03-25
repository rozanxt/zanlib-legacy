package zan.lib.core;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import zan.lib.input.InputManager;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class CoreEngine {
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWCharCallback charCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private GLFWScrollCallback mouseScrollCallback;
	private GLFWCursorEnterCallback mouseEnterCallback;
	
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
			charCallback.release();
			mouseButtonCallback.release();
			mousePosCallback.release();
			mouseScrollCallback.release();
			mouseEnterCallback.release();
		} finally {
			glfwTerminate();
			errorCallback.release();
		}
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, GL_TRUE);
	}
	
	private void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		
		if (glfwInit() != GL_TRUE) throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, VISIBLE);
		glfwWindowHint(GLFW_RESIZABLE, RESIZEABLE);
		
		window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, NULL, NULL);
		if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");
		
		InputManager.init();
		InputManager.setWindow(window);
		
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int state, int mods) {
				InputManager.invokeKeys(window, key, state, mods);
			}
		});
		glfwSetCharCallback(window, charCallback = new GLFWCharCallback() {
			@Override
			public void invoke(long window, int ch) {
				InputManager.invokeChars(window, ch);
			}
		});
		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int state, int mods) {
				InputManager.invokeMouseButtons(window, button, state, mods);
			}
		});
		glfwSetCursorPosCallback(window, mousePosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double mouseX, double mouseY) {
				InputManager.invokeMousePos(window, mouseX, mouseY);
			}
		});
		glfwSetScrollCallback(window, mouseScrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double scrollX, double scrollY) {
				InputManager.invokeMouseScroll(window, scrollY);
			}
		});
		glfwSetCursorEnterCallback(window, mouseEnterCallback = new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, int mouseEnter) {
				InputManager.invokeMouseEnter(window, (mouseEnter == 1));
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
			if (InputManager.isKeyReleased(InputManager.IM_KEY_ESCAPE)) close();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			// Render
			
			glfwSwapBuffers(window);
			
			InputManager.clear(window);
			glfwPollEvents();
		}
	}
	
	public static void main(String[] args) {
		CoreEngine core = new CoreEngine();
		core.run();
	}
	
}
