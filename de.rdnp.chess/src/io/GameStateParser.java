package io;

import game.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import model.Bishop;
import model.ChessLocation;
import model.Figure;
import model.King;
import model.Knight;
import model.Pawn;
import model.Position;
import model.Queen;
import model.Rook;


/** 
 * This is a parser for {@link GameState} objects.
 * @author Richard Pohl */
public class GameStateParser 
{
	// parse states
	// indicates to parse a multi-line comment (actually ignores it)
	private static final int PARSE_COMMENT				= 0x0;
	// indicates to parse figures
	private static final int PARSE_FIGURE_LOCATIONS 	= 0x1; 
	// indicates to parse castling rights
	private static final int PARSE_CASTLING 			= 0x2;
	// indicates to parse en passant line
	private static final int PARSE_EN_PASSANT_LINE 		= 0x3;
	// indicates to parse the player at move
	private static final int PARSE_MOVE_PLAYER			= 0x4;
	
	private InputStream inputStream;
	
	// the state of the parser
	private int parserState;
	// the y-position (row) in the position to parse
	private int row;
	
	private Position parsedPosition;
	// attributes of the result
	private int castlingRights;
	private int enPassantLine;
	private Player activePlayer;
	
	/**
	 * Constructs a {@link GameStateParser}.
	 * @param inputStream - the {@link InputStream} from which the position
	 * should be read. 
	 * */
	public GameStateParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Parses a {@link Position} from the {@link InputStream} of the 
	 * {@link GameStateParser}.
	 * Closes the InputStream of the parser after finishing.
	 * @return A {@link GameState} containing the parsed {@link Position} and
	 * the {@link Player} at move in the Position.
	 * */
	public GameState parse() {
		parsedPosition = new Position();
		castlingRights = 0x0F;
		enPassantLine = -1;
		row = 7;
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
				else if (line.toLowerCase().contains("figure locations"))
					parserState = PARSE_FIGURE_LOCATIONS;
				else if (line.toLowerCase().contains("en passant line"))
					parserState = PARSE_EN_PASSANT_LINE;
				else if (line.toLowerCase().contains("player"))
					parserState = PARSE_MOVE_PLAYER;
				else if (line.toLowerCase().contains("castling"))
					parserState = PARSE_CASTLING;
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
		parsedPosition.setCastlingHistory(castlingRights);
		parsedPosition.setEnPassantLine(enPassantLine);
		return new GameState(parsedPosition, activePlayer);
	}
	
	/**
	 * Parse a line depending on the parser state.
	 * @param line - the line to parse.
	 * */
	private void parseLine(String line)
	{
		switch (parserState)
		{
			case PARSE_CASTLING: 		 parseCastlingLine(line); 		break;
			case PARSE_EN_PASSANT_LINE:  parseEnPassantLine(line); 		break;
			case PARSE_FIGURE_LOCATIONS: parseFigureLocationLine(line); break;
			case PARSE_MOVE_PLAYER: 	 parseMovePlayer(line); 		break;
			default:
		}
	}
	
	/**
	 * @param playerChar - the char corresponding to the {@link Player} to 
	 * return.
	 * @return the {@link Player} corresponding to the char given as parameter 
	 * */
	private Player getPlayer(char playerChar) {
		switch (playerChar)
		{
			case 'b': case 'B': return Player.BLACK; 
			case 'w': case 'W': return Player.WHITE; 
		}
		return null;
	}
	
	/**
	 * @param owner - the owner of the {@link Figure} to return.
	 * @param figureChar - the character representing the figure to return.
	 * @return The figure corresponding to the given parameters.
	 * */
	private Figure getFigure(Player owner, char figureChar)
	{
		Figure result = null;
		switch (figureChar)
		{
			case 'p': case 'P': result = new Pawn(owner); 		break;
			case 'b': case 'B': result = new Bishop(owner);		break;
			case 'n': case 'N': result = new Knight(owner);		break;
			case 'r': case 'R': result = new Rook(owner);		break;
			case 'q': case 'Q': result = new Queen(owner);		break;
			case 'k': case 'K': result = new King(owner);		break;
		}
		return result;
	}
	
	/**
	 * Parses a line containing figure locations.
	 * @param line - the line to parse.
	 * */
	private void parseFigureLocationLine(String line) {
		if (row > 0)
		{
			row = Integer.parseInt(line.substring(0,1)) - 1;
			for (int i = 3; (i + 2) < line.length(); i += 3)
			{
				int chessLine = i / 3 - 1;
				Player player = getPlayer(line.charAt(i));	
				Figure figure = getFigure(player, line.charAt(i + 1));
				parsedPosition = new Position(parsedPosition, figure, 
					new ChessLocation(chessLine, row));
			}
		}
	}
	
	/**
	 * Parses a line containing en passant line information.
	 * @param line - the line to parse.
	 * */
	private void parseEnPassantLine(String line) {
		if (!line.startsWith("n"))
			try {
				enPassantLine = Integer.parseInt(line);
			}
			catch (NumberFormatException nfe)
			{
				nfe.printStackTrace();
			}
	}
	
	/**
	 * Parses a line containing castling rights.
	 * @param line - the line to parse.
	 * */
	private void parseCastlingLine(String line) {
		try {
			castlingRights = Integer.parseInt(line);
		}
		catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}
	
	/**
	 * Parses a line containing the player at move.
	 * @param line - the line to parse.
	 * */
	private void parseMovePlayer(String line) {
		if (line.equalsIgnoreCase("black"))
			activePlayer = Player.BLACK;
		else if (line.equalsIgnoreCase("white"))
			activePlayer = Player.WHITE;
	}
}