package zan.lib.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Gfx {

	public static final int BYTES_PER_FLOAT = 4;
	public static final int BYTES_PER_INT = 4;

	private static int CLEAR_BUFFER_MASK = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;

	public static void clear() {
		glClear(CLEAR_BUFFER_MASK);
	}

	public static void setClearBuffer(int mask) {
		CLEAR_BUFFER_MASK = mask;
	}

	public static void setClearColor(double r, double g, double b, double a) {
		glClearColor((float)r, (float)g, (float)b, (float)a);
	}

	public static void setViewPort(double x, double y, double w, double h) {
		glViewport((int)x, (int)y, (int)w, (int)h);
	}

	public static void enableBlend(boolean blend) {
		if (blend) glEnable(GL_BLEND);
		else glDisable(GL_BLEND);
	}

	public static void setBlendFunc(int blendSrc, int blendDst) {
		glBlendFunc(blendSrc, blendDst);
	}

	public static void enableCullFace(boolean cullFace) {
		if (cullFace) glEnable(GL_CULL_FACE);
		else glDisable(GL_CULL_FACE);
	}

	public static void enableDepthTest(boolean depthTest) {
		if (depthTest) glEnable(GL_DEPTH_TEST);
		else glDisable(GL_DEPTH_TEST);
	}

	public static void bindBuffer(int vertexBuffer, int indexBuffer) {
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	}

	public static void drawElements(int drawMode, int numVertices, int indexOffset) {
		glDrawElements(drawMode, numVertices, GL_UNSIGNED_INT, indexOffset);
	}

}
