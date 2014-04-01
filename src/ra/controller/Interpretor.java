package ra.controller;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author gwendal
 * interpretor of the initial command
 */
public class Interpretor {

	private File source;
	private double support;
	private double confidence;
	private String type;
	
	public void interpret(String[] args) throws IOException {
		setSource(args[0]);
		int i = 1;
		while(i < args.length) {
			switch(args[i]) {
			case "-support":
				setSupport(Double.parseDouble(args[++i]));
				break;
			case "-confidence":
				setConfidence(Double.parseDouble(args[++i]));
				break;
			case "-type":
				setType(args[++i]);
				break;
			default:
				throw new IOException("One or more arguments are not well written");
			}
		}
	}

	private void setSource(String src) {
		File abs = new File("");
		String path = abs.getAbsolutePath();
		source = new File(path + "/" + src);
	}

	private void setSupport(double support) {
		this.support = support;
	}

	private void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	private void setType(String type) {
		this.type = type;
	}

	public File getSource() {
		return source;
	}
	
	public double getSupport() {
		return support;
	}

	public double getConfidence() {
		return confidence;
	}

	public String getType() {
		return type;
	}
	
}
