package zan.lib.gfx.obj;

import java.util.ArrayList;

import zan.lib.util.math.Vec3D;
import zan.lib.util.Utility;

public class ModelObject extends VertexObject {
	
	public ModelObject(String path) {
		super();
		
		String raw = Utility.readFileAsString(path);
		String[] data = raw.split("\n");
		
		ArrayList<Vec3D> ver = new ArrayList<Vec3D>();
		ArrayList<Vec3D> ind = new ArrayList<Vec3D>();
		
		for (int i=0;i<data.length;i++) {
			if (data[i].startsWith("v")) {
				String[] token = data[i].split(" ");
				ver.add(new Vec3D(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3])));
			} else if (data[i].startsWith("f")) {
				String[] token = data[i].split(" ");
				ind.add(new Vec3D(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3])));
			}
		}
		
		float[] vertices = new float[ver.size()*3];
		for (int i=0;i<ver.size();i++) {
			for (int j=0;j<3;j++) {
				vertices[i*3+j] = (float)ver.get(i).get(j);
			}
		}
		int[] indices = new int[ind.size()*3];
		for (int i=0;i<ind.size();i++) {
			for (int j=0;j<3;j++) {
				indices[i*3+j] = (int)ind.get(i).get(j)-1;
			}
		}
		
		createVBO(vertices);
		createIBO(indices);
	}
	
}
