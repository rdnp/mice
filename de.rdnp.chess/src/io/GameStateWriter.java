package io;

import game.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.Position;

/** 
 * This is a writer for {@link GameState} objects.
 * @author Richard Pohl */
public class GameStateWriter {
	private OutputStream outputStream;
	
	/**
	 * Constructs a {@link GameStateWriter}.
	 * @param outputStream - the {@link OutputStream} into which the 
	 * {@link GameState} should be written. 
	 * */
	public GameStateWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * Writes position into the {@link OutputStream} associated with the 
	 * {@link GameStateWriter}.
	 * @param gameState - the {@link GameState} to write.
	 * */
	public void write(GameState gameState)
	{
		PrintWriter printWriter = new PrintWriter(outputStream);
		// computation of data to write
		String playerString = null;
		if (gameState.getActivePlayer() == Player.WHITE) 
			playerString = "white";
		else if (gameState.getActivePlayer() == Player.BLACK) 
			playerString = "black";
		int enPassantLine = gameState.getCurrentPosition().getEnPassantLine();
		// write into the destination stream
		printWriter.println("- comment");
		printWriter.println("This file was saved by MICE - the " +
				"Minimalistic Intelligent Chess Engine.\n" +
				"MICE is Copyright (C) 2011, Richard D. N. Pohl. All rights" +
				" reserved.\n" +
				"Do you notice that this file can be easily read and edited " +
				"by humans?\n" +
				"Feel free to create your own chess positions in a simple " +
				"text editor! You can include arbitrary many comments - like " +
				"this one - using the \"comment\" feature. You can also add " +
				"single line comments using the double-dash (\"--\") at the " +
				"beginning of a line.");
		printWriter.println("- player");
		printWriter.println(playerString);
		printWriter.println("- castling");
		printWriter.println(gameState.getCurrentPosition().getCastlingHistory());
		printWriter.println("- comment");
		printWriter.println("Special encoding for castling history," +
				" computed as follows:\n" +
				"15 = no castling occurred in this position. For each " +
				"castling that has already occurred, subtract the castling " +
				"constant for the specific castling move from the value. The " +
				"castling constants are: " +
				"\n\twhite castle king side:  " +
				Position.WHITE_CASTLE_KING_SIDE +
				"\n\twhite castle queen side: " +
				Position.WHITE_CASTLE_QUEEN_SIDE +
				"\n\tblack castle king side:  " +
				Position.BLACK_CASTLE_KING_SIDE +
				"\n\tblack castle queen side: " +
				Position.BLACK_CASTLE_QUEEN_SIDE 
		);
		printWriter.println("- en passant line");
		printWriter.println(enPassantLine == -1?
				"none" +
				"\n--last location change of a figure was not related to \"en" +
				" passant\".":
				enPassantLine);
		printWriter.println("- comment");
		printWriter.println("The en passant line is the zero-based index of " +
				"the line on which a pawn was moved by two rows during the " +
				"last ply (the last movement in the game)" +
				"\n(A->0, B->1, ..., H->7)" +
				"\nYou should fill in \"none\" here (without quotes), if " +
				"no pawn was moved by two rows during the last ply.");
		printWriter.println("- figure locations");
		// start with single-line comment to prevent the "Position:" header
		// that is generated from Position.toString() to be parsed when
		// reading the file.
		printWriter.println("--" + gameState.getCurrentPosition().toString());
		printWriter.flush();
		printWriter.close();
		try {
			outputStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
