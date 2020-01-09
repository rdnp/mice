package io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/** 
 * This is a parser for setup files.
 * @author Richard Pohl */
public class SetupFileParser 
{
	// parse states
	private static final int PARSE_COMMENT				= 0x0;
	private static final int PARSE_MEMORY			 	= 0x1; 
	private static final int PARSE_NB_CORES 			= 0x2;
	
	private InputStream inputStream;
	
	// the state of the parser
	private int parserState;
	
	// data
	private int memory 	= 1536;
	private int nbCores = 2;
	
	
	/**
	 * Constructs a {@link SetupFileParser}.
	 * @param outputStream - the {@link OutputStream} from which the data
	 * should be read. 
	 * */
	public SetupFileParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public int getMemory() {
		return memory;
	}
	
	public int getNbCores() {
		return nbCores;
	}
	
	/**
	 * Parses the setup file
	 * */
	public void parse() {
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext())
		{
			String line = scanner.nextLine();
			// lines starting with "-" are control lines that change the 
			// parser state
			if (line.startsWith("-"))
			{
				if (line.startsWith("--"))
					// ignore line, treat as single-line comment
					;
				else if (line.toLowerCase().contains("comment"))
					parserState = PARSE_COMMENT;
				else if (line.toLowerCase().contains("memory"))
					parserState = PARSE_MEMORY;
				else if (line.toLowerCase().contains("cores"))
					parserState = PARSE_NB_CORES;
			}
			// all other lines are information lines that are simply parsed
			else
			{
				parseLine(line);
			}
		}
		scanner.close();
		try {
			inputStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Parse a line depending on the parser state.
	 * @param line - the line to parse.
	 * */
	private void parseLine(String line)
	{
		switch (parserState)
		{
			case PARSE_MEMORY: 		 memory = Integer.parseInt(line); 	break;
			case PARSE_NB_CORES:  	 nbCores = Integer.parseInt(line);	break;
			default:
		}
	}
}