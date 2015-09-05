package zan.lib.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class WindowInput {

	private final long window;

	private final KeyEvent[] keyEvents;
	private final MouseEvent[] mouseEvents;
	private final ArrayList<Character> charEvents;

	private DoubleBuffer rawX, rawY;
	private double mouseX, mouseY;
	private double mouseDX, mouseDY;
	private double mouseScroll;
	private boolean mouseEntered;
	private boolean mouseLeft;

	public WindowInput(long window) {
		this.window = window;

		keyEvents = new KeyEvent[GLFW_KEY_LAST];
		for (int i=0;i<GLFW_KEY_LAST;i++) keyEvents[i] = new KeyEvent(i);
		charEvents = new ArrayList<Character>();

		mouseEvents = new MouseEvent[GLFW_MOUSE_BUTTON_LAST];
		for (int i=0;i<GLFW_MOUSE_BUTTON_LAST;i++) mouseEvents[i] = new MouseEvent(i);
		rawX = BufferUtils.createDoubleBuffer(1);
		rawY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, rawX, rawY);
		mouseX = rawX.get(0);
		mouseY = rawY.get(0);
		mouseDX = 0.0;
		mouseDY = 0.0;
		mouseScroll = 0.0;
		mouseEntered = false;
		mouseLeft = false;
	}

	/** @deprecated Replaced by invoke callback methods. */
	public void poll() {
		for (int i=0;i<GLFW_KEY_LAST;i++) keyEvents[i].setPressed((glfwGetKey(window, i) == 1));
		for (int i=0;i<GLFW_MOUSE_BUTTON_LAST;i++) mouseEvents[i].setPressed((glfwGetMouseButton(window, i) == 1));
		invokeRawMousePos();
	}

	public void clear() {
		for (int i=0;i<GLFW_KEY_LAST;i++) keyEvents[i].clear();
		charEvents.clear();

		for (int i=0;i<GLFW_MOUSE_BUTTON_LAST;i++) mouseEvents[i].clear();
		mouseDX = 0.0;
		mouseDY = 0.0;
		mouseScroll = 0.0;
		mouseEntered = false;
		mouseLeft = false;
	}

	public void invokeKeys(int key, int state, int mods) {
		KeyEvent keyEvent = keyEvents[key];
		if (state == GLFW_PRESS) {
			keyEvent.setPressed(true);
		} else if (state == GLFW_RELEASE) {
			keyEvent.setReleased(true);
		} else if (state == GLFW_REPEAT)  {
			keyEvent.setRepeated(true);
		}
		keyEvent.setMods(mods);
	}

	public void invokeChars(char ch) {
		charEvents.add(ch);
	}

	public void invokeMouseButtons(int key, int state, int mods) {
		MouseEvent mouseEvent = mouseEvents[key];
		if (state == GLFW_PRESS) {
			mouseEvent.setPressed(true);
		} else if (state == GLFW_RELEASE) {
			mouseEvent.setReleased(true);
		}
		mouseEvent.setMods(mods);
	}

	public void invokeMousePos(double mouseX, double mouseY) {
		mouseDX = mouseX - this.mouseX;
		mouseDY = mouseY - this.mouseY;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	/** @deprecated Use {@link #invokeMousePos(double,double)} instead. */
	public void invokeRawMousePos() {
		glfwGetCursorPos(window, rawX, rawY);
		mouseDX = rawX.get(0) - mouseX;
		mouseDY = rawY.get(0) - mouseY;
		mouseX = rawX.get(0);
		mouseY = rawY.get(0);
	}

	public void invokeMouseScroll(double mouseScroll) {
		this.mouseScroll = mouseScroll;
	}

	public void invokeMouseEnter(boolean mouseEnter) {
		if (mouseEnter) mouseEntered = true;
		else mouseLeft = true;
	}

	public void setMouseMode(int mode) {glfwSetInputMode(window, GLFW_CURSOR, mode);}

	public long getWindow() {return window;}

	public boolean isKeyMods(int key, int mods) {return (keyEvents[key].getMods() == mods);}
	public boolean isKeyPressed(int key) {return keyEvents[key].isPressed();}
	public boolean isKeyReleased(int key) {return keyEvents[key].isReleased();}
	public boolean isKeyRepeated(int key) {return keyEvents[key].isRepeated();}
	public boolean isKeyDown(int key) {return (glfwGetKey(window, key) == 1);}

	public ArrayList<Character> getCharEvents() {return charEvents;}

	public boolean isMouseMods(int button, int mods) {return (mouseEvents[button].getMods() == mods);}
	public boolean isMousePressed(int button) {return mouseEvents[button].isPressed();}
	public boolean isMouseReleased(int button) {return mouseEvents[button].isReleased();}
	public boolean isMouseDown(int button) {return (glfwGetMouseButton(window, button) == 1);}
	public boolean isMouseEntered() {return mouseEntered;}
	public boolean isMouseLeft() {return mouseLeft;}

	public double getMouseX() {return mouseX;}
	public double getMouseY() {return mouseY;}
	public double getMouseDX() {return mouseDX;}
	public double getMouseDY() {return mouseDY;}
	public double getMouseScroll() {return mouseScroll;}

}
