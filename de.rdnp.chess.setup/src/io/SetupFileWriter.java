package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/** 
 * This is a writer for setup files.
 * @author Richard Pohl */
public class SetupFileWriter {
	private OutputStream outputStream;
	
	/**
	 * Constructs a {@link SetupFileWriter}.
	 * @param outputStream - the {@link OutputStream} into which the 
	 * data should be written. 
	 * */
	public SetupFileWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * Writes position into the {@link OutputStream} associated with the 
	 * {@link SetupFileWriter}.
	 * @param gameState - the {@link GameState} to write.
	 * */
	public void write(int memory, int nbCores)
	{
		PrintWriter printWriter = new PrintWriter(outputStream);
		// write into the destination stream
		printWriter.println("- comment");
		printWriter.println("This file was saved by MICE - the " +
				"Minimalistic Intelligent Chess Engine.\n" +
				"MICE is Copyright (C) 2011, Richard D. N. Pohl. All rights" +
				" reserved.\n");
		printWriter.println("- memory");
		printWriter.println(memory);
		printWriter.println("- cores");
		printWriter.println(nbCores);
		printWriter.flush();
		printWriter.close();
		try {
			outputStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
