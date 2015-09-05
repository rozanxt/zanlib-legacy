package zan.lib.gfx.obj;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.texture.TextureInfo;

public class SpriteObject extends VertexObject {

	public static final boolean ALIGN_HORIZONTAL = false;
	public static final boolean ALIGN_VERTICAL = true;

	protected TextureInfo spriteTexture;

	protected int numFrames;

	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1, float originX, float originY, int horizontal, int vertical, boolean align) {
		spriteTexture = texture;

		float clipWidth = Math.abs(x1-x0);
		float clipHeight = Math.abs(y1-y0);
		float tileWidth = clipWidth/horizontal;
		float tileHeight = clipHeight/vertical;
		float tileRatio = tileWidth/tileHeight;

		float tileX = x0/spriteTexture.width;
		float tileY = y0/spriteTexture.height;
		float tileW = tileWidth/spriteTexture.width;
		float tileH = tileHeight/spriteTexture.height;

		numFrames = horizontal*vertical;

		int[] indices = new int[4*numFrames];
		for (int i=0;i<4*numFrames;i++) indices[i] = i;

		float[] vertices = new float[16*numFrames];
		if (align == ALIGN_VERTICAL) {
			for (int j=0;j<horizontal;j++) {
				for (int i=0;i<vertical;i++) {
					int tileOffset = 16*vertical*j+16*i;
					// Vertex Coords
					vertices[tileOffset+4*0+0] = -originX*tileRatio;
					vertices[tileOffset+4*0+1] = -originY;
					vertices[tileOffset+4*1+0] = (1-originX)*tileRatio;
					vertices[tileOffset+4*1+1] = -originY;
					vertices[tileOffset+4*2+0] = (1-originX)*tileRatio;
					vertices[tileOffset+4*2+1] = (1-originY);
					vertices[tileOffset+4*3+0] = -originX*tileRatio;
					vertices[tileOffset+4*3+1] = (1-originY);
					// Texture Coords
					vertices[tileOffset+4*0+2] = tileX+j*tileW;
					vertices[tileOffset+4*0+3] = tileY+(i+1)*tileH;
					vertices[tileOffset+4*1+2] = tileX+(j+1)*tileW;
					vertices[tileOffset+4*1+3] = tileY+(i+1)*tileH;
					vertices[tileOffset+4*2+2] = tileX+(j+1)*tileW;
					vertices[tileOffset+4*2+3] = tileY+i*tileH;
					vertices[tileOffset+4*3+2] = tileX+j*tileW;
					vertices[tileOffset+4*3+3] = tileY+i*tileH;
				}
			}
		} else if (align == ALIGN_HORIZONTAL) {
			for (int i=0;i<vertical;i++) {
				for (int j=0;j<horizontal;j++) {
					int tileOffset = 16*horizontal*i+16*j;
					// Vertex Coords
					vertices[tileOffset+4*0+0] = -originX*tileRatio;
					vertices[tileOffset+4*0+1] = -originY;
					vertices[tileOffset+4*1+0] = (1-originX)*tileRatio;
					vertices[tileOffset+4*1+1] = -originY;
					vertices[tileOffset+4*2+0] = (1-originX)*tileRatio;
					vertices[tileOffset+4*2+1] = (1-originY);
					vertices[tileOffset+4*3+0] = -originX*tileRatio;
					vertices[tileOffset+4*3+1] = (1-originY);
					// Texture Coords
					vertices[tileOffset+4*0+2] = tileX+j*tileW;
					vertices[tileOffset+4*0+3] = tileY+(i+1)*tileH;
					vertices[tileOffset+4*1+2] = tileX+(j+1)*tileW;
					vertices[tileOffset+4*1+3] = tileY+(i+1)*tileH;
					vertices[tileOffset+4*2+2] = tileX+(j+1)*tileW;
					vertices[tileOffset+4*2+3] = tileY+i*tileH;
					vertices[tileOffset+4*3+2] = tileX+j*tileW;
					vertices[tileOffset+4*3+3] = tileY+i*tileH;
				}
			}
		}

		createVBO(vertices);
		createIBO(indices);
		setAttributes(2, 0, 0, 2);
		setNumVertices(4);
		setDrawMode(GL_TRIANGLE_FAN);
	}
	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1, float originX, float originY) {
		this(texture, x0, y0, x1, y1, originX, originY, 1, 1, false);
	}
	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1, int horizontal, int vertical, boolean align) {
		this(texture, x0, y0, x1, y1, 0.5f, 0.5f, horizontal, vertical, align);
	}
	public SpriteObject(TextureInfo texture, float originX, float originY, int horizontal, int vertical, boolean align) {
		this(texture, 0f, 0f, texture.width, texture.height, originX, originY, horizontal, vertical, align);
	}
	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1) {
		this(texture, x0, y0, x1, y1, 0.5f, 0.5f, 1, 1, false);
	}
	public SpriteObject(TextureInfo texture, float originX, float originY) {
		this(texture, 0f, 0f, texture.width, texture.height, originX, originY, 1, 1, false);
	}
	public SpriteObject(TextureInfo texture, int horizontal, int vertical, boolean align) {
		this(texture, 0f, 0f, texture.width, texture.height, 0.5f, 0.5f, horizontal, vertical, align);
	}
	public SpriteObject(TextureInfo texture) {
		this(texture, 0f, 0f, texture.width, texture.height, 0.5f, 0.5f, 1, 1, false);
	}

	public void setFrame(int frame) {setIndexOffset(numVertices*numData*frame);}
	public int getNumFrames() {return numFrames;}

	@Override
	public void render(DefaultShader sp) {
		sp.bindTexture(spriteTexture.id);
		sp.bindMatrix();
		sp.bindBuffer(vertexBuffer, indexBuffer);

		sp.setPositionPointer(numCoords, numData, coordOffset);
		sp.enablePositionPointer();
		sp.setTexCoordPointer(numTexCoords, numData, texCoordOffset);
		sp.enableTexCoordPointer();
		sp.disableNormalPointer();
		sp.disableColorPointer();

		sp.drawElements(drawMode, numVertices, indexOffset);
	}

	public void renderFrame(DefaultShader sp, int frame) {
		setFrame(frame);
		render(sp);
	}

}
