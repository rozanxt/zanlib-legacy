package zan.lib.gfx.obj;

import zan.lib.math.matrix.Mat44D;
import zan.lib.math.matrix.MatUtil;

public class VertexState {
	
	private Mat44D currentState;
	private Mat44D lastState;
	
	public VertexState() {
		currentState = MatUtil.identityMat44D();
		lastState = MatUtil.identityMat44D();
	}
	
	public void amendState() {lastState.set(currentState);}
	
	public void setState(Mat44D matrix) {currentState.set(matrix);}
	public void loadIdentity() {currentState.loadIdentity();}
	public void multMatrix(Mat44D matrix) {
		MatUtil.mult(new Mat44D(currentState), matrix, currentState);
	}
	
	public Mat44D getState(double ip) {
		Mat44D state = new Mat44D();
		for (int i=0;i<state.size();i++) {
			double current = currentState.get(i);
			double last = lastState.get(i);
			state.set(i, last+(current-last)*ip);
		}
		return state;
	}
	
}
