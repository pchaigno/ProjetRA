package ra.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Interpretor for the command line arguments.
 */
public class Interpretor {
	public static final String DEFAULT_TYPE = "frequent";
	public static final double DEFAULT_CONFIDENCE = -1;
	public static final double DEFAULT_SUPPORT = 0.5;
	public static final boolean DEFAULT_MEMORY = true;
	private File source;
	private double support = DEFAULT_SUPPORT;
	private double confidence = DEFAULT_CONFIDENCE;
	private String type = DEFAULT_TYPE;
	private boolean memory = DEFAULT_MEMORY;
	private PrintStream output;
	
	/**
	 * Constructor
	 * @param args The arguments from the command line.
	 * @throws IllegalArgumentException If the arguments are incorrect.
	 * @throws FileNotFoundException 
	 */
	public Interpretor(String[] args) throws IllegalArgumentException, FileNotFoundException {
		if(args.length < 1) {
			throw new IllegalArgumentException("Missing argument");
		}
		this.output = System.out;
		this.source = new File(new File("").getAbsolutePath() + "/" + args[0]);
		int i = 1;
		while(i < args.length) {
			switch(args[i]) {
				case "-support":
					this.support = Double.parseDouble(args[++i]);
					break;
				case "-confidence":
					this.confidence = Double.parseDouble(args[++i]);
					break;
				case "-type":
					this.type = args[++i];
					break;
				case "-nomemory":
					this.memory = false;
					break;
				case "-output":
					this.output = new PrintStream(new File(args[++i]));
					break;
				default:
					throw new IllegalArgumentException("One or more arguments are not well written");
			}
			i++;
		}
	}

	/**
	 * @return The source file.
	 */
	public File getSource() {
		return source;
	}
	
	/**
	 * @return The minimum support.
	 */
	public double getSupport() {
		return support;
	}

	/**
	 * @return The confidence.
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * @return The type of APriori algorithm to use.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return True if the transactions should be saved in memory.
	 */
	public boolean useMemory() {
		return this.memory;
	}

	/**
	 * @return the output file, or null if the standard output should be used
	 */
	public PrintStream getOutput() {
		return output;
	}
}
