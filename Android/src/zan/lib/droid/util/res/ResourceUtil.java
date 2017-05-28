package zan.lib.droid.util.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import zan.lib.droid.util.Utility;

public class ResourceUtil {

	public static String readFileAsString(int resourceID) {
		final InputStream inputStream = Utility.getContext().getResources().openRawResource(resourceID);
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String nextLine;
		final StringBuilder body = new StringBuilder();

		try {
			while ((nextLine = bufferedReader.readLine()) != null) {
				body.append(nextLine);
				body.append('\n');
			}
		} catch (IOException e) {
			return null;
		}

		return body.toString();
	}

	public static ResourceData readResource(String name, int resourceID) {
		ResourceData resourceData = new ResourceData(name);

		String resource = readFileAsString(resourceID);
		if (!resource.isEmpty()) {
			try {
				String[] lines = resource.split("\n");
				ArrayList<String> parse = new ArrayList<String>();
				for (int i=0;i<lines.length;i++) {
					if (!lines[i].isEmpty() && !lines[i].startsWith("#")) {
						String[] tkns = lines[i].split(" |\t|(?<=\\{)|(?=\\{)|(?<=\\})|(?=\\})|(?<=:)|(?=:)|(?<=;)|(?=;)");
						for (int j=0;j<tkns.length;j++) if (!tkns[j].isEmpty()) parse.add(tkns[j]);
					}
				}

				ArrayList<ResourceData> nodes = new ArrayList<ResourceData>();
				nodes.add(resourceData);
				boolean inline = false;
				for (int i=0;i<parse.size();i++) {
					if (nodes.isEmpty()) throw new ResourceParsingException(2, name);
					ResourceData node = nodes.get(nodes.size()-1);
					String token = parse.get(i);

					if (token.contains("=")) {
						int sep = token.indexOf("=");
						String key = token.substring(0, sep);
						String value = token.substring(sep+1);
						if (key.isEmpty()) throw new ResourceParsingException(3, name);
						if (value.isEmpty()) throw new ResourceParsingException(4, name);
						if (node.hasKey(key)) throw new ResourceParsingException(5, name, key);
						node.addValue(key, value);
					} else if (token.contentEquals("{")) {
						if (i == 0) throw new ResourceParsingException(1, name);
						ResourceData childNode = new ResourceData(parse.get(i-1), node);
						node.addNode(childNode);
						nodes.add(childNode);
						inline = false;
					} else if (token.contentEquals("}")) {
						if (inline) throw new ResourceParsingException(1, name);
						nodes.remove(node);
					} else if (token.contentEquals(":")) {
						if (i == 0) throw new ResourceParsingException(1, name);
						ResourceData childNode = new ResourceData(parse.get(i-1), node);
						node.addNode(childNode);
						nodes.add(childNode);
						inline = true;
					} else if (token.contentEquals(";")) {
						if (inline) nodes.remove(node);
						inline = false;
					}
				}

				if (nodes.size() != 1) throw new ResourceParsingException(2, name);
			} catch (ResourceParsingException e) {
				e.printStackTrace();
			}
		}

		return resourceData;
	}

	public static ArrayList<ResourceData> searchDataByName(ResourceData data, String name) {
		ArrayList<ResourceData> found = new ArrayList<ResourceData>();
		for (int i=0;i<data.getNumNodes();i++) {
			ResourceData node = data.getNode(i);
			if (node.getName().contentEquals(name)) found.add(node);
			found.addAll(searchDataByName(node, name));
		}
		return found;
	}

}
