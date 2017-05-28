package zan.lib.droid.gfx.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import zan.lib.droid.util.Utility;
import static android.opengl.GLES20.*;

public class TextureManager {

	private static int TEXTURE_FILTER = GL_LINEAR;

	public static void setTextureFilter(int filter) {TEXTURE_FILTER = filter;}

	public static TextureInfo getTexture(int resourceID) {
		int textureWidth = 0;
		int textureHeight = 0;

		final int[] textureID = new int[1];
		glGenTextures(1, textureID, 0);
		if (textureID[0] != 0) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;

			final Bitmap bitmap = BitmapFactory.decodeResource(Utility.getContext().getResources(), resourceID, options);
			textureWidth = bitmap.getWidth();
			textureHeight = bitmap.getHeight();

			glBindTexture(GL_TEXTURE_2D, textureID[0]);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, TEXTURE_FILTER);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, TEXTURE_FILTER);

			GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
		}
		if (textureID[0] == 0) throw new RuntimeException("Error loading texture.");

		return new TextureInfo(textureID[0], textureWidth, textureHeight);
	}

    public static int getTextureID(int resourceID) {return getTexture(resourceID).id;}
    public static int getTextureWidth(int resourceID) {return getTexture(resourceID).width;}
	public static int getTextureHeight(int resourceID) {return getTexture(resourceID).height;}

}
