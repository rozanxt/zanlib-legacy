package zan.lib.droid.util.res;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import zan.lib.droid.util.Utility;

public class ResourceData {

	private String name;
	private ResourceData parent;
	private ArrayList<ResourceData> nodes = new ArrayList<ResourceData>();
	private HashMap<String, String> values = new HashMap<String, String>();

	public ResourceData(String name, ResourceData parent) {
		this.name = name;
		this.parent = parent;
	}
	public ResourceData(String name) {this(name, null);}

	public void addNode(ResourceData node) {nodes.add(node);}
	public void addValue(String key, String value) {values.put(key, value);}

	public String getName() {return name;}

	public ResourceData getParent() {return parent;}

	public ResourceData getNode(int node) {return nodes.get(node);}
	public ResourceData getNode(String node) {
		for (int i=0;i<getNumNodes();i++) if (getNode(i).getName().contentEquals(node)) return getNode(i);
		System.err.println("Error reading data '" + getLocation() + "': Node '" + node + "' not found!");
		return null;
	}

	public String getValue(String key) {
		if (!values.containsKey(key)) System.err.println("Error reading data '" + getLocation() + "': Value key '" + key + "' not found!");
		return values.get(key);
	}
	public int getIntegerValue(String key) {return Utility.parseInt(getValue(key));}
	public float getFloatValue(String key) {return Utility.parseFloat(getValue(key));}

	public Set<Entry<String, String>> getValuesEntrySet() {return values.entrySet();}

	public int getNumNodes() {return nodes.size();}
	public int getNumValues() {return values.size();}

	public boolean isEmpty() {return (nodes.isEmpty() && values.isEmpty());}
	public boolean hasNodes() {return !nodes.isEmpty();}
	public boolean hasValues() {return !values.isEmpty();}
	public boolean hasKey(String key) {return values.containsKey(key);}
	public boolean hasValue(String value) {return values.containsValue(value);}
	public boolean hasParent() {return (parent != null);}

	public String getLocation() {
		if (hasParent()) return parent.getLocation() + "/" + name;
		return name;
	}

}
