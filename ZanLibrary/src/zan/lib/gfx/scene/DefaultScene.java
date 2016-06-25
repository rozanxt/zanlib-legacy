package zan.lib.gfx.scene;

import static org.lwjgl.opengl.GL11.*;

import zan.lib.gfx.Gfx;

public abstract class DefaultScene extends MatrixScene {

	protected boolean enableBlend = false;
	protected boolean enableCullFace = false;
	protected boolean enableDepthTest = false;

	protected int blendSrc = GL_SRC_ALPHA;
	protected int blendDst = GL_ONE_MINUS_SRC_ALPHA;

	@Override
	public void begin() {
		Gfx.enableBlend(enableBlend);
		Gfx.setBlendFunc(blendSrc, blendDst);
		Gfx.enableCullFace(enableCullFace);
		Gfx.enableDepthTest(enableDepthTest);
	}

	public void enableBlend(boolean blend) {enableBlend = blend;}
	public void setBlendFunc(int src, int dst) {blendSrc = src; blendDst = dst;}
	public void enableCullFace(boolean cullFace) {enableCullFace = cullFace;}
	public void enableDepthTest(boolean depthTest) {enableDepthTest = depthTest;}

	public void bindBuffer(int vertexBuffer, int indexBuffer) {Gfx.bindBuffer(vertexBuffer, indexBuffer);}
	public void drawElements(int drawMode, int numVertices, int indexOffset) {Gfx.drawElements(drawMode, numVertices, indexOffset);}

	public abstract void setColor(double r, double g, double b, double a);
	public abstract void bindTexture(int texture);

	public abstract void bindModelView();
	public abstract void bindProjection();
	public void bindMatrix() {bindModelView(); bindProjection();}

	public abstract void enablePositionPointer();
	public abstract void disablePositionPointer();
	public abstract void setPositionPointer(int size, int stride, int offset);

	public abstract void enableNormalPointer();
	public abstract void disableNormalPointer();
	public abstract void setNormalPointer(int size, int stride, int offset);

	public abstract void enableColorPointer();
	public abstract void disableColorPointer();
	public abstract void setColorPointer(int size, int stride, int offset);

	public abstract void enableTexCoordPointer();
	public abstract void disableTexCoordPointer();
	public abstract void setTexCoordPointer(int size, int stride, int offset);

}
