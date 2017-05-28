package zan.lib.core;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import zan.lib.input.InputManager;
import zan.lib.util.IconLoader;

public abstract class CoreEngine {

	// WINDOW VARIABLES

	private String WIN_TITLE = "";
	private String WIN_ICON = "";

	private int SCR_X = -1;
	private int SCR_Y = -1;

	private int WIN_WIDTH = 640;
	private int WIN_HEIGHT = 480;
	private int MNT_WIDTH = 0;
	private int MNT_HEIGHT = 0;
	private int SCR_WIDTH = WIN_WIDTH;
	private int SCR_HEIGHT = WIN_HEIGHT;

	// WINDOW FLAGS

	private boolean SCR_FULL = false;
	private boolean SCR_CROP = false;
	private boolean SCR_VSYNC = false;
	private boolean SCR_RESIZABLE = false;

	private boolean REQ_WINDOW_REFRESH = false;

	// TIMING VARIABLES

	private long ticks = 0L;
	private int fps = 0;
	private int ups = 0;
	private int TARGET_FPS = 120;
	private double TARGET_UPS = 50.0;
	private double UPS_INTERVAL = (1.0/TARGET_UPS);

	// WINDOW & PANEL

	private BasePanel corePanel = null;

	private boolean running = false;

	// RUN METHOD

	public void run() {
		if (running) return;
		init();
		loop();
		destroy();
	}

	// CLEANUP

	private void destroy() {
		if (corePanel != null) corePanel.destroy();
		onDestroy();
		InputManager.destroy();
		Display.destroy();
		System.exit(0);
	}

	public void close() {
		running = false;
	}

	// INITIALIZATION

	private void init() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		MNT_WIDTH = (int)screenSize.getWidth();
		MNT_HEIGHT = (int)screenSize.getHeight();
		if (SCR_X == -1) SCR_X = (MNT_WIDTH-WIN_WIDTH)/2;
		if (SCR_Y == -1) SCR_Y = (MNT_HEIGHT-WIN_HEIGHT)/2;

		try {
			setupDisplayMode();
			Display.setLocation(SCR_X, SCR_Y);
			Display.setVSyncEnabled(SCR_VSYNC);
			Display.setResizable(SCR_RESIZABLE);
			Display.setTitle(WIN_TITLE);
			if (!WIN_ICON.isEmpty()) Display.setIcon(IconLoader.loadIcon(WIN_ICON));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		InputManager.init();
		onInit();
		running = true;

		if (corePanel == null) {
			System.err.println("Error in 'CoreEngine': No panels set!");
			close();
		} else corePanel.init();
	}

	// MAIN LOOP

	private void loop() {
		double timeThen = getTime();
		double timeNow;
		double deltaTime;
		double accumulator = 0.0;

		double timeCount = 0.0;
		int fpsCount = 0;
		int upsCount = 0;

		while (running) {
			timeNow = getTime();
			deltaTime = timeNow - timeThen;
			timeThen = timeNow;

			accumulator += deltaTime;
			timeCount += deltaTime;

			while (accumulator >= UPS_INTERVAL) {
				InputManager.poll();

				update(timeNow);

				upsCount++;
				ticks++;
				accumulator -= UPS_INTERVAL;
			}

			render(accumulator / UPS_INTERVAL);
			fpsCount++;

			if (timeCount > 1.0) {
				fps = fpsCount;
				fpsCount = 0;
				ups = upsCount;
				upsCount = 0;
				timeCount -= 1.0;
			}

			Display.update();
			if (TARGET_FPS != 0) Display.sync(TARGET_FPS);

			check();

			if (REQ_WINDOW_REFRESH) setupDisplayMode();
			if (Display.wasResized()) {
				SCR_WIDTH = getDisplayWidth();
				SCR_HEIGHT = getDisplayHeight();
				corePanel.onScreenResize(SCR_WIDTH, SCR_HEIGHT);
			}
			if (Display.isCloseRequested()) close();
		}
	}

	private void update(double time) {
		corePanel.update(time);
	}

	private void render(double ip) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		corePanel.render(ip);
	}

	private void check() {
		BasePanel nextPanel = corePanel.changePanel();
		if (nextPanel != null) setPanel(nextPanel);
	}

	// SETTERS

	public void setPanel(BasePanel panel) {
		if (running && corePanel != null) corePanel.destroy();
		corePanel = panel;
		if (running) corePanel.init();
	}

	public void setTitle(String title) {
		WIN_TITLE = title;
		if (running) Display.setTitle(WIN_TITLE);
	}

	public void setIcon(String filename) {
		WIN_ICON = filename;
		if (running) Display.setIcon(IconLoader.loadIcon(WIN_ICON));
	}

	public void setWindowPos(int x, int y) {
		SCR_X = x;
		SCR_Y = y;
		if (running) Display.setLocation(SCR_X, SCR_Y);
	}

	public void setResizable(boolean resizable) {
		SCR_RESIZABLE = resizable;
		if (running) Display.setResizable(SCR_RESIZABLE);
	}

	public void setScreenSize(int width, int height) {
		WIN_WIDTH = width;
		WIN_HEIGHT = height;
		SCR_WIDTH = WIN_WIDTH;
		SCR_HEIGHT = WIN_HEIGHT;
		if (running) REQ_WINDOW_REFRESH = true;
	}

	public void setFullScreen(boolean fullscreen) {
		SCR_FULL = fullscreen;
		if (running) REQ_WINDOW_REFRESH = true;
	}
	public void toggleFullScreen() {setFullScreen(!SCR_FULL);}

	public void setScreenCrop(boolean screencrop) {
		SCR_CROP = screencrop;
		if (running) REQ_WINDOW_REFRESH = true;
	}
	public void toggleScreenCrop() {setScreenCrop(SCR_CROP);}

	public void setVSync(boolean vsync) {
		SCR_VSYNC = vsync;
		if (running) Display.setVSyncEnabled(SCR_VSYNC);
	}
	public void toggleVSync() {setVSync(!SCR_VSYNC);}

	// DISPLAY MODE

	private void setupDisplayMode() {
		if (SCR_FULL && !SCR_CROP) {
			SCR_WIDTH = MNT_WIDTH;
			SCR_HEIGHT = MNT_HEIGHT;
		} else {
			SCR_WIDTH = WIN_WIDTH;
			SCR_HEIGHT = WIN_HEIGHT;
		}

		setDisplayMode(SCR_WIDTH, SCR_HEIGHT, SCR_FULL);
		SCR_FULL = Display.isFullscreen();

		if (running) corePanel.onScreenResize(SCR_WIDTH, SCR_HEIGHT);
		REQ_WINDOW_REFRESH = false;
	}

	private void setDisplayMode(int width, int height, boolean fullscreen) {
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen)) return;

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
						    (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x" + height + " fullscreen=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + ":\n" + e);
		}
	}

	// TIMING METHODS

	public void setTargetFPS(int fps) {TARGET_FPS = fps;}
	public void setTargetUPS(double ups) {
		TARGET_UPS = ups;
		UPS_INTERVAL = (1.0/TARGET_UPS);
	}

	// CALLBACK METHODS

	protected void onInit() {}
	protected void onDestroy() {}

	// GETTERS

	public boolean isRunning() {return running;}

	public BasePanel getPanel() {return corePanel;}

	public String getTitle() {return Display.getTitle();}

	public int getWindowX() {return Display.getX();}
	public int getWindowY() {return Display.getY();}

	public int getWindowWidth() {return WIN_WIDTH;}
	public int getWindowHeight() {return WIN_HEIGHT;}
	public double getWindowRatio() {return (double)WIN_WIDTH/(double)WIN_HEIGHT;}

	public int getMonitorWidth() {return MNT_WIDTH;}
	public int getMonitorHeight() {return MNT_HEIGHT;}
	public double getMonitorRatio() {return (double)MNT_WIDTH/(double)MNT_HEIGHT;}

	public int getScreenWidth() {return SCR_WIDTH;}
	public int getScreenHeight() {return SCR_HEIGHT;}
	public double getScreenRatio() {return (double)SCR_WIDTH/(double)SCR_HEIGHT;}

	public int getDisplayWidth() {return Display.getWidth();}
	public int getDisplayHeight() {return Display.getHeight();}
	public double getDisplayRatio() {return (double)Display.getWidth()/Display.getHeight();}

	public boolean isFullScreen() {return SCR_FULL;}
	public boolean isScreenCrop() {return SCR_CROP;}
	public boolean isVSync() {return SCR_VSYNC;}

	public boolean isVisible() {return Display.isVisible();}
	public boolean isResizable() {return Display.isResizable();}

	public double getTime() {return System.nanoTime() / 1000000000.0;}
	public long getTicks() {return ticks;}
	public int getFPS() {return fps;}
	public int getUPS() {return ups;}
	public int getTargetFPS() {return TARGET_FPS;}
	public double getTargetUPS() {return TARGET_UPS;}
	public double getUpdateInterval() {return UPS_INTERVAL;}

}
