package zan.lib.res;

import java.util.ArrayList;
import java.util.HashMap;

import zan.lib.util.Utility;

public class ResourceData {

	private String name;
	private ArrayList<ResourceData> nodes = new ArrayList<ResourceData>();
	private HashMap<String, String> values = new HashMap<String, String>();

	public ResourceData(String name) {this.name = name;}

	public void addValue(String key, String value) {values.put(key, value);}
	public void addNode(ResourceData node) {nodes.add(node);}

	public String getName() {return name;}

	public ResourceData getNode(int node) {return nodes.get(node);}
	public ResourceData getNode(String node) {
		for (int i=0;i<getNumNodes();i++) if (getNode(i).getName().contentEquals(node)) return getNode(i);
		System.err.println("Error in ResourceData " + name + ": Node '" + node + "' not found!");
		return null;
	}

	public String getValue(String key) {
		if (!values.containsKey(key)) System.err.println("Error in ResourceData " + name + ": Key '" + key + "' not found!");
		return values.get(key);
	}
	public int getIntegerValue(String key) {return Utility.parseInt(getValue(key));}
	public float getFloatValue(String key) {return Utility.parseFloat(getValue(key));}

	public boolean hasKey(String key) {return values.containsKey(key);}

	public int getNumNodes() {return nodes.size();}
	public int getNumValues() {return values.size();}

	public static ResourceData readResource(String filename) {
		String resourceName = filename;
		if (filename.contains(".")) {
			int dot = filename.lastIndexOf(".");
			resourceName = filename.substring(0, dot);
		}
		ResourceData resourceData = new ResourceData(resourceName);

		String resource = Utility.readFileAsString(filename);
		if (!resource.isEmpty()) {
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
				if (nodes.isEmpty()) {
					System.err.println("Error parsing file " + filename + ": Excessive use of closing brackets!");
					return null;
				}
				ResourceData node = nodes.get(nodes.size()-1);
				String token = parse.get(i);

				if (token.contains("=")) {
					int sep = token.indexOf("=");
					String key = token.substring(0, sep);
					String value = token.substring(sep+1);

					if (key.isEmpty()) System.err.println("Error parsing file " + filename + ": Empty node key!");
					else if (value.isEmpty()) System.err.println("Error parsing file " + filename + ": Empty node value!");
					else if (node.hasKey(key)) System.err.println("Error parsing file " + filename + ": Value key '" + key + "' already used!");
					else node.addValue(key, value);
				} else if (token.contentEquals("{")) {
					if (i > 0) {
						ResourceData childNode = new ResourceData(parse.get(i-1));
						node.addNode(childNode);
						nodes.add(childNode);
						inline = false;
					} else System.err.println("Error parsing file " + filename + ": Misplaced bracket!");
				} else if (token.contentEquals("}")) {
					if (!inline) nodes.remove(node);
					else System.err.println("Error parsing file " + filename + ": Misplaced bracket!");
				} else if (token.contentEquals(":")) {
					if (i > 0) {
						ResourceData childNode = new ResourceData(parse.get(i-1));
						node.addNode(childNode);
						nodes.add(childNode);
						inline = true;
					} else System.err.println("Error parsing file " + filename + ": Misplaced colon!");
				} else if (token.contentEquals(";")) {
					if (inline) nodes.remove(node);
					inline = false;
				}
			}

			if (nodes.size() != 1) {
				System.err.println("Error parsing file " + filename + ": Missing or excessive use of brackets or colons!");
				return null;
			}
		}
		return resourceData;
	}

}
