package zan.lib.res;

import static org.lwjgl.BufferUtils.createByteBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.BufferUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import zan.lib.util.Utility;

public class ResourceUtil {

	public static String readFileAsString(String filename) {
		StringBuilder content = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			String line;
			while((line = br.readLine()) != null) content.append(line).append('\n');
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}

	public static ResourceData readResource(String filename) {
		ResourceData resourceData = new ResourceData(Utility.getPrefix(filename));

		String resource = readFileAsString(filename);
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
					if (nodes.isEmpty()) throw new ResourceParsingException(2, filename);
					ResourceData node = nodes.get(nodes.size()-1);
					String token = parse.get(i);

					if (token.contains("=")) {
						int sep = token.indexOf("=");
						String key = token.substring(0, sep);
						String value = token.substring(sep+1);
						if (key.isEmpty()) throw new ResourceParsingException(3, filename);
						if (value.isEmpty()) throw new ResourceParsingException(4, filename);
						if (node.hasKey(key)) throw new ResourceParsingException(5, filename, key);
						node.addValue(key, value);
					} else if (token.contentEquals("{")) {
						if (i == 0) throw new ResourceParsingException(1, filename);
						ResourceData childNode = new ResourceData(parse.get(i-1), node);
						node.addNode(childNode);
						nodes.add(childNode);
						inline = false;
					} else if (token.contentEquals("}")) {
						if (inline) throw new ResourceParsingException(1, filename);
						nodes.remove(node);
					} else if (token.contentEquals(":")) {
						if (i == 0) throw new ResourceParsingException(1, filename);
						ResourceData childNode = new ResourceData(parse.get(i-1), node);
						node.addNode(childNode);
						nodes.add(childNode);
						inline = true;
					} else if (token.contentEquals(";")) {
						if (inline) nodes.remove(node);
						inline = false;
					}
				}

				if (nodes.size() != 1) throw new ResourceParsingException(2, filename);
			} catch (ResourceParsingException e) {
				e.printStackTrace();
			}
		}

		return resourceData;
	}

	public static ResourceData readXML(String filename) {
		ResourceData resourceData = new ResourceData(Utility.getPrefix(filename));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(filename));
			genResourceData(resourceData, doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

	private static void genResourceData(ResourceData parentData, Node parentNode) {
		if (parentNode.hasAttributes()) {
			NamedNodeMap attr = parentNode.getAttributes();
			for (int i=0;i<attr.getLength();i++) {
				Node item = attr.item(i);
				parentData.addValue(item.getNodeName(), item.getNodeValue());
			}
		}
		if (parentNode.hasChildNodes()) {
			NodeList nodes = parentNode.getChildNodes();
			StringBuilder textValue = new StringBuilder();
			for (int i=0;i<nodes.getLength();i++) {
				Node childNode = nodes.item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					ResourceData childData = new ResourceData(childNode.getNodeName(), parentData);
					parentData.addNode(childData);
					genResourceData(childData, childNode);
				} else if (childNode.getNodeType() == Node.TEXT_NODE) {
					if (!childNode.getNodeValue().trim().isEmpty()) {
						if (textValue.length() > 0) textValue.append('\n');
						textValue.append(childNode.getNodeValue().trim());
					}
				}
			}
			if (textValue.length() > 0) parentData.addValue("value", textValue.toString());
		}
	}

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;

		Path path = Paths.get(resource);
		if ( Files.isReadable(path) ) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
				while ( fc.read(buffer) != -1 ) ;
			}
		} else {
			try (
				InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)
			) {
				buffer = createByteBuffer(bufferSize);

				while ( true ) {
					int bytes = rbc.read(buffer);
					if ( bytes == -1 )
						break;
					if ( buffer.remaining() == 0 )
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
				}
			}
		}

		buffer.flip();
		return buffer;
	}

}
