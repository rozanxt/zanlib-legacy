package zan.lib.droid.util.res;

public class ResourceParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	private int error;
	private String resource;
	private String message;

	public ResourceParsingException(int error, String resource, String message) {
		this.error = error;
		this.resource = resource;
		this.message = message;
	}
	public ResourceParsingException(int error, String resource) {this(error, resource, null);}

	@Override
	public void printStackTrace() {
		String msg = null;
		switch (error) {
			case 1:
				msg = "Syntax Error!";
				break;
			case 2:
				msg = "Missing or excessive use of brackets or colons!";
				break;
			case 3:
				msg = "Empty data key!";
				break;
			case 4:
				msg = "Empty data value!";
				break;
			case 5:
				msg = "Value key '" + message + "' already used!";
				break;
			default:
				msg = message;
				break;
		}
		if (msg == null) msg = "Unknown error";
		System.err.println("Error parsing file: '" + resource + "': " + msg);
		super.printStackTrace();
	}

}
