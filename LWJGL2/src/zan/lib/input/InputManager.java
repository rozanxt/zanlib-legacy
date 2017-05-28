package zan.lib.input;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputManager {

	public static final int IM_CHAR_NONE           = '\0';
	public static final int IM_KEY_NONE            = 0x00;
	public static final int IM_KEY_ESCAPE          = 0x01;
	public static final int IM_KEY_1               = 0x02;
	public static final int IM_KEY_2               = 0x03;
	public static final int IM_KEY_3               = 0x04;
	public static final int IM_KEY_4               = 0x05;
	public static final int IM_KEY_5               = 0x06;
	public static final int IM_KEY_6               = 0x07;
	public static final int IM_KEY_7               = 0x08;
	public static final int IM_KEY_8               = 0x09;
	public static final int IM_KEY_9               = 0x0A;
	public static final int IM_KEY_0               = 0x0B;
	public static final int IM_KEY_MINUS           = 0x0C; /* - on main keyboard */
	public static final int IM_KEY_EQUALS          = 0x0D;
	public static final int IM_KEY_BACKSPACE       = 0x0E; /* backspace */
	public static final int IM_KEY_TAB             = 0x0F;
	public static final int IM_KEY_Q               = 0x10;
	public static final int IM_KEY_W               = 0x11;
	public static final int IM_KEY_E               = 0x12;
	public static final int IM_KEY_R               = 0x13;
	public static final int IM_KEY_T               = 0x14;
	public static final int IM_KEY_Y               = 0x15;
	public static final int IM_KEY_U               = 0x16;
	public static final int IM_KEY_I               = 0x17;
	public static final int IM_KEY_O               = 0x18;
	public static final int IM_KEY_P               = 0x19;
	public static final int IM_KEY_LBRACKET        = 0x1A;
	public static final int IM_KEY_RBRACKET        = 0x1B;
	public static final int IM_KEY_ENTER           = 0x1C; /* Enter on main keyboard */
	public static final int IM_KEY_LCONTROL        = 0x1D;
	public static final int IM_KEY_A               = 0x1E;
	public static final int IM_KEY_S               = 0x1F;
	public static final int IM_KEY_D               = 0x20;
	public static final int IM_KEY_F               = 0x21;
	public static final int IM_KEY_G               = 0x22;
	public static final int IM_KEY_H               = 0x23;
	public static final int IM_KEY_J               = 0x24;
	public static final int IM_KEY_K               = 0x25;
	public static final int IM_KEY_L               = 0x26;
	public static final int IM_KEY_SEMICOLON       = 0x27;
	public static final int IM_KEY_APOSTROPHE      = 0x28;
	public static final int IM_KEY_GRAVE           = 0x29; /* accent grave */
	public static final int IM_KEY_LSHIFT          = 0x2A;
	public static final int IM_KEY_BACKSLASH       = 0x2B;
	public static final int IM_KEY_Z               = 0x2C;
	public static final int IM_KEY_X               = 0x2D;
	public static final int IM_KEY_C               = 0x2E;
	public static final int IM_KEY_V               = 0x2F;
	public static final int IM_KEY_B               = 0x30;
	public static final int IM_KEY_N               = 0x31;
	public static final int IM_KEY_M               = 0x32;
	public static final int IM_KEY_COMMA           = 0x33;
	public static final int IM_KEY_PERIOD          = 0x34; /* . on main keyboard */
	public static final int IM_KEY_SLASH           = 0x35; /* / on main keyboard */
	public static final int IM_KEY_RSHIFT          = 0x36;
	public static final int IM_KEY_MULTIPLY        = 0x37; /* * on numeric keypad */
	public static final int IM_KEY_LMENU           = 0x38; /* left Alt */
	public static final int IM_KEY_SPACE           = 0x39;
	public static final int IM_KEY_CAPITAL         = 0x3A;
	public static final int IM_KEY_F1              = 0x3B;
	public static final int IM_KEY_F2              = 0x3C;
	public static final int IM_KEY_F3              = 0x3D;
	public static final int IM_KEY_F4              = 0x3E;
	public static final int IM_KEY_F5              = 0x3F;
	public static final int IM_KEY_F6              = 0x40;
	public static final int IM_KEY_F7              = 0x41;
	public static final int IM_KEY_F8              = 0x42;
	public static final int IM_KEY_F9              = 0x43;
	public static final int IM_KEY_F10             = 0x44;
	public static final int IM_KEY_NUMLOCK         = 0x45;
	public static final int IM_KEY_SCROLL          = 0x46; /* Scroll Lock */
	public static final int IM_KEY_NUMPAD7         = 0x47;
	public static final int IM_KEY_NUMPAD8         = 0x48;
	public static final int IM_KEY_NUMPAD9         = 0x49;
	public static final int IM_KEY_SUBTRACT        = 0x4A; /* - on numeric keypad */
	public static final int IM_KEY_NUMPAD4         = 0x4B;
	public static final int IM_KEY_NUMPAD5         = 0x4C;
	public static final int IM_KEY_NUMPAD6         = 0x4D;
	public static final int IM_KEY_ADD             = 0x4E; /* + on numeric keypad */
	public static final int IM_KEY_NUMPAD1         = 0x4F;
	public static final int IM_KEY_NUMPAD2         = 0x50;
	public static final int IM_KEY_NUMPAD3         = 0x51;
	public static final int IM_KEY_NUMPAD0         = 0x52;
	public static final int IM_KEY_DECIMAL         = 0x53; /* . on numeric keypad */
	public static final int IM_KEY_F11             = 0x57;
	public static final int IM_KEY_F12             = 0x58;
	public static final int IM_KEY_F13             = 0x64; /*                     (NEC PC98) */
	public static final int IM_KEY_F14             = 0x65; /*                     (NEC PC98) */
	public static final int IM_KEY_F15             = 0x66; /*                     (NEC PC98) */
	public static final int IM_KEY_F16             = 0x67; /* Extended Function keys - (Mac) */
	public static final int IM_KEY_F17             = 0x68;
	public static final int IM_KEY_F18             = 0x69;
	public static final int IM_KEY_KANA            = 0x70; /* (Japanese keyboard)            */
	public static final int IM_KEY_F19             = 0x71; /* Extended Function keys - (Mac) */
	public static final int IM_KEY_CONVERT         = 0x79; /* (Japanese keyboard)            */
	public static final int IM_KEY_NOCONVERT       = 0x7B; /* (Japanese keyboard)            */
	public static final int IM_KEY_YEN             = 0x7D; /* (Japanese keyboard)            */
	public static final int IM_KEY_NUMPADEQUALS    = 0x8D; /* = on numeric keypad (NEC PC98) */
	public static final int IM_KEY_CIRCUMFLEX      = 0x90; /* (Japanese keyboard)            */
	public static final int IM_KEY_AT              = 0x91; /*                     (NEC PC98) */
	public static final int IM_KEY_COLON           = 0x92; /*                     (NEC PC98) */
	public static final int IM_KEY_UNDERLINE       = 0x93; /*                     (NEC PC98) */
	public static final int IM_KEY_KANJI           = 0x94; /* (Japanese keyboard)            */
	public static final int IM_KEY_STOP            = 0x95; /*                     (NEC PC98) */
	public static final int IM_KEY_AX              = 0x96; /*                     (Japan AX) */
	public static final int IM_KEY_UNLABELED       = 0x97; /*                        (J3100) */
	public static final int IM_KEY_NUMPADENTER     = 0x9C; /* Enter on numeric keypad */
	public static final int IM_KEY_RCONTROL        = 0x9D;
	public static final int IM_KEY_SECTION         = 0xA7; /* Section symbol (Mac) */
	public static final int IM_KEY_NUMPADCOMMA     = 0xB3; /* , on numeric keypad (NEC PC98) */
	public static final int IM_KEY_DIVIDE          = 0xB5; /* / on numeric keypad */
	public static final int IM_KEY_SYSRQ           = 0xB7;
	public static final int IM_KEY_RMENU           = 0xB8; /* right Alt */
	public static final int IM_KEY_FUNCTION        = 0xC4; /* Function (Mac) */
	public static final int IM_KEY_PAUSE           = 0xC5; /* Pause */
	public static final int IM_KEY_HOME            = 0xC7; /* Home on arrow keypad */
	public static final int IM_KEY_UP              = 0xC8; /* UpArrow on arrow keypad */
	public static final int IM_KEY_PRIOR           = 0xC9; /* PgUp on arrow keypad */
	public static final int IM_KEY_LEFT            = 0xCB; /* LeftArrow on arrow keypad */
	public static final int IM_KEY_RIGHT           = 0xCD; /* RightArrow on arrow keypad */
	public static final int IM_KEY_END             = 0xCF; /* End on arrow keypad */
	public static final int IM_KEY_DOWN            = 0xD0; /* DownArrow on arrow keypad */
	public static final int IM_KEY_NEXT            = 0xD1; /* PgDn on arrow keypad */
	public static final int IM_KEY_INSERT          = 0xD2; /* Insert on arrow keypad */
	public static final int IM_KEY_DELETE          = 0xD3; /* Delete on arrow keypad */
	public static final int IM_KEY_CLEAR           = 0xDA; /* Clear key (Mac) */
	public static final int IM_KEY_LMETA           = 0xDB; /* Left Windows/Option key */
	public static final int IM_KEY_LWIN            = IM_KEY_LMETA; /* Left Windows key */
	public static final int IM_KEY_RMETA           = 0xDC; /* Right Windows/Option key */
	public static final int IM_KEY_RWIN            = IM_KEY_RMETA; /* Right Windows key */
	public static final int IM_KEY_APPS            = 0xDD; /* AppMenu key */
	public static final int IM_KEY_POWER           = 0xDE;
	public static final int IM_KEY_SLEEP           = 0xDF;

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

	public static final int IM_KEYBOARD_SIZE = 256;
	public static final int IM_MOUSE_SIZE = 8;

	private static final KeyEvent[] keyEvents = new KeyEvent[IM_KEYBOARD_SIZE];
	private static final MouseEvent[] mouseEvents = new MouseEvent[IM_MOUSE_SIZE];
	private static final ArrayList<Character> charEvents = new ArrayList<Character>();

	public static void init() {
		for (int i=0;i<IM_KEYBOARD_SIZE;i++) keyEvents[i] = new KeyEvent(i);
		for (int i=0;i<IM_MOUSE_SIZE;i++) mouseEvents[i] = new MouseEvent(i);
	}

	public static void destroy() {}

	private static void clear() {
		for (int i=0;i<IM_KEYBOARD_SIZE;i++) keyEvents[i].clear();
		for (int i=0;i<IM_MOUSE_SIZE;i++) mouseEvents[i].clear();
		charEvents.clear();
	}

	public static void poll() {
		clear();
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() >= 0 && Keyboard.getEventKey() < IM_KEYBOARD_SIZE) {
				if (!Keyboard.isRepeatEvent()) {
					if (Keyboard.getEventKeyState()) {
						keyEvents[Keyboard.getEventKey()].setPressed(true);
					} else {
						keyEvents[Keyboard.getEventKey()].setReleased(true);
					}
				} else if (Keyboard.getEventKeyState()) {
					keyEvents[Keyboard.getEventKey()].setRepeated(true);
				}
				if (Keyboard.getEventKeyState()) charEvents.add(Keyboard.getEventCharacter());
			}
		}
		while (Mouse.next()) {
			if (Mouse.getEventButton() >= 0 && Mouse.getEventButton() < IM_MOUSE_SIZE) {
				if (Mouse.getEventButtonState()) {
					mouseEvents[Mouse.getEventButton()].setPressed(true);
				} else {
					mouseEvents[Mouse.getEventButton()].setReleased(true);
				}
			}
		}
	}

	public static void enableRepeatEvents(boolean repeat) {Keyboard.enableRepeatEvents(repeat);}

	public static void setCursor(Cursor cursor) {
		try {
			Mouse.setNativeCursor(cursor);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static void setCursorPosition(int x, int y) {Mouse.setCursorPosition(x, y);}
	public static void setMouseGrabbed(boolean grabbed) {Mouse.setGrabbed(grabbed);}
	public static void setClipMouseCoordinatesToWindow(boolean clip) {Mouse.setClipMouseCoordinatesToWindow(clip);}

	public static Cursor getCursor() {return Mouse.getNativeCursor();}

	public static ArrayList<Character> getCharEvents() {return charEvents;}

	public static boolean isKeyPressed(int key) {return keyEvents[key].isPressed();}
	public static boolean isKeyReleased(int key) {return keyEvents[key].isReleased();}
	public static boolean isKeyRepeated(int key) {return keyEvents[key].isRepeated();}
	public static boolean isKeyDown(int key) {return Keyboard.isKeyDown(key);}
	public static boolean areRepeatEventsEnabled() {return Keyboard.areRepeatEventsEnabled();}

	public static boolean isMousePressed(int button) {return mouseEvents[button].isPressed();}
	public static boolean isMouseReleased(int button) {return mouseEvents[button].isReleased();}
	public static boolean isMouseDown(int button) {return Mouse.isButtonDown(button);}
	public static boolean isMouseGrabbed() {return Mouse.isGrabbed();}
	public static boolean isClipMouseCoordinatesToWindow() {return Mouse.isClipMouseCoordinatesToWindow();}
	public static boolean isMouseInsideWindow() {return Mouse.isInsideWindow();}

	public static double getMouseX() {return Mouse.getX();}
	public static double getMouseY() {return Mouse.getY();}
	public static double getMouseDX() {return Mouse.getDX();}
	public static double getMouseDY() {return Mouse.getDY();}
	public static double getMouseScroll() {return Mouse.getDWheel();}

}
