package zan.lib.droid.gfx.obj;

import java.util.ArrayList;

import zan.lib.droid.util.math.Vec3F;
import zan.lib.droid.util.math.VecUtil;
import zan.lib.droid.util.res.ResourceUtil;
import zan.lib.droid.util.Utility;

public class ModelObject extends VertexObject {

	public ModelObject(int resourceID) {
		String source = ResourceUtil.readFileAsString(resourceID);
		String[] data = source.split("\n");

		ArrayList<Vec3F> pos = new ArrayList<Vec3F>();
		ArrayList<Vec3F> ind = new ArrayList<Vec3F>();
		ArrayList<Vec3F> ver = new ArrayList<Vec3F>();
		ArrayList<Vec3F> nor = new ArrayList<Vec3F>();

		for (int i=0;i<data.length;i++) {
			if (data[i].startsWith("v")) {
				String[] token = data[i].split(" ");
				pos.add(new Vec3F(Utility.parseFloat(token[1]), Utility.parseFloat(token[2]), Utility.parseFloat(token[3])));
			} else if (data[i].startsWith("f")) {
				String[] token = data[i].split(" ");
				ind.add(new Vec3F(Utility.parseInt(token[1]), Utility.parseInt(token[2]), Utility.parseInt(token[3])));
			}
		}

		for (int i=0;i<ind.size();i++) {
			Vec3F p0 = pos.get((int)ind.get(i).get(0)-1);
			Vec3F p1 = pos.get((int)ind.get(i).get(1)-1);
			Vec3F p2 = pos.get((int)ind.get(i).get(2)-1);
			Vec3F v01 = new Vec3F(VecUtil.sub(p1, p0));
			Vec3F v12 = new Vec3F(VecUtil.sub(p2, p1));
			Vec3F n = VecUtil.cross(v01, v12);
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
					vertices[18*i+6*j+k] = ver.get(3*i+j).get(k);
					vertices[18*i+6*j+3+k] = nor.get(3*i+j).get(k);
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
