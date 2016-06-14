package zan.lib.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.BufferUtils.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.BufferUtils;

import zan.lib.input.InputManager;

public abstract class FrameEngine extends RawEngine {

	// Window Variables

	private String WIN_TITLE = "";
	private String WIN_ICON = "";
	private int WIN_X = -1;
	private int WIN_Y = -1;
	private int WIN_WIDTH = 640;
	private int WIN_HEIGHT = 480;
	private int SCR_WIDTH;
	private int SCR_HEIGHT;
	private int MNT_WIDTH;
	private int MNT_HEIGHT;
	private int MNT_REFRESHRATE;

	private boolean WIN_VISIBLE = true;
	private boolean WIN_FOCUSED = true;
	private boolean WIN_FLOATING = false;
	private boolean WIN_DECORATED = true;
	private boolean WIN_RESIZABLE = false;
	private boolean WIN_MAXIMIZED = false;
	private boolean WIN_MINIMIZED = false;
	private boolean WIN_AUTOICONIFY = true;
	private boolean WIN_FULLSCREEN = false;
	private boolean WIN_SCREENCROP = false;

	private int SWAP_INTERVAL = 1;

	// Timing Variables

	private long ticks = 0L;
	private double fps = 0.0;
	private double TARGET_FPS = 0.0;
	private double DELTA_FPS = 0.0;
	private double TARGET_UPS = 50.0;
	private double DELTA_UPS = (1.0/TARGET_UPS);
	private int SLEEP_DURATION = 2;
	private int MAX_FRAME_SKIP = 5;

	// Window and Panel

	private long currentWindow = 0L;
	private FramePanel currentPanel = null;

	// Initialization

	@Override
	protected void init() {
		initWindow();
		initIcon();
		initInput();
		initGL();
		onInit();
		initPanel();
		showWindow();
	}

	private void initWindow() {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_FOCUSED, WIN_FOCUSED ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_FLOATING, WIN_FLOATING ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, WIN_DECORATED ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, WIN_RESIZABLE ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_MAXIMIZED, WIN_MAXIMIZED ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_AUTO_ICONIFY, WIN_AUTOICONIFY ? GL_TRUE : GL_FALSE);

		currentWindow = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, WIN_TITLE, NULL, NULL);
		if (currentWindow == NULL) throw new RuntimeException("Error: Failed to create the GLFW window");
	}

	private void initIcon() {
		if (!WIN_ICON.isEmpty()) {
			IntBuffer w = memAllocInt(1);
			IntBuffer h = memAllocInt(1);
			IntBuffer comp = memAllocInt(1);
			ByteBuffer icon16, icon32;
			try {
				icon16 = ioResourceToByteBuffer(WIN_ICON, 2048);
				icon32 = ioResourceToByteBuffer(WIN_ICON, 4096);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			try (GLFWImage.Buffer icons = GLFWImage.malloc(2)) {
				ByteBuffer pixels16 = stbi_load_from_memory(icon16, w, h, comp, 4);
				icons.position(0).width(w.get(0)).height(h.get(0)).pixels(pixels16);
				ByteBuffer pixels32 = stbi_load_from_memory(icon32, w, h, comp, 4);
				icons.position(1).width(w.get(0)).height(h.get(0)).pixels(pixels32);
				icons.position(0);
				glfwSetWindowIcon(currentWindow, icons);
			}
		}
	}

	private void initInput() {
		InputManager.create();
		InputManager.setWindow(currentWindow);

		glfwSetKeyCallback(currentWindow, new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int state, int mods) {
				InputManager.invokeKeys(window, key, state, mods);
				onKey(key, state, mods, scancode);
			}
		});
		glfwSetCharCallback(currentWindow, new GLFWCharCallback() {
			@Override
			public void invoke(long window, int ch) {
				InputManager.invokeChars(window, (char)ch);
				onChar((char)ch);
			}
		});
		glfwSetMouseButtonCallback(currentWindow, new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int state, int mods) {
				InputManager.invokeMouseButtons(window, button, state, mods);
				onMouseButton(button, state, mods);
			}
		});
		glfwSetCursorPosCallback(currentWindow, new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				InputManager.invokeMousePos(window, x, y);
				onMouseMove(x, y);
			}
		});
		glfwSetScrollCallback(currentWindow, new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				InputManager.invokeMouseScroll(window, y);
				onMouseScroll(x, y);
			}
		});
		glfwSetCursorEnterCallback(currentWindow, new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, boolean mouseEnter) {
				InputManager.invokeMouseEnter(window, mouseEnter);
				onMouseEnter(mouseEnter);
			}
		});

		glfwSetWindowCloseCallback(currentWindow, new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				onWindowClose();
			}
		});
		glfwSetWindowRefreshCallback(currentWindow, new GLFWWindowRefreshCallback() {
			@Override
			public void invoke(long window) {
				onWindowRefresh();
			}
		});
		glfwSetWindowSizeCallback(currentWindow, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				if (!isFullScreen()) {
					WIN_WIDTH = width;
					WIN_HEIGHT = height;
				}
				onWindowResize(width, height);
			}
		});
		glfwSetFramebufferSizeCallback(currentWindow, new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				SCR_WIDTH = width;
				SCR_HEIGHT = height;
				onScreenResize(width, height);
			}
		});
		glfwSetWindowPosCallback(currentWindow, new GLFWWindowPosCallback() {
			@Override
			public void invoke(long window, int x, int y) {
				if (!isFullScreen()) {
					WIN_X = x;
					WIN_Y = y;
				}
				onWindowMove(x, y);
			}
		});
		glfwSetWindowIconifyCallback(currentWindow, new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, boolean minimized) {
				WIN_MINIMIZED = minimized;
				onWindowMinimize(minimized);
			}
		});
		glfwSetWindowFocusCallback(currentWindow, new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, boolean focus) {
				WIN_FOCUSED = focus;
				onWindowFocus(focus);
			}
		});
	}

	private void initGL() {
		glfwMakeContextCurrent(currentWindow);
		GL.createCapabilities();

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		IntBuffer w = memAllocInt(1);
		IntBuffer h = memAllocInt(1);
		glfwGetFramebufferSize(currentWindow, w, h);
		SCR_WIDTH = w.get(0);
		SCR_HEIGHT = h.get(0);
		MNT_WIDTH = vidmode.width();
		MNT_HEIGHT = vidmode.height();
		MNT_REFRESHRATE = vidmode.refreshRate();
		if (WIN_X == -1) WIN_X = (MNT_WIDTH - WIN_WIDTH) / 2;
		if (WIN_Y == -1) WIN_Y = (MNT_HEIGHT - WIN_HEIGHT) / 2;

		doFullScreen(WIN_FULLSCREEN);
	}

	private void doFullScreen(boolean fullscreen) {
		if (WIN_FULLSCREEN) {
			if (WIN_SCREENCROP) glfwSetWindowMonitor(currentWindow, glfwGetPrimaryMonitor(), 0, 0, WIN_WIDTH, WIN_HEIGHT, MNT_REFRESHRATE);
			else glfwSetWindowMonitor(currentWindow, glfwGetPrimaryMonitor(), 0, 0, MNT_WIDTH, MNT_HEIGHT, MNT_REFRESHRATE);
		} else glfwSetWindowMonitor(currentWindow, NULL, WIN_X, WIN_Y, WIN_WIDTH, WIN_HEIGHT, MNT_REFRESHRATE);
		WIN_FULLSCREEN = (glfwGetWindowMonitor(currentWindow) == glfwGetPrimaryMonitor());
		glfwSwapInterval(SWAP_INTERVAL);
	}

	private void initPanel() {
		if (currentPanel != null) currentPanel.create();
	}

	private void showWindow() {
		if (WIN_MINIMIZED) glfwIconifyWindow(currentWindow);
		if (WIN_FOCUSED) glfwFocusWindow(currentWindow);
		if (WIN_VISIBLE) glfwShowWindow(currentWindow);
	}

	// Main Loop

	@Override
	protected void loop() {
		double nextTick, nextFrame;
		nextTick = nextFrame = getTime();
		int frameCount = 0;

		while (!glfwWindowShouldClose(currentWindow)) {
			double time = getTime();

			int frameSkip = 0;
			while (time > nextTick && frameSkip < MAX_FRAME_SKIP) {
				InputManager.clear(currentWindow);
				glfwPollEvents();

				update(time);

				ticks++;
				frameSkip++;
				nextTick += DELTA_UPS;
			}

			frameCount++;
			if (time > nextFrame) {
				fps = frameCount;
				frameCount = 0;
				nextFrame += 1.0;
			}

			render((time + DELTA_UPS - nextTick) / DELTA_UPS);
			sleep(time);
		}
	}

	private void update(double time) {
		onUpdate(time);
	}

	private void render(double ip) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		onRender(ip);
		glfwSwapBuffers(currentWindow);
	}

	private void sleep(double time) {
		if (DELTA_FPS > 0.0) {
			double now = getTime();
			while (now - time < DELTA_FPS) {
				Thread.yield();
				now = getTime();
			}
		} else {
			Thread.yield();
			try {Thread.sleep(SLEEP_DURATION);}
			catch (InterruptedException e) {}
		}
	}

	// Cleanup

	@Override
	protected void exit() {
		if (currentPanel != null) currentPanel.destroy();
		onExit();
		InputManager.destroy();
		glfwFreeCallbacks(currentWindow);
		glfwDestroyWindow(currentWindow);
	}

	public void close(boolean close) {glfwSetWindowShouldClose(currentWindow, close);}
	public void close() {close(true);}

	// Callback Methods

	protected void onInit() {}
	protected void onUpdate(double time) {if (currentPanel != null) currentPanel.update(time);}
	protected void onRender(double ip) {if (currentPanel != null) currentPanel.render(ip);}
	protected void onExit() {}

	protected void onKey(int key, int state, int mods, int scancode) {if (currentPanel != null) currentPanel.onKey(key, state, mods, scancode);}
	protected void onChar(char ch) {if (currentPanel != null) currentPanel.onChar(ch);}
	protected void onMouseButton(int button, int state, int mods) {if (currentPanel != null) currentPanel.onMouseButton(button, state, mods);}
	protected void onMouseMove(double x, double y) {if (currentPanel != null) currentPanel.onMouseMove(x, y);}
	protected void onMouseScroll(double x, double y) {if (currentPanel != null) currentPanel.onMouseScroll(x, y);}
	protected void onMouseEnter(boolean mouseEnter) {if (currentPanel != null) currentPanel.onMouseEnter(mouseEnter);}

	protected void onWindowClose() {if (currentPanel != null) currentPanel.onWindowClose();}
	protected void onWindowRefresh() {if (currentPanel != null) currentPanel.onWindowRefresh();}
	protected void onWindowResize(int width, int height) {if (currentPanel != null) currentPanel.onWindowResize(width, height);}
	protected void onScreenResize(int width, int height) {if (currentPanel != null) currentPanel.onScreenResize(width, height);}
	protected void onWindowMove(int x, int y) {if (currentPanel != null) currentPanel.onWindowMove(x, y);}
	protected void onWindowMinimize(boolean minimize) {if (currentPanel != null) currentPanel.onWindowMinimize(minimize);}
	protected void onWindowFocus(boolean focus) {if (currentPanel != null) currentPanel.onWindowFocus(focus);}

	// Setter Methods

	public void setPanel(FramePanel panel) {
		if (isInitialized()) {
			currentPanel.destroy();
			currentPanel = panel;
			currentPanel.create();
		} else {
			currentPanel = panel;
		}
	}

	public void setTitle(String title) {
		WIN_TITLE = title;
		if (isInitialized()) glfwSetWindowTitle(currentWindow, WIN_TITLE);
	}

	public void setIcon(String icon) {
		WIN_ICON = icon;
		if (isInitialized()) initIcon();
	}

	public void setWindowPos(int x, int y) {
		WIN_X = x;
		WIN_Y = y;
		if (isInitialized()) glfwSetWindowPos(currentWindow, WIN_X, WIN_Y);
	}

	public void setWindowSize(int width, int height) {
		WIN_WIDTH = width;
		WIN_HEIGHT = height;
		if (isInitialized()) glfwSetWindowSize(currentWindow, WIN_WIDTH, WIN_HEIGHT);
	}

	public void setVisible(boolean visible) {
		WIN_VISIBLE = visible;
		if (isInitialized()) {
			if (WIN_VISIBLE) glfwShowWindow(currentWindow);
			else glfwHideWindow(currentWindow);
		}
	}

	public void setFocused(boolean focused) {
		WIN_FOCUSED = focused;
		if (isInitialized()) {
			if (WIN_FOCUSED) glfwFocusWindow(currentWindow);
		}
	}

	public void setFloating(boolean floating) {
		WIN_FLOATING = floating;
	}

	public void setDecorated(boolean decorated) {
		WIN_DECORATED = decorated;
	}

	public void setResizable(boolean resizable) {
		WIN_RESIZABLE = resizable;
	}

	public void setMaximized(boolean maximized) {
		WIN_MAXIMIZED = maximized;
		if (isInitialized()) {
			if (WIN_MAXIMIZED) glfwMaximizeWindow(currentWindow);
			else glfwRestoreWindow(currentWindow);
		}
	}

	public void setMinimized(boolean minimized) {
		WIN_MINIMIZED = minimized;
		if (isInitialized()) {
			if (WIN_MINIMIZED) glfwIconifyWindow(currentWindow);
			else glfwRestoreWindow(currentWindow);
		}
	}

	public void setAutoIconify(boolean autoiconify) {
		WIN_AUTOICONIFY = autoiconify;
	}

	public void setFullScreen(boolean fullscreen) {
		WIN_FULLSCREEN = fullscreen;
		if (isInitialized()) doFullScreen(WIN_FULLSCREEN);
	}

	public void setScreenCrop(boolean screencrop) {
		WIN_SCREENCROP = screencrop;
	}

	public void setVSync(boolean vsync) {
		SWAP_INTERVAL = vsync ? 1 : 0;
		if (isInitialized()) glfwSwapInterval(SWAP_INTERVAL);
	}

	// Getter Methods

	public long getWindow() {return currentWindow;}
	public FramePanel getPanel() {return currentPanel;}

	public String getTitle() {return WIN_TITLE;}
	public String getIcon() {return WIN_ICON;}

	public int getWindowX() {return WIN_X;}
	public int getWindowY() {return WIN_Y;}

	public int getWindowWidth() {return WIN_WIDTH;}
	public int getWindowHeight() {return WIN_HEIGHT;}
	public double getWindowRatio() {return (double)WIN_WIDTH/(double)WIN_HEIGHT;}

	public int getScreenWidth() {return SCR_WIDTH;}
	public int getScreenHeight() {return SCR_HEIGHT;}
	public double getScreenRatio() {return (double)SCR_WIDTH/(double)SCR_HEIGHT;}

	public int getMonitorWidth() {return MNT_WIDTH;}
	public int getMonitorHeight() {return MNT_HEIGHT;}
	public double getMonitorRatio() {return (double)MNT_WIDTH/(double)MNT_HEIGHT;}

	public int getRefreshRate() {return MNT_REFRESHRATE;}

	public boolean isVisible() {return (glfwGetWindowAttrib(currentWindow, GLFW_VISIBLE) == GL_TRUE);}
	public boolean isFocused() {return (glfwGetWindowAttrib(currentWindow, GLFW_FOCUSED) == GL_TRUE);}
	public boolean isFloating() {return (glfwGetWindowAttrib(currentWindow, GLFW_FLOATING) == GL_TRUE);}
	public boolean isDecorated() {return (glfwGetWindowAttrib(currentWindow, GLFW_DECORATED) == GL_TRUE);}
	public boolean isResizable() {return (glfwGetWindowAttrib(currentWindow, GLFW_RESIZABLE) == GL_TRUE);}
	public boolean isMaximized() {return (glfwGetWindowAttrib(currentWindow, GLFW_MAXIMIZED) == GL_TRUE);}
	public boolean isMinimized() {return (glfwGetWindowAttrib(currentWindow, GLFW_ICONIFIED) == GL_TRUE);}
	public boolean isAutoIconify() {return (glfwGetWindowAttrib(currentWindow, GLFW_AUTO_ICONIFY) == GL_TRUE);}
	public boolean isFullScreen() {return WIN_FULLSCREEN;}
	public boolean isScreenCrop() {return WIN_SCREENCROP;}
	public boolean isVSync() {return !(SWAP_INTERVAL == 0);}

	// Timing Methods

	public void setTargetFPS(double fps) {
		if (fps > 0.0) {
			TARGET_FPS = fps;
			DELTA_FPS = (1.0/TARGET_FPS);
		} else {
			TARGET_FPS = 0.0;
			DELTA_FPS = 0.0;
		}
	}
	public void setTargetUPS(double ups) {
		TARGET_UPS = ups;
		DELTA_UPS = (1.0/TARGET_UPS);
	}

	public void setSleepDuration(int duration) {SLEEP_DURATION = duration;}
	public void setMaxFrameSkip(int mfs) {MAX_FRAME_SKIP = mfs;}

	public double getTime() {return glfwGetTime();}
	public long getTime(int resolution) {return (long)(glfwGetTime()*Math.pow(10, resolution));}
	public long getTicks() {return ticks;}
	public double getFPS() {return fps;}
	public double getTargetFPS() {return TARGET_FPS;}
	public double getDeltaFPS() {return DELTA_FPS;}
	public double getTargetUPS() {return TARGET_UPS;}
	public double getDeltaUPS() {return DELTA_UPS;}
	public int getSleepDuration() {return SLEEP_DURATION;}
	public int getMaxFrameSkip() {return MAX_FRAME_SKIP;}

	// Utility Methods

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;
		Path path = Paths.get(resource);
		if ( Files.isReadable(path) ) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
				while ( fc.read(buffer) != -1 ) ;
			}
		} else {
			try (
				InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)
			) {
				buffer = createByteBuffer(bufferSize);

				while ( true ) {
					int bytes = rbc.read(buffer);
					if ( bytes == -1 )
						break;
					if ( buffer.remaining() == 0 )
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
				}
			}
		}
		buffer.flip();
		return buffer;
	}

}
