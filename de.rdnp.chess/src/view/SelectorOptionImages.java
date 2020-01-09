package view;

import java.awt.Image;
import java.awt.image.BufferedImage;

// TODO maybe replace with factory or provider or at least anything that is less  
// weird than this...
/**@author Richard Pohl*/
public class SelectorOptionImages {
	public static final BufferedImage BLACK_HUMAN 
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage BLACK_AUTOMATON_EASIEST
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final BufferedImage BLACK_AUTOMATON_EASY
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage BLACK_AUTOMATON_MEDIUM
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final BufferedImage BLACK_AUTOMATON_HARD 
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage BLACK_AUTOMATON_HARDEST 
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final BufferedImage WHITE_HUMAN 
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage WHITE_AUTOMATON_EASIEST 
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage WHITE_AUTOMATON_EASY 
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage WHITE_AUTOMATON_MEDIUM 
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage WHITE_AUTOMATON_HARD 
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final BufferedImage WHITE_AUTOMATON_HARDEST 
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image POSITION_MODE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image INTERACTIVE_MODE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image MOVE_FIGURE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image REMOVE_FIGURE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image ADD_BLACK_PAWN
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_BLACK_BISHOP
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_BLACK_KNIGHT
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_BLACK_ROOK
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_BLACK_QUEEN
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_BLACK_KING
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image ADD_WHITE_PAWN
	= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_WHITE_BISHOP
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_WHITE_KNIGHT
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_WHITE_ROOK
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_WHITE_QUEEN
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image ADD_WHITE_KING
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	public static final Image WHITE_AT_MOVE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);
	
	public static final Image BLACK_AT_MOVE
		= new BufferedImage(44, 44, BufferedImage.TYPE_INT_RGB);

	
	private static final int LINE_1 = 12;
	private static final int LINE_2 = 24;
	private static final int LINE_3 = 36;

	
	static {
		BLACK_HUMAN.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_HUMAN.getGraphics().drawString("Human", 2, LINE_2);
		BLACK_HUMAN.getGraphics().drawString("Player", 2, LINE_3);

		BLACK_AUTOMATON_EASIEST.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AUTOMATON_EASIEST.getGraphics().drawString(
				"Auto", 2, LINE_2);
		BLACK_AUTOMATON_EASIEST.getGraphics().drawString(
				"Easiest", 2, LINE_3);

		BLACK_AUTOMATON_EASY.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AUTOMATON_EASY.getGraphics().drawString("Auto", 2, LINE_2);
		BLACK_AUTOMATON_EASY.getGraphics().drawString(
				"Easy", 2, LINE_3);

		BLACK_AUTOMATON_MEDIUM.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AUTOMATON_MEDIUM.getGraphics().drawString("Auto", 2, LINE_2);
		BLACK_AUTOMATON_MEDIUM.getGraphics().drawString(
				"Normal", 2, LINE_3);

		BLACK_AUTOMATON_HARD.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AUTOMATON_HARD.getGraphics().drawString("Auto", 2, LINE_2);
		BLACK_AUTOMATON_HARD.getGraphics().drawString(
				"Hard", 2, LINE_3);
		
		BLACK_AUTOMATON_HARDEST.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AUTOMATON_HARDEST.getGraphics().drawString(
				"Auto", 2, LINE_2);
		BLACK_AUTOMATON_HARDEST.getGraphics().drawString(
				"Harder", 2, LINE_3);
		
		WHITE_HUMAN.getGraphics().drawString("White", 2, LINE_1);
		WHITE_HUMAN.getGraphics().drawString("Human", 2, LINE_2);
		WHITE_HUMAN.getGraphics().drawString("Player", 2, LINE_3);
		
		WHITE_AUTOMATON_EASIEST.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AUTOMATON_EASIEST.getGraphics().drawString(
				"Auto", 2, LINE_2);
		WHITE_AUTOMATON_EASIEST.getGraphics().drawString(
				"Easiest", 2, LINE_3);

		WHITE_AUTOMATON_EASY.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AUTOMATON_EASY.getGraphics().drawString("Auto", 2, LINE_2);
		WHITE_AUTOMATON_EASY.getGraphics().drawString(
				"Easy", 2, LINE_3);
		
		WHITE_AUTOMATON_MEDIUM.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AUTOMATON_MEDIUM.getGraphics().drawString("Auto", 2, LINE_2);
		WHITE_AUTOMATON_MEDIUM.getGraphics().drawString(
				"Normal", 2, LINE_3);

		WHITE_AUTOMATON_HARD.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AUTOMATON_HARD.getGraphics().drawString("Auto", 2, LINE_2);
		WHITE_AUTOMATON_HARD.getGraphics().drawString(
				"Hard", 2, LINE_3);

		WHITE_AUTOMATON_HARDEST.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AUTOMATON_HARDEST.getGraphics().drawString(
				"Auto", 2, LINE_2);
		WHITE_AUTOMATON_HARDEST.getGraphics().drawString(
				"Harder", 2, LINE_3);
		
		POSITION_MODE.getGraphics().drawString("Position", 2, LINE_1);
		POSITION_MODE.getGraphics().drawString(
				"Editor", 2, LINE_2);
		POSITION_MODE.getGraphics().drawString(
				"Mode", 2, LINE_3);
		
		INTERACTIVE_MODE.getGraphics().drawString("Interact.", 2, LINE_1);
		INTERACTIVE_MODE.getGraphics().drawString(
				"Game", 2, LINE_2);
		INTERACTIVE_MODE.getGraphics().drawString(
				"Mode", 2, LINE_3);
		
		MOVE_FIGURE.getGraphics().drawString("Move", 2, LINE_1);
		MOVE_FIGURE.getGraphics().drawString(
				"Figure", 2, LINE_2);
		
		REMOVE_FIGURE.getGraphics().drawString("Remove", 2, LINE_1);
		REMOVE_FIGURE.getGraphics().drawString(
				"Figure", 2, LINE_2);
		

		ADD_BLACK_PAWN.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_PAWN.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_PAWN.getGraphics().drawString(
				"Pawn", 2, LINE_3);

		ADD_BLACK_BISHOP.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_BISHOP.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_BISHOP.getGraphics().drawString(
				"Bishop", 2, LINE_3);

		ADD_BLACK_KNIGHT.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_KNIGHT.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_KNIGHT.getGraphics().drawString(
				"Knight", 2, LINE_3);

		ADD_BLACK_ROOK.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_ROOK.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_ROOK.getGraphics().drawString(
				"Rook", 2, LINE_3);

		ADD_BLACK_QUEEN.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_QUEEN.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_QUEEN.getGraphics().drawString(
				"Queen", 2, LINE_3);

		ADD_BLACK_KING.getGraphics().drawString("Add", 2, LINE_1);
		ADD_BLACK_KING.getGraphics().drawString(
				"Black", 2, LINE_2);
		ADD_BLACK_KING.getGraphics().drawString(
				"King", 2, LINE_3);
		
		ADD_WHITE_PAWN.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_PAWN.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_PAWN.getGraphics().drawString(
				"Pawn", 2, LINE_3);

		ADD_WHITE_BISHOP.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_BISHOP.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_BISHOP.getGraphics().drawString(
				"Bishop", 2, LINE_3);

		ADD_WHITE_KNIGHT.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_KNIGHT.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_KNIGHT.getGraphics().drawString(
				"Knight", 2, LINE_3);

		ADD_WHITE_ROOK.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_ROOK.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_ROOK.getGraphics().drawString(
				"Rook", 2, LINE_3);

		ADD_WHITE_QUEEN.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_QUEEN.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_QUEEN.getGraphics().drawString(
				"Queen", 2, LINE_3);

		ADD_WHITE_KING.getGraphics().drawString("Add", 2, LINE_1);
		ADD_WHITE_KING.getGraphics().drawString(
				"White", 2, LINE_2);
		ADD_WHITE_KING.getGraphics().drawString(
				"King", 2, LINE_3);
	
		WHITE_AT_MOVE.getGraphics().drawString("White", 2, LINE_1);
		WHITE_AT_MOVE.getGraphics().drawString(
				"at", 2, LINE_2);
		WHITE_AT_MOVE.getGraphics().drawString(
				"move", 2, LINE_3);
		
		BLACK_AT_MOVE.getGraphics().drawString("Black", 2, LINE_1);
		BLACK_AT_MOVE.getGraphics().drawString(
				"at", 2, LINE_2);
		BLACK_AT_MOVE.getGraphics().drawString(
				"move", 2, LINE_3);
	}
}
