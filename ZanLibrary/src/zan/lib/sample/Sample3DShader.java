package zan.lib.sample;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.util.Utility;

public class Sample3DShader extends DefaultShader {

	@Override
	public void loadProgram() {
		String vertexShaderSource = Utility.readFileAsString("res/shader/sample3d.glvs");
		String fragmentShaderSource = Utility.readFileAsString("res/shader/sample3d.glfs");
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
