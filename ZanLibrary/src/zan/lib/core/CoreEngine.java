package zan.lib.core;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import zan.lib.input.InputManager;

public abstract class CoreEngine {

	// CALLBACK CLASSES

	private GLFWErrorCallback errorCallback;

	private GLFWKeyCallback keyCallback;
	private GLFWCharCallback charCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private GLFWScrollCallback mouseScrollCallback;
	private GLFWCursorEnterCallback mouseEnterCallback;

	private GLFWWindowCloseCallback windowCloseCallback;
	private GLFWWindowRefreshCallback windowRefreshCallback;
	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWFramebufferSizeCallback framebufferSizeCallback;
	private GLFWWindowPosCallback windowPosCallback;
	private GLFWWindowIconifyCallback windowIconifyCallback;
	private GLFWWindowFocusCallback windowFocusCallback;

	// WINDOW VARIABLES

	private String SCR_TITLE = "";

	private int SCR_X = -1;
	private int SCR_Y = -1;

	private int WIN_WIDTH = 640;
	private int WIN_HEIGHT = 480;
	private int MNT_WIDTH;
	private int MNT_HEIGHT;
	private int SCR_WIDTH = WIN_WIDTH;
	private int SCR_HEIGHT = WIN_HEIGHT;

	// WINDOW FLAGS

	private boolean SCR_VISIBLE = true;
	private boolean SCR_MINIMIZE = false;
	private boolean SCR_FULL = false;
	private boolean SCR_CROP = false;

	private boolean SCR_RESIZABLE = false;
	private boolean SCR_DECORATED = true;
	private boolean SCR_FLOATING = false;
	private boolean SCR_AUTOMINIMIZE = true;

	private int SWAP_INTERVAL = 0;

	private boolean REQ_WINDOW_RESET = false;

	// TIMING VARIABLES

	public static final int SM_NONE = 0;
	public static final int SM_FREE = 1;
	public static final int SM_TARGET = 2;
	private int SLEEP_MODE = SM_FREE;

	private long ticks = 0L;
	private double fps = 0.0;
	private double TARGET_FPS = 120.0;
	private double DELTA_FPS = (1.0/TARGET_FPS);
	private double TARGET_TPS = 50.0;
	private double DELTA_TPS = (1.0/TARGET_TPS);
	private int SLEEP_DURATION = 2;
	private int MAX_FRAME_SKIP = 5;

	// WINDOW & PANEL

	private long coreWindow;

	private BasePanel corePanel;

	private boolean initialized = false;

	// RUN METHOD

	protected void run() {
		if (initialized) return;
		try {
			init();
			loop();
			destroy();
		} finally {
			glfwTerminate();
			errorCallback.release();
		}
	}

	// CLEANUP

	private void destroy() {
		corePanel.destroy();
		InputManager.destroy();
		glfwDestroyWindow(coreWindow);
		keyCallback.release();
		charCallback.release();
		mouseButtonCallback.release();
		mousePosCallback.release();
		mouseScrollCallback.release();
		mouseEnterCallback.release();
		windowCloseCallback.release();
		windowRefreshCallback.release();
		windowSizeCallback.release();
		framebufferSizeCallback.release();
		windowPosCallback.release();
		windowIconifyCallback.release();
		windowFocusCallback.release();
	}

	public void close() {glfwSetWindowShouldClose(coreWindow, GL_TRUE);}
	public void close(boolean close) {
		if (close) close();
		else glfwSetWindowShouldClose(coreWindow, GL_FALSE);
	}

	// INITIALIZATION

	private void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		if (glfwInit() != GL_TRUE) throw new IllegalStateException("Unable to initialize GLFW");

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		MNT_WIDTH = GLFWvidmode.width(vidmode);
		MNT_HEIGHT = GLFWvidmode.height(vidmode);
		if (SCR_X == -1) SCR_X = (MNT_WIDTH-WIN_WIDTH)/2;
		if (SCR_Y == -1) SCR_Y = (MNT_HEIGHT-WIN_HEIGHT)/2;

		createWindow(NULL);
		initInput(NULL);
		initWindow();
		initGL();

		corePanel.init();

		REQ_WINDOW_RESET = false;
		initialized = true;
	}

	private void resetWindow() {
		long previousWindow = coreWindow;
		createWindow(previousWindow);
		initInput(previousWindow);
		initWindow();

		onWindowResize(coreWindow, WIN_WIDTH, WIN_HEIGHT);
		onScreenResize(coreWindow, SCR_WIDTH, SCR_HEIGHT);
		onWindowMove(coreWindow, SCR_X, SCR_Y);
		onWindowMinimize(coreWindow, isMinimized());
		onWindowFocus(coreWindow, isFocused());

		REQ_WINDOW_RESET = false;
	}

	private void createWindow(long previousWindow) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_FOCUSED, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, SCR_RESIZABLE ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, SCR_DECORATED ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_FLOATING, SCR_FLOATING ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_AUTO_ICONIFY, SCR_AUTOMINIMIZE ? GL_TRUE : GL_FALSE);

		if (SCR_FULL) {
			if (SCR_CROP) {
				SCR_WIDTH = WIN_WIDTH;
				SCR_HEIGHT = WIN_HEIGHT;
			} else {
				SCR_WIDTH = MNT_WIDTH;
				SCR_HEIGHT = MNT_HEIGHT;
			}
			coreWindow = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, SCR_TITLE, glfwGetPrimaryMonitor(), previousWindow);
		} else {
			SCR_WIDTH = WIN_WIDTH;
			SCR_HEIGHT = WIN_HEIGHT;
			coreWindow = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, SCR_TITLE, NULL, previousWindow);
		}

		if (coreWindow == NULL) throw new RuntimeException("Failed to create the GLFW window");
		if (previousWindow != NULL) glfwDestroyWindow(previousWindow);
	}

	private void initInput(long previousWindow) {
		InputManager.init();
		InputManager.setWindow(coreWindow);
		if (previousWindow != NULL) InputManager.destroyWindow(previousWindow);

		glfwSetKeyCallback(coreWindow, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int state, int mods) {
				if (initialized) {
					InputManager.invokeKeys(window, key, state, mods);
					onKey(window, key, state, mods, scancode);
				}
			}
		});
		glfwSetCharCallback(coreWindow, charCallback = new GLFWCharCallback() {
			@Override
			public void invoke(long window, int ch) {
				if (initialized) {
					InputManager.invokeChars(window, (char)ch);
					onChar(window, (char)ch);
				}
			}
		});
		glfwSetMouseButtonCallback(coreWindow, mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int state, int mods) {
				if (initialized) {
					InputManager.invokeMouseButtons(window, button, state, mods);
					onMouseButton(window, button, state, mods);
				}
			}
		});
		glfwSetCursorPosCallback(coreWindow, mousePosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double mouseX, double mouseY) {
				if (initialized) {
					InputManager.invokeMousePos(window, mouseX, mouseY);
					onMouseMove(window, mouseX, mouseY);
				}
			}
		});
		glfwSetScrollCallback(coreWindow, mouseScrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double scrollX, double scrollY) {
				if (initialized) {
					InputManager.invokeMouseScroll(window, scrollY);
					onMouseScroll(window, scrollX, scrollY);
				}
			}
		});
		glfwSetCursorEnterCallback(coreWindow, mouseEnterCallback = new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, int mouseEnter) {
				if (initialized) {
					InputManager.invokeMouseEnter(window, (mouseEnter == GL_TRUE));
					onMouseEnter(window, (mouseEnter == GL_TRUE));
				}
			}
		});

		glfwSetWindowCloseCallback(coreWindow, windowCloseCallback = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				if (initialized) onWindowClose(window);
			}
		});
		glfwSetWindowRefreshCallback(coreWindow, windowRefreshCallback = new GLFWWindowRefreshCallback() {
			@Override
			public void invoke(long window) {
				if (initialized) onWindowRefresh(window);
			}
		});
		glfwSetWindowSizeCallback(coreWindow, windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				if (initialized && !SCR_FULL) {
					WIN_WIDTH = width;
					WIN_HEIGHT = height;
					onWindowResize(window, width, height);
				}
			}
		});
		glfwSetFramebufferSizeCallback(coreWindow, framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				if (initialized && !SCR_FULL) {
					SCR_WIDTH = width;
					SCR_HEIGHT = height;
					onScreenResize(window, width, height);
				}
			}
		});
		glfwSetWindowPosCallback(coreWindow, windowPosCallback = new GLFWWindowPosCallback() {
			@Override
			public void invoke(long window, int posX, int posY) {
				if (initialized && !SCR_FULL) {
					SCR_X = posX;
					SCR_Y = posY;
					onWindowMove(window, posX, posY);
				}
			}
		});
		glfwSetWindowIconifyCallback(coreWindow, windowIconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, int minimize) {
				if (initialized) {
					SCR_MINIMIZE = (minimize == GL_TRUE);
					onWindowMinimize(window, (minimize == GL_TRUE));
				}
			}
		});
		glfwSetWindowFocusCallback(coreWindow, windowFocusCallback = new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, int focus) {
				if (initialized) onWindowFocus(window, (focus == GL_TRUE));
			}
		});
	}

	private void initWindow() {
		glfwMakeContextCurrent(coreWindow);
		glfwSwapInterval(SWAP_INTERVAL);
		if (!SCR_FULL) glfwSetWindowPos(coreWindow, SCR_X, SCR_Y);
		if (SCR_MINIMIZE) glfwIconifyWindow(coreWindow);
		if (SCR_VISIBLE) glfwShowWindow(coreWindow);
	}

	private void initGL() {
		GLContext.createFromCurrent();
	}

	// MAIN LOOP

	private void loop() {
		double nextTick, nextFrame;
		nextTick = nextFrame = getTime();
		int frameCount = 0;

		while (glfwWindowShouldClose(coreWindow) == GL_FALSE) {
			double time = getTime();

			int frameSkip = 0;
			while (time > nextTick && frameSkip < MAX_FRAME_SKIP) {
				InputManager.clear(coreWindow);
				glfwPollEvents();

				update(time);

				ticks++;
				frameSkip++;
				nextTick += DELTA_TPS;
			}

			// FPS counter
			frameCount++;
			if (time > nextFrame) {
				fps = frameCount;
				frameCount = 0;
				nextFrame += 1.0;
			}

			render((time+DELTA_TPS-nextTick)/(DELTA_TPS));
			sleep(time);
			check();
		}
	}

	private void update(double time) {
		corePanel.update(time);
	}

	private void render(double ip) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		corePanel.render(ip);

		glfwSwapBuffers(coreWindow);
	}

	private void sleep(double time) {
		if (SLEEP_MODE == SM_FREE) {
			Thread.yield();
			try {Thread.sleep(SLEEP_DURATION);}
			catch (InterruptedException e) {}
		} else if (SLEEP_MODE == SM_TARGET) {
			double now = getTime();
			while (now - time < DELTA_FPS) {
				Thread.yield();
				now = getTime();
			}
		}
	}

	private void check() {
		BasePanel nextPanel = corePanel.changePanel();
		if (nextPanel != null) setPanel(nextPanel);

		if (REQ_WINDOW_RESET) resetWindow();
	}

	// SETTERS

	public void setPanel(BasePanel panel) {
		if (initialized && corePanel != null) corePanel.destroy();
		corePanel = panel;
		if (initialized) corePanel.init();
	}

	public void setTitle(String title) {
		SCR_TITLE = title;
		if (initialized) glfwSetWindowTitle(coreWindow, SCR_TITLE);
	}

	public void setWindowPos(int posX, int posY) {
		SCR_X = posX;
		SCR_Y = posY;
		if (initialized) glfwSetWindowPos(coreWindow, SCR_X, SCR_Y);
	}

	public void setScreenSize(int width, int height) {
		WIN_WIDTH = width;
		WIN_HEIGHT = height;
		SCR_WIDTH = WIN_WIDTH;
		SCR_HEIGHT = WIN_HEIGHT;
		if (initialized) glfwSetWindowSize(coreWindow, SCR_WIDTH, SCR_HEIGHT);
	}

	// TOGGLERS

	public void setFullScreen(boolean fullscreen) {SCR_FULL = fullscreen; REQ_WINDOW_RESET = true;}
	public void toggleFullScreen() {setFullScreen(!SCR_FULL);}

	public void setScreenCrop(boolean screencrop) {SCR_CROP = screencrop; REQ_WINDOW_RESET = true;}
	public void toggleScreenCrop() {setScreenCrop(!SCR_CROP);}

	public void setVSync(boolean vsync) {
		SWAP_INTERVAL = vsync ? 1 : 0;
		if (initialized) glfwSwapInterval(SWAP_INTERVAL);
	}
	public void toggleVSync() {setVSync(!isVSync());}

	public void setVisible(boolean visible) {
		SCR_VISIBLE = visible;
		if (initialized) {
			if (SCR_VISIBLE) glfwShowWindow(coreWindow);
			else glfwHideWindow(coreWindow);
		}
	}
	public void toggleVisible() {setVisible(!isVisible());}

	public void setMinimize(boolean minimize) {
		SCR_MINIMIZE = minimize;
		if (initialized) {
			if (SCR_MINIMIZE) glfwIconifyWindow(coreWindow);
			else glfwRestoreWindow(coreWindow);
		}
	}
	public void toggleMinimize() {setMinimize(!isMinimized());}

	/** @deprecated No implementation of this method yet. */
	public void setFocus(boolean focused) {}
	/** @deprecated No implementation of this method yet. */
	public void toggleFocus() {}

	public void setResizable(boolean resizable) {SCR_RESIZABLE = resizable; REQ_WINDOW_RESET = true;}
	public void toggleResizable() {setResizable(!SCR_RESIZABLE);}

	public void setDecorated(boolean decorated) {SCR_DECORATED = decorated; REQ_WINDOW_RESET = true;}
	public void toggleDecorated() {setDecorated(!SCR_DECORATED);}

	public void setFloating(boolean floating) {SCR_FLOATING = floating; REQ_WINDOW_RESET = true;}
	public void toggleFloating() {setFloating(!SCR_FLOATING);}

	public void setAutoMinimize(boolean autominimize) {SCR_AUTOMINIMIZE = autominimize; REQ_WINDOW_RESET = true;}
	public void toggleAutoMinimize() {setAutoMinimize(!SCR_AUTOMINIMIZE);}

	// TIMING METHODS

	public void setSleepMode(int mode) {SLEEP_MODE = mode;}

	public void setTargetFPS(double fps) {
		TARGET_FPS = fps;
		DELTA_FPS = (1.0/TARGET_FPS);
	}
	public void setTargetTPS(double tps) {
		TARGET_TPS = tps;
		DELTA_TPS = (1.0/TARGET_TPS);
	}

	public void setSleepDuration(int duration) {SLEEP_DURATION = duration;}
	public void setMaxFrameSkip(int mfs) {MAX_FRAME_SKIP = mfs;}

	// CALLBACK METHODS

	protected void onKey(int key, int state, int mods, int scancode) {if (corePanel != null) corePanel.onKey(key, state, mods, scancode);}
	protected void onChar(char ch) {if (corePanel != null) corePanel.onChar(ch);}
	protected void onMouseButton(int button, int state, int mods) {if (corePanel != null) corePanel.onMouseButton(button, state, mods);}
	protected void onMouseMove(double mouseX, double mouseY) {if (corePanel != null) corePanel.onMouseMove(mouseX, mouseY);}
	protected void onMouseScroll(double scrollX, double scrollY) {if (corePanel != null) corePanel.onMouseScroll(scrollX, scrollY);}
	protected void onMouseEnter(boolean mouseEnter) {if (corePanel != null) corePanel.onMouseEnter(mouseEnter);}

	protected void onWindowClose() {if (corePanel != null) corePanel.onWindowClose();}
	protected void onWindowRefresh() {if (corePanel != null) corePanel.onWindowRefresh();}
	protected void onWindowResize(int width, int height) {if (corePanel != null) corePanel.onWindowResize(width, height);}
	protected void onScreenResize(int width, int height) {if (corePanel != null) corePanel.onScreenResize(width, height);}
	protected void onWindowMove(int posX, int posY) {if (corePanel != null) corePanel.onWindowMove(posX, posY);}
	protected void onWindowMinimize(boolean minimize) {if (corePanel != null) corePanel.onWindowMinimize(minimize);}
	protected void onWindowFocus(boolean focus) {if (corePanel != null) corePanel.onWindowFocus(focus);}

	protected void onKey(long window, int key, int state, int mods, int scancode) {if (coreWindow == window) onKey(key, state, mods, scancode);}
	protected void onChar(long window, char ch) {if (coreWindow == window) onChar(ch);}
	protected void onMouseButton(long window, int button, int state, int mods) {if (coreWindow == window) onMouseButton(button, state, mods);}
	protected void onMouseMove(long window, double mouseX, double mouseY) {if (coreWindow == window) onMouseMove(mouseX, mouseY);}
	protected void onMouseScroll(long window, double scrollX, double scrollY) {if (coreWindow == window) onMouseScroll(scrollX, scrollY);}
	protected void onMouseEnter(long window, boolean mouseEnter) {if (coreWindow == window) onMouseEnter(mouseEnter);}

	protected void onWindowClose(long window) {if (coreWindow == window) onWindowClose();}
	protected void onWindowRefresh(long window) {if (coreWindow == window) onWindowRefresh();}
	protected void onWindowResize(long window, int width, int height) {if (coreWindow == window) onWindowResize(width, height);}
	protected void onScreenResize(long window, int width, int height) {if (coreWindow == window) onScreenResize(width, height);}
	protected void onWindowMove(long window, int posX, int posY) {if (coreWindow == window) onWindowMove(posX, posY);}
	protected void onWindowMinimize(long window, boolean minimize) {if (coreWindow == window) onWindowMinimize(minimize);}
	protected void onWindowFocus(long window, boolean focus) {if (coreWindow == window) onWindowFocus(focus);}

	// GETTERS

	public long getWindow() {return coreWindow;}

	public boolean isInitialized() {return initialized;}

	public BasePanel getPanel() {return corePanel;}

	public String getTitle() {return SCR_TITLE;}

	public int getWindowX() {return SCR_X;}
	public int getWindowY() {return SCR_Y;}

	public int getWindowWidth() {return WIN_WIDTH;}
	public int getWindowHeight() {return WIN_HEIGHT;}
	public double getWindowRatio() {return (double)WIN_WIDTH/(double)WIN_HEIGHT;}

	public int getMonitorWidth() {return MNT_WIDTH;}
	public int getMonitorHeight() {return MNT_HEIGHT;}
	public double getMonitorRatio() {return (double)MNT_WIDTH/(double)MNT_HEIGHT;}

	public int getScreenWidth() {return SCR_WIDTH;}
	public int getScreenHeight() {return SCR_HEIGHT;}
	public double getScreenRatio() {return (double)SCR_WIDTH/(double)SCR_HEIGHT;}

	public boolean isFullScreen() {return (glfwGetWindowMonitor(coreWindow) == glfwGetPrimaryMonitor());}
	public boolean isScreenCrop() {return SCR_CROP;}
	public boolean isVSync() {return !(SWAP_INTERVAL == 0);}

	public boolean isVisible() {return (glfwGetWindowAttrib(coreWindow, GLFW_VISIBLE) == GL_TRUE);}
	public boolean isMinimized() {return (glfwGetWindowAttrib(coreWindow, GLFW_ICONIFIED) == GL_TRUE);}
	public boolean isFocused() {return (glfwGetWindowAttrib(coreWindow, GLFW_FOCUSED) == GL_TRUE);}
	public boolean isResizable() {return (glfwGetWindowAttrib(coreWindow, GLFW_RESIZABLE) == GL_TRUE);}
	public boolean isDecorated() {return (glfwGetWindowAttrib(coreWindow, GLFW_DECORATED) == GL_TRUE);}
	public boolean isFloating() {return (glfwGetWindowAttrib(coreWindow, GLFW_FLOATING) == GL_TRUE);}
	public boolean isAutoMinimize() {return (glfwGetWindowAttrib(coreWindow, GLFW_AUTO_ICONIFY) == GL_TRUE);}

	public double getTime() {return glfwGetTime();}
	public long getTime(int resolution) {return (long)(glfwGetTime()*Math.pow(10, resolution));}
	public long getTicks() {return ticks;}
	public double getFPS() {return fps;}
	public double getTargetFPS() {return TARGET_FPS;}
	public double getDeltaFPS() {return DELTA_FPS;}
	public double getTargetTPS() {return TARGET_TPS;}
	public double getDeltaTPS() {return DELTA_TPS;}
	public int getSleepMode() {return SLEEP_MODE;}
	public int getSleepDuration() {return SLEEP_DURATION;}
	public int getMaxFrameSkip() {return MAX_FRAME_SKIP;}

}
