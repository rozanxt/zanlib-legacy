package zan.lib.res;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import zan.lib.util.Utility;

/** Resource reader class */
public class ResourceReader {

	private ResourceData resource;

	public ResourceReader(String fnm) {
		resource = new ResourceData(Utility.getPrefix(fnm));

		try {
			ArrayList<ResourceData> parent = new ArrayList<ResourceData>();
			parent.add(resource);

			BufferedReader br = new BufferedReader(new FileReader(fnm));
			String line;
			while((line = br.readLine()) != null) {
				if (line.length() == 0)	continue;
				String[] tkns = Utility.split(line);
				if (tkns[0].isEmpty() || tkns[0].startsWith("//")) continue;

				if (tkns.length == 1) {
					if (tkns[0].contentEquals("}")) {
						if (parent.size() > 1) {
							parent.remove(parent.size()-1);
							continue;
						}
					}
				} else if (tkns.length == 2) {
					if (tkns[1].contentEquals("{")) {
						ResourceData child = new ResourceData(tkns[0]);
						parent.get(parent.size()-1).addNode(child);
						parent.add(child);
						continue;
					}
				}
				if (line.contains("{") || line.contains("}")) {
					System.out.println("Invalid syntax in file " + fnm + ":\n " + line);
				}

				if (tkns.length == 1) {
					parent.get(parent.size()-1).addValue(tkns[0]);
					continue;
				} else if (tkns.length > 1) {
					ResourceData data = new ResourceData(tkns[0]);
					for (int i=1;i<tkns.length;i++) data.addValue(tkns[i]);
					parent.get(parent.size()-1).addNode(data);
					continue;
				}
			}

			br.close();
		} catch (IOException e) {
			System.err.println("Error reading file " + fnm + ":\n " + e);
		}
	}

	public ResourceData getData() {
		return resource;
	}

}
