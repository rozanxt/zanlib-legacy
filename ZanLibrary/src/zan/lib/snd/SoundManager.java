package zan.lib.snd;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {

	private static SoundSystem soundSystem;

	public static void init() {
		try {
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (SoundSystemException e) {
			System.err.println("Error linking with sound plugins!");
			e.printStackTrace();
		}
		soundSystem = new SoundSystem();
	}

	public static void destroy() {soundSystem.cleanup();}

	public static void loadSound(String name, String filename) {
		try {
			URL url = new File(filename).toURI().toURL();
			soundSystem.newSource(false, name, url, filename, false, 0f, 0f, 0f, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
		} catch (MalformedURLException e) {
			System.out.println("Error loading sound '" + name + "':");
			e.printStackTrace();
		}
	}
	public static void loadMusic(String name, String filename) {
		try {
			URL url = new File(filename).toURI().toURL();
			soundSystem.newStreamingSource(true, name, url, filename, true, 0f, 0f, 0f, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
		} catch (MalformedURLException e) {
			System.out.println("Error loading music '" + name + "':");
			e.printStackTrace();
		}
	}

	public static void preloadSource(String filename) {
		try {
			URL url = new File(filename).toURI().toURL();
			soundSystem.loadSound(url, filename);
		} catch (MalformedURLException e) {
			System.out.println("Error preloading source '" + filename + "':");
			e.printStackTrace();
		}
	}
	public static void unloadSource(String filename) {soundSystem.unloadSound(filename);}
	public static void removeSource(String filename) {soundSystem.removeSource(filename);}
	public static void removeTemporarySources() {soundSystem.removeTemporarySources();}

	public static void quickPlay(String filename, float x, float y, float z) {
		try {
			URL url = new File(filename).toURI().toURL();
			soundSystem.quickPlay(false, url, filename, false, x, y, z, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
		} catch (MalformedURLException e) {
			System.out.println("Error loading source '" + filename + "':");
			e.printStackTrace();
		}
	}
	public static void quickStream(String filename, float x, float y, float z) {
		try {
			URL url = new File(filename).toURI().toURL();
			soundSystem.quickStream(false, url, filename, false, x, y, z, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
		} catch (MalformedURLException e) {
			System.out.println("Error loading source '" + filename + "':");
			e.printStackTrace();
		}
	}
	public static void quickPlay(String filename) {quickPlay(filename, 0f, 0f, 0f);}
	public static void quickStream(String filename) {quickStream(filename, 0f, 0f, 0f);}

	public static void playSound(String name) {
		if (isPlaying(name)) soundSystem.stop(name);
		soundSystem.play(name);
	}
	public static void playMusic(String name) {soundSystem.play(name);}
	public static void pauseMusic(String name) {soundSystem.pause(name);}
	public static void stopMusic(String name) {soundSystem.stop(name);}
	public static void rewindMusic(String name) {soundSystem.rewind(name);}
	public static void toggleMusic(String name) {
		if (isPlaying(name)) pauseMusic(name);
		else playMusic(name);
	}

	public static void setPriority(String name, boolean priority) {soundSystem.setPriority(name, priority);}
	public static void setTemporary(String name, boolean temporary) {soundSystem.setTemporary(name, temporary);}
	public static void setLooping(String name, boolean loop) {soundSystem.setLooping(name, loop);}
	public static void setMasterVolume(float volume) {soundSystem.setMasterVolume(volume);}
	public static void setVolume(String name, float volume) {soundSystem.setVolume(name, volume);}
	public static void setPitch(String name, float pitch) {soundSystem.setPitch(name, pitch);}
	public static void setPosition(String name, float x, float y, float z) {soundSystem.setPosition(name, x, y, z);}
	public static void setVelocity(String name, float x, float y, float z) {soundSystem.setVelocity(name, x, y, z);}

	public static void queueMusic(String name, String next) {
		try {
			URL url = new File(next).toURI().toURL();
			soundSystem.queueSound(name, url, next);
		} catch (MalformedURLException e) {
			System.out.println("Error queuing music '" + next + "':");
			e.printStackTrace();
		}
	}
	public static void queueMusic(String name, String next, long fadeOut) {
		try {
			URL url = new File(next).toURI().toURL();
			soundSystem.fadeOut(name, url, next, fadeOut);
		} catch (MalformedURLException e) {
			System.out.println("Error queuing music '" + next + "':");
			e.printStackTrace();
		}
	}
	public static void queueMusic(String name, String next, long fadeOut, long fadeIn) {
		try {
			URL url = new File(next).toURI().toURL();
			soundSystem.fadeOutIn(name, url, next, fadeOut, fadeIn);
		} catch (MalformedURLException e) {
			System.out.println("Error queuing music '" + next + "':");
			e.printStackTrace();
		}
	}

	public static void checkFadeVolumes() {soundSystem.checkFadeVolumes();}

	public static void setListenerPosition(float x, float y, float z) {soundSystem.setListenerPosition(x, y, z);}
	public static void setListenerVelocity(float x, float y, float z) {soundSystem.setListenerVelocity(x, y, z);}
	public static void setListenerOrientation(float lookX, float lookY, float lookZ, float upX, float upY, float upZ) {soundSystem.setListenerOrientation(lookX, lookY, lookZ, upX, upY, upZ);}
	public static void setListenerAngle(float angle) {soundSystem.setListenerAngle((float)(angle*(Math.PI/180f)));}

	public static void moveListener(float x, float y, float z) {soundSystem.moveListener(x, y, z);}
	public static void turnListener(float angle) {soundSystem.turnListener((float)(angle*(Math.PI/180f)));}

	public static float getMasterVolume() {return soundSystem.getMasterVolume();}
	public static float getVolume(String name) {return soundSystem.getVolume(name);}
	public static float getPitch(String name) {return soundSystem.getPitch(name);}

	public static boolean isPlaying() {return soundSystem.playing();}
	public static boolean isPlaying(String name) {return soundSystem.playing(name);}

}
