package zan.lib.gfx.obj;

import java.util.ArrayList;

import zan.lib.util.math.Vec3D;
import zan.lib.util.math.VecUtil;
import zan.lib.util.Utility;

public class ModelObject extends VertexObject {

	public ModelObject(String path) {
		String source = Utility.readFileAsString(path);
		String[] data = source.split("\n");

		ArrayList<Vec3D> pos = new ArrayList<Vec3D>();
		ArrayList<Vec3D> ind = new ArrayList<Vec3D>();
		ArrayList<Vec3D> ver = new ArrayList<Vec3D>();
		ArrayList<Vec3D> nor = new ArrayList<Vec3D>();

		for (int i=0;i<data.length;i++) {
			if (data[i].startsWith("v")) {
				String[] token = data[i].split(" ");
				pos.add(new Vec3D(Utility.parseFloat(token[1]), Utility.parseFloat(token[2]), Utility.parseFloat(token[3])));
			} else if (data[i].startsWith("f")) {
				String[] token = data[i].split(" ");
				ind.add(new Vec3D(Utility.parseInt(token[1]), Utility.parseInt(token[2]), Utility.parseInt(token[3])));
			}
		}

		for (int i=0;i<ind.size();i++) {
			Vec3D p0 = pos.get((int)ind.get(i).get(0)-1);
			Vec3D p1 = pos.get((int)ind.get(i).get(1)-1);
			Vec3D p2 = pos.get((int)ind.get(i).get(2)-1);
			Vec3D v01 = new Vec3D(VecUtil.sub(p1, p0));
			Vec3D v12 = new Vec3D(VecUtil.sub(p2, p1));
			Vec3D n = VecUtil.cross(v01, v12);
			n.normalize();
			ver.add(p0);
			ver.add(p1);
			ver.add(p2);
			nor.add(n);
			nor.add(n);
			nor.add(n);
		}

		float[] vertices = new float[18*ind.size()];
		for (int i=0;i<ind.size();i++) {
			for (int j=0;j<3;j++) {
				for (int k=0;k<3;k++) {
					vertices[18*i+6*j+k] = (float)ver.get(3*i+j).get(k);
					vertices[18*i+6*j+3+k] = (float)nor.get(3*i+j).get(k);
				}
			}
		}

		int[] indices = new int[3*ind.size()];
		for (int i=0;i<3*ind.size();i++) indices[i] = i;

		createVBO(vertices);
		createIBO(indices);
		setAttributes(3, 3, 0, 0);
	}

}
