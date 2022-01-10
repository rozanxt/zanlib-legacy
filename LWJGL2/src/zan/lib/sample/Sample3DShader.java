package zan.lib.sample;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.util.res.ResourceUtil;

public class Sample3DShader extends DefaultShader {

	@Override
	public void loadProgram() {
		String vertexShaderSource = ResourceUtil.readFileAsString("res/shd/sample3d.glvs");
		String fragmentShaderSource = ResourceUtil.readFileAsString("res/shd/sample3d.glfs");
		loadProgram(vertexShaderSource, fragmentShaderSource);
	}

	@Override
	protected void fetchLocations() {
		vertexPosID = fetchAttribLocation("vertexPos");
		vertexNormalID = fetchAttribLocation("vertexNormal");
		vertexColorID = fetchAttribLocation("vertexColor");
		texCoordID = fetchAttribLocation("texCoord");
		texUnitID = fetchUniformLocation("texUnit");
		enableTextureID = fetchUniformLocation("enableTexture");
		enableColorID = fetchUniformLocation("enableColor");
		tintColorID = fetchUniformLocation("tintColor");
		modelViewMatrixID = fetchUniformLocation("modelViewMatrix");
		projectionMatrixID = fetchUniformLocation("projectionMatrix");
	}

}
