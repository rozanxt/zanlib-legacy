package zan.lib.input;

import java.util.ArrayList;

public class InputManager {
	
	public static final int IM_KEY_UNKNOWN = 0xFFFFFFFF;
	
	public static final int IM_RELEASE = 0x0;
	public static final int IM_PRESS = 0x1;
	public static final int IM_REPEAT = 0x2;
	
	public static final int
		IM_KEY_SPACE         = 0x20,
		IM_KEY_APOSTROPHE    = 0x27,
		IM_KEY_COMMA         = 0x2C,
		IM_KEY_MINUS         = 0x2D,
		IM_KEY_PERIOD        = 0x2E,
		IM_KEY_SLASH         = 0x2F,
		IM_KEY_0             = 0x30,
		IM_KEY_1             = 0x31,
		IM_KEY_2             = 0x32,
		IM_KEY_3             = 0x33,
		IM_KEY_4             = 0x34,
		IM_KEY_5             = 0x35,
		IM_KEY_6             = 0x36,
		IM_KEY_7             = 0x37,
		IM_KEY_8             = 0x38,
		IM_KEY_9             = 0x39,
		IM_KEY_SEMICOLON     = 0x3B,
		IM_KEY_EQUAL         = 0x3D,
		IM_KEY_A             = 0x41,
		IM_KEY_B             = 0x42,
		IM_KEY_C             = 0x43,
		IM_KEY_D             = 0x44,
		IM_KEY_E             = 0x45,
		IM_KEY_F             = 0x46,
		IM_KEY_G             = 0x47,
		IM_KEY_H             = 0x48,
		IM_KEY_I             = 0x49,
		IM_KEY_J             = 0x4A,
		IM_KEY_K             = 0x4B,
		IM_KEY_L             = 0x4C,
		IM_KEY_M             = 0x4D,
		IM_KEY_N             = 0x4E,
		IM_KEY_O             = 0x4F,
		IM_KEY_P             = 0x50,
		IM_KEY_Q             = 0x51,
		IM_KEY_R             = 0x52,
		IM_KEY_S             = 0x53,
		IM_KEY_T             = 0x54,
		IM_KEY_U             = 0x55,
		IM_KEY_V             = 0x56,
		IM_KEY_W             = 0x57,
		IM_KEY_X             = 0x58,
		IM_KEY_Y             = 0x59,
		IM_KEY_Z             = 0x5A,
		IM_KEY_LEFT_BRACKET  = 0x5B,
		IM_KEY_BACKSLASH     = 0x5C,
		IM_KEY_RIGHT_BRACKET = 0x5D,
		IM_KEY_GRAVE_ACCENT  = 0x60,
		IM_KEY_WORLD_1       = 0xA1,
		IM_KEY_WORLD_2       = 0xA2;
	
	public static final int
		IM_KEY_ESCAPE        = 0x100,
		IM_KEY_ENTER         = 0x101,
		IM_KEY_TAB           = 0x102,
		IM_KEY_BACKSPACE     = 0x103,
		IM_KEY_INSERT        = 0x104,
		IM_KEY_DELETE        = 0x105,
		IM_KEY_RIGHT         = 0x106,
		IM_KEY_LEFT          = 0x107,
		IM_KEY_DOWN          = 0x108,
		IM_KEY_UP            = 0x109,
		IM_KEY_PAGE_UP       = 0x10A,
		IM_KEY_PAGE_DOWN     = 0x10B,
		IM_KEY_HOME          = 0x10C,
		IM_KEY_END           = 0x10D,
		IM_KEY_CAPS_LOCK     = 0x118,
		IM_KEY_SCROLL_LOCK   = 0x119,
		IM_KEY_NUM_LOCK      = 0x11A,
		IM_KEY_PRINT_SCREEN  = 0x11B,
		IM_KEY_PAUSE         = 0x11C,
		IM_KEY_F1            = 0x122,
		IM_KEY_F2            = 0x123,
		IM_KEY_F3            = 0x124,
		IM_KEY_F4            = 0x125,
		IM_KEY_F5            = 0x126,
		IM_KEY_F6            = 0x127,
		IM_KEY_F7            = 0x128,
		IM_KEY_F8            = 0x129,
		IM_KEY_F9            = 0x12A,
		IM_KEY_F10           = 0x12B,
		IM_KEY_F11           = 0x12C,
		IM_KEY_F12           = 0x12D,
		IM_KEY_F13           = 0x12E,
		IM_KEY_F14           = 0x12F,
		IM_KEY_F15           = 0x130,
		IM_KEY_F16           = 0x131,
		IM_KEY_F17           = 0x132,
		IM_KEY_F18           = 0x133,
		IM_KEY_F19           = 0x134,
		IM_KEY_F20           = 0x135,
		IM_KEY_F21           = 0x136,
		IM_KEY_F22           = 0x137,
		IM_KEY_F23           = 0x138,
		IM_KEY_F24           = 0x139,
		IM_KEY_F25           = 0x13A,
		IM_KEY_KP_0          = 0x140,
		IM_KEY_KP_1          = 0x141,
		IM_KEY_KP_2          = 0x142,
		IM_KEY_KP_3          = 0x143,
		IM_KEY_KP_4          = 0x144,
		IM_KEY_KP_5          = 0x145,
		IM_KEY_KP_6          = 0x146,
		IM_KEY_KP_7          = 0x147,
		IM_KEY_KP_8          = 0x148,
		IM_KEY_KP_9          = 0x149,
		IM_KEY_KP_DECIMAL    = 0x14A,
		IM_KEY_KP_DIVIDE     = 0x14B,
		IM_KEY_KP_MULTIPLY   = 0x14C,
		IM_KEY_KP_SUBTRACT   = 0x14D,
		IM_KEY_KP_ADD        = 0x14E,
		IM_KEY_KP_ENTER      = 0x14F,
		IM_KEY_KP_EQUAL      = 0x150,
		IM_KEY_LEFT_SHIFT    = 0x154,
		IM_KEY_LEFT_CONTROL  = 0x155,
		IM_KEY_LEFT_ALT      = 0x156,
		IM_KEY_LEFT_SUPER    = 0x157,
		IM_KEY_RIGHT_SHIFT   = 0x158,
		IM_KEY_RIGHT_CONTROL = 0x159,
		IM_KEY_RIGHT_ALT     = 0x15A,
		IM_KEY_RIGHT_SUPER   = 0x15B,
		IM_KEY_MENU          = 0x15C,
		IM_KEY_LAST          = IM_KEY_MENU;
	
	public static final int IM_MOD_SHIFT = 0x1;
	public static final int IM_MOD_CONTROL = 0x2;
	public static final int IM_MOD_ALT = 0x4;
	public static final int IM_MOD_SUPER = 0x8;
	
	public static final int
		IM_MOUSE_BUTTON_1      = 0x0,
		IM_MOUSE_BUTTON_2      = 0x1,
		IM_MOUSE_BUTTON_3      = 0x2,
		IM_MOUSE_BUTTON_4      = 0x3,
		IM_MOUSE_BUTTON_5      = 0x4,
		IM_MOUSE_BUTTON_6      = 0x5,
		IM_MOUSE_BUTTON_7      = 0x6,
		IM_MOUSE_BUTTON_8      = 0x7,
		IM_MOUSE_BUTTON_LAST   = IM_MOUSE_BUTTON_8,
		IM_MOUSE_BUTTON_LEFT   = IM_MOUSE_BUTTON_1,
		IM_MOUSE_BUTTON_RIGHT  = IM_MOUSE_BUTTON_2,
		IM_MOUSE_BUTTON_MIDDLE = IM_MOUSE_BUTTON_3;
	
	public static final int
		IM_MOUSE_NORMAL   = 0x34001,
		IM_MOUSE_HIDDEN   = 0x34002,
		IM_MOUSE_DISABLED = 0x34003;
	
	private static ArrayList<WindowInput> windowInputs;
	
	private static long currentWindow;
	
	public static void init() {
		windowInputs = new ArrayList<WindowInput>();
		currentWindow = 0L;
	}
	
	public static boolean setWindow(long window) {
		if (window == currentWindow) return false;
		for (int i=0;i<windowInputs.size();i++) {
			if (window == windowInputs.get(i).getWindow()) {
				currentWindow = window;
				return true;
			}
		}
		windowInputs.add(new WindowInput(window));
		currentWindow = window;
		return true;
	}
	
	private static WindowInput getWindow(long window) {
		for (int i=0;i<windowInputs.size();i++) {
			WindowInput windowInput = windowInputs.get(i);
			if (window == windowInput.getWindow()) return windowInput;
		}
		return null;
	}
	
	/** @deprecated Replaced by invoke callback methods */
	public static void poll() {getWindow(currentWindow).poll();}
	/** @deprecated Replaced by invoke callback methods */
	public static void poll(long window) {getWindow(window).poll();}
	
	public static void clear() {getWindow(currentWindow).clear();}
	public static void clear(long window) {getWindow(window).clear();}
	
	public static void invokeKeys(int key, int state, int mods) {getWindow(currentWindow).invokeKeys(key, state, mods);}
	public static void invokeKeys(long window, int key, int state, int mods) {getWindow(window).invokeKeys(key, state, mods);}
	public static void invokeChars(int ch) {getWindow(currentWindow).invokeChars(ch);}
	public static void invokeChars(long window, int ch) {getWindow(window).invokeChars(ch);}
	public static void invokeMouseButtons(int button, int state, int mods) {getWindow(currentWindow).invokeMouseButtons(button, state, mods);}
	public static void invokeMouseButtons(long window, int button, int state, int mods) {getWindow(window).invokeMouseButtons(button, state, mods);}
	public static void invokeMousePos(double mouseX, double mouseY) {getWindow(currentWindow).invokeMousePos(mouseX, mouseY);}
	public static void invokeMousePos(long window, double mouseX, double mouseY) {getWindow(window).invokeMousePos(mouseX, mouseY);}
	public static void invokeMouseScroll(double mouseScroll) {getWindow(currentWindow).invokeMouseScroll(mouseScroll);}
	public static void invokeMouseScroll(long window, double mouseScroll) {getWindow(window).invokeMouseScroll(mouseScroll);}
	public static void invokeMouseEnter(boolean mouseEnter) {getWindow(currentWindow).invokeMouseEnter(mouseEnter);}
	public static void invokeMouseEnter(long window, boolean mouseEnter) {getWindow(window).invokeMouseEnter(mouseEnter);}
	
	public static void setMouseMode(int mode) {getWindow(currentWindow).setMouseMode(mode);}
	public static void setMouseMode(long window, int mode) {getWindow(window).setMouseMode(mode);}
	
	public static boolean isKeyMods(int key, int mods) {return getWindow(currentWindow).isKeyMods(key, mods);}
	public static boolean isKeyMods(long window, int key, int mods) {return getWindow(window).isKeyMods(key, mods);}
	public static boolean isKeyPressed(int key) {return getWindow(currentWindow).isKeyPressed(key);}
	public static boolean isKeyPressed(long window, int key) {return getWindow(window).isKeyPressed(key);}
	public static boolean isKeyReleased(int key) {return getWindow(currentWindow).isKeyReleased(key);}
	public static boolean isKeyReleased(long window, int key) {return getWindow(window).isKeyReleased(key);}
	public static boolean isKeyRepeated(int key) {return getWindow(currentWindow).isKeyRepeated(key);}
	public static boolean isKeyRepeated(long window, int key) {return getWindow(window).isKeyRepeated(key);}
	public static boolean isKeyDown(int key) {return getWindow(currentWindow).isKeyDown(key);}
	public static boolean isKeyDown(long window, int key) {return getWindow(window).isKeyDown(key);}
	
	public static ArrayList<Character> getCharEvents() {return getWindow(currentWindow).getCharEvents();}
	public static ArrayList<Character> getCharEvents(long window) {return getWindow(window).getCharEvents();}
	
	public static boolean isMouseMods(int button, int mods) {return getWindow(currentWindow).isMouseMods(button, mods);}
	public static boolean isMouseMods(long window, int button, int mods) {return getWindow(window).isMouseMods(button, mods);}
	public static boolean isMousePressed(int button) {return getWindow(currentWindow).isMousePressed(button);}
	public static boolean isMousePressed(long window, int button) {return getWindow(window).isMousePressed(button);}
	public static boolean isMouseReleased(int button) {return getWindow(currentWindow).isMouseReleased(button);}
	public static boolean isMouseReleased(long window, int button) {return getWindow(window).isMouseReleased(button);}
	public static boolean isMouseDown(int button) {return getWindow(currentWindow).isMouseDown(button);}
	public static boolean isMouseDown(long window, int button) {return getWindow(window).isMouseDown(button);}
	public static boolean isMouseEntered() {return getWindow(currentWindow).isMouseEntered();}
	public static boolean isMouseEntered(long window) {return getWindow(window).isMouseEntered();}
	public static boolean isMouseLeft() {return getWindow(currentWindow).isMouseLeft();}
	public static boolean isMouseLeft(long window) {return getWindow(window).isMouseLeft();}
	
	public static double getMouseX() {return getWindow(currentWindow).getMouseX();}
	public static double getMouseX(long window) {return getWindow(window).getMouseX();}
	public static double getMouseY() {return getWindow(currentWindow).getMouseY();}
	public static double getMouseY(long window) {return getWindow(window).getMouseY();}
	public static double getMouseDX() {return getWindow(currentWindow).getMouseDX();}
	public static double getMouseDX(long window) {return getWindow(window).getMouseDX();}
	public static double getMouseDY() {return getWindow(currentWindow).getMouseDY();}
	public static double getMouseDY(long window) {return getWindow(window).getMouseDY();}
	public static double getMouseScroll() {return getWindow(currentWindow).getMouseScroll();}
	public static double getMouseScroll(long window) {return getWindow(window).getMouseScroll();}
	
}
